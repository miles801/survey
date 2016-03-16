package eccrm.base.role.service.impl;

import com.michael.pinyin.PinYin;
import com.michael.pinyin.SimplePinYin;
import com.michael.pinyin.StandardStrategy;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.role.bo.RoleBo;
import eccrm.base.role.dao.RoleDao;
import eccrm.base.role.domain.Role;
import eccrm.base.role.domain.RoleStatus;
import eccrm.base.role.exceptions.RoleCodeExistsException;
import eccrm.base.role.exceptions.RoleNameExistsException;
import eccrm.base.role.service.RoleService;
import eccrm.base.role.vo.RoleVo;
import eccrm.core.VoHelper;
import com.ycrl.core.pager.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-03-26
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public String save(Role role) {
        verify(role);
        if (role.getStatus() == null) {
            role.setStatus(RoleStatus.INACTIVE.getValue());
        }
        PinYin pinYin = new SimplePinYin();
        role.setPinyin(pinYin.toPinYin(role.getName(), new StandardStrategy()));
        return roleDao.save(role);
    }

    @Override
    public void update(Role role) {
        verify(role);
        PinYin pinYin = new SimplePinYin();
        role.setPinyin(pinYin.toPinYin(role.getName(), new StandardStrategy()));
        roleDao.update(role);
    }

    @Override
    public PageVo query(RoleBo bo) {
        PageVo vo = new PageVo();
        Long total = roleDao.getTotal(bo);
        if (total == null || total == 0) return vo;
        vo.setTotal(total);
        List<Role> roles = roleDao.query(bo);
        vo.setData(VoHelper.wrapVos(roles, this));
        return vo;
    }

    @Override
    public RoleVo findById(String id) {
        return wrap(roleDao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            if ("".equals(id.trim())) continue;
            Role role = roleDao.findById(id);
            if (role == null) {
                throw new EntityNotFoundException("ID为[" + id + "]的角色不存在或者已经被删除，请刷新重试!");
            }
            String status = role.getStatus();
            if (status == null || status.equals(RoleStatus.INACTIVE.getValue())) {
                roleDao.delete(role);
            } else if (status.equals(RoleStatus.ACTIVE.getValue())) {
                role.setStatus(RoleStatus.CANCELED.getValue());
            }
        }
    }

    @Override
    public List<RoleVo> queryValid(RoleBo bo) {
        if (bo == null) {
            bo = new RoleBo();
        }
        bo.setStatus(RoleStatus.ACTIVE.getValue());
        bo.setValid(true);
        List<Role> roles = roleDao.query(bo);
        return VoHelper.wrapVos(roles, this);
    }

    @Override
    public RoleVo wrap(Role role) {
        if (role == null) return null;
        RoleVo vo = new RoleVo();
        BeanUtils.copyProperties(role, vo);
        vo.setStatusName(ParameterContainer.getInstance().getSystemName(RoleService.ROLE_STATE, vo.getStatus()));
        return vo;
    }

    /**
     * 保存/更新之前对角色的合法性进行校验
     * 如果不合法，则会抛出相应的异常
     */
    private void verify(Role role) {
        if (role != null) {
            String id = role.getId();
            String name = role.getName();
            String code = role.getCode();
            if (roleDao.hasName(id, name)) {
                throw new RoleNameExistsException(name);
            }
            if (roleDao.hasCode(id, code)) {
                throw new RoleCodeExistsException(code);
            }
        }
    }
}
