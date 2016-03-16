package eccrm.base.user.dao;

import eccrm.base.user.bo.LoginLogBo;
import eccrm.base.user.domain.LoginLog;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-04-14
 */
public interface LoginLogDao {

    String save(LoginLog loginLog);

    void update(LoginLog loginLog);

    List<LoginLog> query(LoginLogBo bo);

    long getTotal(LoginLogBo bo);

    LoginLog findById(String id);

    void deleteById(String id);

    /**
     * 获得指定用户最近一次的登录信息
     *
     * @param userId 用户id
     */
    LoginLog last(String userId);

    /**
     * 由于系统关闭而强制踢出所有在线用户
     */
    void logoutAllBySystemDown();

    /**
     * 判断用户是否在线
     *
     * @param userId 用户id
     */
    boolean userIsOnline(String userId);

    /**
     * 查询所有在线用户的id
     */
    List<String> queryAllOnlineUserId();

    /**
     * 查询所有在线用户
     */
    List<LoginLog> queryAllOnlineUser(LoginLogBo bo);

}
