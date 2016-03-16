package eccrm.base.user.dao;

import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.user.domain.PasswordPolicy;

/**
 * @author miles
 * @datetime 2014-03-17
 */
public interface PasswordPolicyDao {

    @LogInfo(type = OperateType.ADD, describe = "密码策略-新增")
    String save(PasswordPolicy passwordPolicy);

    @LogInfo(type = OperateType.MODIFY, describe = "密码策略-修改")
    void update(PasswordPolicy passwordPolicy);

    /**
     * 获得当前系统中的默认密码策略
     */
    PasswordPolicy get();

}
