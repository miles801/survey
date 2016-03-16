package eccrm.base.parameter.service.impl;

import com.ycrl.core.pager.PageVo;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.constant.ParameterConstant;
import eccrm.base.message.BasicMessage;
import eccrm.base.message.distribute.DistributeHelper;
import eccrm.base.parameter.bo.SysParamTypeBo;
import eccrm.base.parameter.dao.SysParamTypeDao;
import eccrm.base.parameter.domain.SysParamType;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.parameter.service.SysParamTypeService;
import eccrm.base.parameter.vo.SysParamTypeVo;
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
 * @datetime: 2014-06-20
 */
@Service("sysParamTypeService")
public class SysParamTypeServiceImpl implements SysParamTypeService, VoWrapper<SysParamType, SysParamTypeVo> {
    @Resource
    private SysParamTypeDao dao;

    @Override
    public String save(SysParamType sysParamType) {
        sysParamType.setPath(null);
        if (sysParamType.getParent() != null && StringUtils.isEmpty(sysParamType.getParent().getId())) {
            sysParamType.setParent(null);
        }
        String id = dao.save(sysParamType);
        PathTreeBuilder.buildAfterSave(dao, sysParamType);
        return id;
    }

    @Override
    public void update(SysParamType sysParamType) {
        if (sysParamType.getParent() != null && StringUtils.isEmpty(sysParamType.getParent().getId())) {
            sysParamType.setParent(null);
        }
        PathTreeBuilder.buildBeforeUpdate(dao, sysParamType);
        dao.update(sysParamType);

        // 更新缓存信息
//        noticeBroker(sysParamType.getCode());
        ParameterContainer.getInstance().reloadSystem(sysParamType.getCode());

    }

    @Override
    public PageVo query(SysParamTypeBo bo) {
        PageVo vo = new PageVo();
        Long total = dao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<SysParamType> sysParamTypes = dao.query(bo);
        vo.setData(VoHelper.wrapVos(sysParamTypes, this));
        return vo;
    }

    @Override
    public SysParamTypeVo findById(String id) {
        return wrap(dao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            SysParamType type = dao.findById(id);
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
            ParameterContainer.getInstance().removeSys(type.getCode());
        }
    }


    public SysParamTypeVo wrap(SysParamType sysParamType) {
        if (sysParamType == null) return null;
        SysParamTypeVo vo = new SysParamTypeVo();
        BeanUtils.copyProperties(sysParamType, vo);
        SysParamType parent = sysParamType.getParent();
        if (parent != null) {
            vo.setParentId(parent.getId());
            vo.setParentName(parent.getName());
        }
        List<SysParamType> children = sysParamType.getChildren();
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
    public List<SysParamTypeVo> queryOther(String id) {
        List<SysParamType> params = dao.queryOther(id);
        List<SysParamType> tree = TreeFactory.buildTree(params, SysParamType.class);
        return VoHelper.wrapVos(tree, this);
    }

    @Override
    public List<SysParamTypeVo> allForTree() {
        List<SysParamType> types = dao.query(null);
        return VoHelper.wrapVos(TreeFactory.buildTree(types, SysParamType.class), this);
    }

    @Override
    public List<SysParamTypeVo> queryChildren(String id, boolean containSelf) {
        List<SysParamType> types = dao.queryChildren(id);
        if (containSelf) {
            types.add(0, dao.findById(id));
        }
        return VoHelper.wrapVos(types, this);
    }

    @Override
    public List<SysParamTypeVo> queryUsingTree() {
        List<SysParamType> params = dao.queryUsing();
        List<SysParamType> tree = TreeFactory.buildTree(params, SysParamType.class);
        return VoHelper.wrapVos(tree, this);
    }

    @Override
    public List<SysParamTypeVo> queryValidTree() {
        SysParamTypeBo bo = new SysParamTypeBo();
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<SysParamType> params = dao.query(bo);
        List<SysParamType> tree = TreeFactory.buildTree(params, SysParamType.class);
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
                DistributeHelper.getInstance().distribute(SysParamMessageHandler.TYPE, basicMessage);
            }
        });
        thread.start();
    }
}
