package eccrm.base.parameter.service.impl;

import com.ycrl.core.pager.PageVo;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.constant.ParameterConstant;
import eccrm.base.message.BasicMessage;
import eccrm.base.message.distribute.DistributeHelper;
import eccrm.base.parameter.bo.BusinessParamTypeBo;
import eccrm.base.parameter.dao.BusinessParamTypeDao;
import eccrm.base.parameter.domain.BusinessParamType;
import eccrm.base.parameter.service.BusinessParamTypeService;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.parameter.vo.BusinessParamTypeVo;
import eccrm.core.VoHelper;
import eccrm.core.VoWrapper;
import eccrm.utils.StringUtils;
import eccrm.utils.tree.PathTreeBuilder;
import eccrm.utils.tree.TreeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author: miles
 * @datetime: 2014-07-02
 */
@Service("businessParamTypeService")
public class BusinessParamTypeServiceImpl implements BusinessParamTypeService, VoWrapper<BusinessParamType, BusinessParamTypeVo> {
    @Resource
    private BusinessParamTypeDao dao;

    @Override
    public String save(BusinessParamType sysParamType) {
        sysParamType.setPath(null);
        clearParentIfNull(sysParamType);
        String id = dao.save(sysParamType);
        PathTreeBuilder.buildAfterSave(dao, sysParamType);
        return id;
    }

    private void clearParentIfNull(BusinessParamType sysParamType) {
        if (sysParamType.getParent() != null && StringUtils.isEmpty(sysParamType.getParent().getId())) {
            sysParamType.setParent(null);
        }
    }

    @Override
    public void update(BusinessParamType sysParamType) {
        clearParentIfNull(sysParamType);
        PathTreeBuilder.buildBeforeUpdate(dao, sysParamType);
        dao.update(sysParamType);

        // 更新缓存
//        noticeBroker(sysParamType.getCode());
        ParameterContainer.getInstance().reloadBusiness(sysParamType.getCode());
    }

    @Override
    public PageVo query(BusinessParamTypeBo bo) {
        PageVo vo = new PageVo();
        Long total = dao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<BusinessParamType> sysParamTypes = dao.query(bo);
        vo.setData(VoHelper.wrapVos(sysParamTypes, this));
        return vo;
    }

    @Override
    public BusinessParamTypeVo findById(String id) {
        return wrap(dao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            BusinessParamType type = dao.findById(id);
            if (type == null) {
                throw new EntityNotFoundException(id);
            }

            // 删除或更改状态
            String status = type.getStatus();
            if (CommonStatus.ACTIVE.getValue().equals(status)) {
                type.setStatus(CommonStatus.CANCELED.getValue());
            } else if (CommonStatus.INACTIVE.getValue().equals(status)) {
                dao.delete(type);
            }

            // 更新缓存
//            noticeBroker(type.getCode());
            ParameterContainer.getInstance().reloadBusiness(type.getCode());
        }
    }


    public BusinessParamTypeVo wrap(BusinessParamType businessParamType) {
        if (businessParamType == null) return null;
        BusinessParamTypeVo vo = new BusinessParamTypeVo();
        BeanUtils.copyProperties(businessParamType, vo);
        BusinessParamType parent = businessParamType.getParent();
        if (parent != null) {
            vo.setParentId(parent.getId());
            vo.setParentName(parent.getName());
        }
        List<BusinessParamType> children = businessParamType.getChildren();
        if (children != null && children.size() > 0) {
            vo.setChildren(VoHelper.wrapVos(children, this));
        }
        ParameterContainer container = ParameterContainer.getInstance();
        vo.setStatusName(container.getSystemName(ParameterConstant.COMMON_STATE, vo.getStatus()));
        return vo;
    }

    @Override
    public boolean hasName(String parentId, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("名称不能为空!");
        }
        return dao.hasName(parentId, name);
    }

    @Override
    public boolean hasCode(String code) {
        return dao.hasCode(code);
    }

    @Override
    public List<BusinessParamTypeVo> queryOther(String id) {
        List<BusinessParamType> params = dao.queryOther(id);
        List<BusinessParamType> tree = TreeFactory.buildTree(params, BusinessParamType.class);
        return VoHelper.wrapVos(tree, this);
    }

    @Override
    public List<BusinessParamTypeVo> allForTree() {
        List<BusinessParamType> params = dao.query(null);
        List<BusinessParamType> tree = TreeFactory.buildTree(params, BusinessParamType.class);
        return VoHelper.wrapVos(tree, this);
    }

    @Override
    public List<BusinessParamTypeVo> queryChildren(String id, boolean containSelf) {
        List<BusinessParamType> types = dao.queryChildren(id);
        if (containSelf) {
            types.add(0, dao.findById(id));
        }
        return VoHelper.wrapVos(types, this);
    }

    @Override
    public List<BusinessParamTypeVo> queryUsingTree() {
        List<BusinessParamType> params = dao.queryUsing();
        List<BusinessParamType> tree = TreeFactory.buildTree(params, BusinessParamType.class);
        return VoHelper.wrapVos(tree, this);
    }

    @Override
    public List<BusinessParamTypeVo> queryValidTree() {
        BusinessParamTypeBo bo = new BusinessParamTypeBo();
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<BusinessParamType> params = dao.query(bo);
        List<BusinessParamType> tree = TreeFactory.buildTree(params, BusinessParamType.class);
        return VoHelper.wrapVos(tree, this);
    }

    @Override
    public String getName(String code) {
        return dao.getName(code);
    }

    private void noticeBroker(final String type) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BasicMessage basicMessage = new BasicMessage();
                basicMessage.setContent(type);
                DistributeHelper.getInstance().distribute(BusinessParamMessageHandler.TYPE, basicMessage);
            }
        });
        thread.start();
    }
}
