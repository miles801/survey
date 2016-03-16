package eccrm.base.user.service;

import eccrm.base.user.domain.PasswordPolicy;
import eccrm.base.user.vo.PasswordPolicyVo;

/**
 * @author miles
 * @datetime 2014-03-17
 */
public interface PasswordPolicyService {

    String save(PasswordPolicy passwordPolicy);

    void update(PasswordPolicy passwordPolicy);

    /**
     * 获得密码策略
     *
     * @return
     */
    PasswordPolicyVo get();

}
