package eccrm.core.security;

/**
 * @author miles
 * @datetime 13-12-15 下午11:47
 */
public interface LoginInfo {
    /**
     * 是否已经登录
     */
    String HAS_LOGIN = "hasLogin";
    /**
     * 账号
     */
    String USERNAME = "username";
    /**
     * 账户id
     */
    String USER = "userId";

    String ORG = "orgId";

    /**
     * 员工id
     */
    String EMPLOYEE = "employeeId";

    /**
     * 员工名称
     */
    String EMPLOYEE_NAME = "employeeName";

    /**
     * 租户id
     */
    String TENEMENT = "tenementId";

    String LOGIN_DATETIME = "loginDatetime";
    String ORG_NAME = "orgName";
}
