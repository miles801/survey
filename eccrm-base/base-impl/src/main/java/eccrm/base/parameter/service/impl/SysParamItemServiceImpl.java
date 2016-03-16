package eccrm.base.parameter.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.pager.PageVo;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.constant.ParameterConstant;
import eccrm.base.message.BasicMessage;
import eccrm.base.message.distribute.DistributeHelper;
import eccrm.base.parameter.bo.SysParamItemBo;
import eccrm.base.parameter.dao.SysParamItemDao;
import eccrm.base.parameter.dao.SysParamTypeDao;
import eccrm.base.parameter.domain.SysParamItem;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.parameter.service.SysParamItemService;
import eccrm.base.parameter.vo.SysParamItemVo;
import eccrm.core.VoHelper;
import eccrm.core.VoWrapper;
import eccrm.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author: miles
 * @datetime: 2014-06-20
 */
@Service("sysParamItemService")
public class SysParamItemServiceImpl implements SysParamItemService, VoWrapper<SysParamItem, SysParamItemVo> {
    @Resource(name = "sysParamItemDao")
    private SysParamItemDao dao;

    @Resource(name = "sysParamTypeDao")
    private SysParamTypeDao sysParamTypeDao;

    @Override
    public String save(SysParamItem sysParamItem) {
        String id = dao.save(sysParamItem);
        // 更新缓存
//        noticeBroker(sysParamItem.getType());
        ParameterContainer.getInstance().reloadSystem(sysParamItem.getType());
        return id;
    }

    @Override
    public void update(SysParamItem sysParamItem) {
        dao.update(sysParamItem);
        // 更新缓存
//        noticeBroker(sysParamItem.getType());
        ParameterContainer.getInstance().reloadSystem(sysParamItem.getType());
    }

    @Override
    public PageVo query(SysParamItemBo bo) {
        PageVo vo = new PageVo();
        Long total = dao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<SysParamItem> sysParamItems = dao.query(bo);
        vo.setData(VoHelper.wrapVos(sysParamItems, this));
        return vo;
    }

    @Override
    public SysParamItemVo findById(String id) {
        return wrap(dao.findById(id));
    }

    @Override
    public List<SysParamItemVo> queryValid(String type) {
        if (StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException("根据参数类型查询有效的选项时,没有获得类型编号!");
        }
        SysParamItemBo bo = new SysParamItemBo();
        bo.setType(type);
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<SysParamItem> items = dao.query(bo);
        return VoHelper.wrapVos(items, new VoWrapper<SysParamItem, SysParamItemVo>() {
            @Override
            public SysParamItemVo wrap(SysParamItem sysParamItem) {
                SysParamItemVo vo = new SysParamItemVo();
                vo.setId(sysParamItem.getId());
                vo.setName(sysParamItem.getName());
                vo.setType(sysParamItem.getType());
                vo.setValue(sysParamItem.getValue());
                vo.setCascadeTypeCode(sysParamItem.getCascadeTypeCode());
                vo.setCascadeItemValue(sysParamItem.getCascadeItemValue());
                return vo;
            }
        });
    }

    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            SysParamItem item = dao.findById(id);
            if (item == null) {
                throw new EntityNotFoundException(id);
            }

            // 删除或更改状态
            String status = item.getStatus();
            if (CommonStatus.ACTIVE.getValue().equals(status)) {
                item.setStatus(CommonStatus.CANCELED.getValue());
            } else if (CommonStatus.INACTIVE.getValue().equals(status)) {
                dao.delete(item);
            }

            // 更新缓存
//            noticeBroker(item.getType());
            ParameterContainer.getInstance().reloadSystem(item.getType());
        }
    }


    public SysParamItemVo wrap(SysParamItem sysParamItem) {
        if (sysParamItem == null) return null;
        SysParamItemVo vo = new SysParamItemVo();
        BeanUtils.copyProperties(sysParamItem, vo);
        ParameterContainer container = ParameterContainer.getInstance();
        vo.setTypeName(container.getSystemTypeName(vo.getType()));
        vo.setStatusName(container.getSystemName(ParameterConstant.COMMON_STATE, vo.getStatus()));
        String cascadeType = sysParamItem.getCascadeTypeCode();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(cascadeType)) {
            vo.setCascadeTypeName(container.getSystemTypeName(cascadeType));
            String cascadeValue = sysParamItem.getCascadeItemValue();
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(cascadeValue)) {
                String name = container.getSystemNameWithNoQuery(cascadeType, cascadeValue);
                if (name == null) {
                    name = dao.queryName(cascadeType, cascadeValue);
                }
                vo.setCascadeItemName(name);
            }
        }
        return vo;
    }


    @Override
    public boolean hasName(String typeCode, String name) {
        return dao.hasName(typeCode, name);
    }

    @Override
    public boolean hasValue(String typeCode, String value) {
        return dao.hasValue(typeCode, value);
    }

    @Override
    public List<SysParamItemVo> fetchCascade(String typeCode, String value) {
        List<SysParamItem> items = dao.fetchCascade(typeCode, value);
        return BeanWrapBuilder.newInstance().wrapList(items, SysParamItemVo.class);
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
