package com.ycrl.core.context;

/**
 * 安全上下文
 * Created by Michael on 2014/10/19.
 */
public class SecurityContext {
    /**
     * 用户ID
     */
    private static ThreadLocal<String> _userId = new ThreadLocal<String>();
    /**
     * 用户名
     */
    private static ThreadLocal<String> _username = new ThreadLocal<String>();
    /**
     * 租户ID
     */
    private static ThreadLocal<String> _tenementId = new ThreadLocal<String>();
    /**
     * 是否已经登录
     */
    private static ThreadLocal<Boolean> _login = new ThreadLocal<Boolean>();
    /**
     * 员工名称
     */
    private static ThreadLocal<String> _empName = new ThreadLocal<String>();
    /**
     *
     */
    private static ThreadLocal<String> _empId = new ThreadLocal<String>();


    public static String getUserId() {
        return _userId.get();
    }


    public static String getUsername() {
        return _username.get();
    }


    public static String getTenementId() {
        return _tenementId.get();
    }

    public static String getEmpName() {
        return _empName.get();
    }

    /**
     * 全部移除
     */
    public static void remove() {
        _userId.remove();
        _username.remove();
        _tenementId.remove();
        _empName.remove();
        _empId.remove();
        _login.remove();
    }

    public static void set(String userId, String username, String tenementId) {
        _userId.set(userId);
        _username.set(username);
        _tenementId.set(tenementId);
        _login.set(true);
    }

    public static void setLogin(boolean login) {
        _login.set(login);
    }

    public static boolean hasLogin() {
        return _login.get();
    }

    public static void setEmpName(String empName) {
        _empName.set(empName);
    }

    public static void setEmpId(String empId) {
        _empId.set(empId);
    }

    public static String getEmpId() {
        return _empId.get();
    }

}
