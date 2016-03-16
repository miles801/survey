package eccrm.base.menu.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.constant.ParameterConstant;
import eccrm.base.menu.bo.ResourceBo;
import eccrm.base.menu.dao.ResourceDao;
import eccrm.base.menu.domain.Resource;
import eccrm.base.menu.service.DataResourceContext;
import eccrm.base.menu.service.ResourceService;
import eccrm.base.menu.vo.ResourceVo;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.core.exception.NullParamException;
import eccrm.utils.StringUtils;
import eccrm.utils.tree.PathTreeBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Michael
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService, BeanWrapCallback<Resource, ResourceVo> {
    @javax.annotation.Resource
    private ResourceDao resourceDao;

    private Logger logger = Logger.getLogger(ResourceServiceImpl.class);

    @Override
    public String save(Resource entity) {
        entity.setPath("/");//设置一个默认的path
        if (entity.getParent() != null && StringUtils.isEmpty(entity.getParent().getId())) {
            entity.setParent(null);
        }
        if (StringUtils.isEmpty(entity.getStatus())) {
            entity.setStatus(CommonStatus.INACTIVE.getValue());
        }
        String id = resourceDao.save(entity);
        PathTreeBuilder.buildAfterSave(resourceDao, entity);

        // 如果是数据资源，则重置已缓存的数据
        if (Resource.TYPE_DATA.equals(entity.getType())) {
            DataResourceContext.getInstance().reload();
        }
        return id;
    }

    @Override
    public ResourceVo findById(String entityId) {
        if (entityId == null) {
            throw new NullParamException("id");
        }
        Resource resource = resourceDao.findById(entityId);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrap(resource, ResourceVo.class);
    }


    @Override
    public void update(Resource entity) {
        PathTreeBuilder.buildBeforeUpdate(resourceDao, entity);
        if (entity.getParent() != null && StringUtils.isEmpty(entity.getParent().getId())) {
            entity.setParent(null);
        }
        //更新
        resourceDao.update(entity);

        // 如果是数据资源，则重置已缓存的数据
        if (Resource.TYPE_DATA.equals(entity.getType())) {
            DataResourceContext.getInstance().reload();
        }
    }

    @Override
    public void delete(String... ids) {
        if (ids == null || ids.length < 1) return;
        for (String id : ids) {
            Resource resource = resourceDao.findById(id);
            if (resource == null) {
                throw new EntityNotFoundException(id);
            }
            //如果状态为未启用，则直接删除（同时删除子节点）；否则修改状态
            if (CommonStatus.INACTIVE.getValue().equals(resource.getStatus())) {
                List<Resource> resources = resourceDao.queryChildren(id);
                for (Resource foo : resources) {
                    resourceDao.delete(foo);
                }
                resourceDao.delete(resource);
                logger.debug(SecurityContext.getUsername() + "删除菜单[" + resource.getName() + "]以及其所有子菜单!");
            } else {
                resource.setStatus(CommonStatus.CANCELED.getValue());
            }
        }

        // 删除了资源后，重置已缓存的数据
        DataResourceContext.getInstance().reload();
    }


    @Override
    public List<ResourceVo> tree(ResourceBo bo) {
        List<Resource> resources = resourceDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(resources, ResourceVo.class);
    }


    @Override
    public PageVo query(ResourceBo bo) {
        PageVo vo = new PageVo();
        long total = resourceDao.getTotal(bo);
        vo.setTotal(total);
        if (total < 1) return vo;
        List<Resource> resources = resourceDao.query(bo);

        vo.setData(BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(resources, ResourceVo.class));
        return vo;
    }

    @Override
    public List<ResourceVo> queryOtherTree(String id) {
        List<Resource> resources = resourceDao.queryOther(id);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(resources, ResourceVo.class);
    }

    @Override
    public boolean hasName(String id, String name) {
        return resourceDao.hasName(id, name);
    }

    @Override
    public boolean hasCode(String code) {
        return resourceDao.hasCode(code);
    }

    @Override
    public PageVo queryAllChildren(String id) {
        PageVo vo = new PageVo();
        ResourceBo bo = new ResourceBo();
        bo.setPath("/" + id + "/");
        long total = resourceDao.getTotal(bo);
        vo.setTotal(total);
        if (total < 1) {
            return vo;
        }
        List<Resource> children = resourceDao.query(bo);
        List<ResourceVo> data = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(children, ResourceVo.class);
        vo.setData(data);
        return vo;
    }

    @Override
    public List<ResourceVo> queryValid() {
        ResourceBo bo = new ResourceBo();
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        bo.setShow(true);
        List<Resource> resources = resourceDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(resources, ResourceVo.class);
    }

    @Override
    public List<ResourceVo> queryAllValidMenu() {
        List<Resource> resources = resourceDao.queryValidResource();
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(resources, ResourceVo.class);
    }

    @Override
    public List<String> queryPermissionResources() {
        return null;
    }


    @Override
    public void doCallback(Resource resource, ResourceVo vo) {
        if (resource.getParent() != null) {
            vo.setParentId(resource.getParent().getId());
            vo.setParentName(resource.getParent().getName());
        }
        ParameterContainer container = ParameterContainer.getInstance();
        vo.setStatusName(container.getSystemName(ParameterConstant.COMMON_STATE, vo.getStatus()));
        vo.setTypeName(container.getSystemName(Resource.PARAM_TYPE, vo.getType()));
        vo.setModuleName(container.getSystemName(Resource.SYS_MODULE, vo.getModule()));
    }

    @Override
    public List<ResourceVo> queryAllValidElement() {
        ResourceBo bo = new ResourceBo();
        bo.setType(Resource.TYPE_ELEMENT);
        bo.setValid(true);
        List<Resource> resources = resourceDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "module", "code"})
                .setCallback(this)
                .wrapList(resources, ResourceVo.class);
    }

    @Override
    public List<ResourceVo> queryAllValidData() {
        ResourceBo bo = new ResourceBo();
        bo.setType(Resource.TYPE_DATA);
        bo.setValid(true);
        // 查询出所有的数据资源
        List<Resource> resources = resourceDao.query(bo);
        if (resources == null || resources.isEmpty()) return new ArrayList<ResourceVo>();
        // 获取所有的上级资源
        Set<String> parentIds = new HashSet<String>();
        for (Resource resource : resources) {
            String path = resource.getPath();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(path)) {
                String arr[] = path.split("/");
                for (String foo : arr) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(foo) && !foo.equals(resource.getId())) {
                        parentIds.add(foo);
                    }
                }
            }
        }

        // 查询所有的上级资源
        List<Resource> parent = resourceDao.queryByIds(parentIds);
        if (parent != null && !parent.isEmpty()) {
            resources.addAll(parent);
        }
        // 组装成树
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "module", "code"})
                .setCallback(new BeanWrapCallback<Resource, ResourceVo>() {
                    @Override
                    public void doCallback(Resource o, ResourceVo vo) {
                        if (o.getParent() != null) {
                            vo.setParentId(o.getParent().getId());
                        }
                        // 是否为数据类型
                        vo.setData(o.getType().equals(Resource.TYPE_DATA));
                    }
                })
                .wrapList(resources, ResourceVo.class);
    }

    @Override
    public List<String> queryAllLimitDataResourceCode() {
        return resourceDao.queryAllLimitDataResource();
    }
}
