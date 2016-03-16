package eccrm.base.user.service.impl;

import eccrm.base.user.dao.PasswordPolicyDao;
import eccrm.base.user.domain.PasswordPolicy;
import eccrm.base.user.service.PasswordPolicyService;
import eccrm.base.user.vo.PasswordPolicyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author miles
 * @datetime 2014-03-17
 */
@Service("passwordPolicyService")
public class PasswordPolicyServiceImpl implements PasswordPolicyService {
    @Resource
    private PasswordPolicyDao passwordPolicyDao;

    @Override
    public String save(PasswordPolicy passwordPolicy) {
        return passwordPolicyDao.save(passwordPolicy);
    }

    @Override
    public void update(PasswordPolicy passwordPolicy) {
        passwordPolicyDao.update(passwordPolicy);
    }


    @Override
    public PasswordPolicyVo get() {
        return wrapVo(passwordPolicyDao.get());
    }


    private PasswordPolicyVo wrapVo(PasswordPolicy passwordPolicy) {
        if (passwordPolicy == null) return null;
        PasswordPolicyVo vo = new PasswordPolicyVo();
        BeanUtils.copyProperties(passwordPolicy, vo);
        return vo;
    }

}
