package eccrm.base.user.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.user.bo.LoginLogBo;
import eccrm.base.user.domain.LoginLog;
import eccrm.base.user.vo.LoginLogVo;
import eccrm.core.VoWrapper;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-04-14
 */
public interface LoginLogService extends VoWrapper<LoginLog, LoginLogVo> {

    String save(LoginLog loginLog);

    void update(LoginLog loginLog);

    PageVo query(LoginLogBo bo);

    LoginLogVo findById(String id);

    void deleteByIds(String... ids);

    /**
     * 用户由于session超时，而强制设置其登录状态为“超时退出”
     * 如果用户已经退出，则不做任何操作
     *
     * @param userId
     * @param lastAccessedTime 最后一次访问的时间
     */
    void logoffByTimeout(String userId, long lastAccessedTime);

    /**
     * 正常退出
     */
    void logoff();

    /**
     * 强制踢出多个用户,并修改登录状态
     *
     * @param userIds 用户的id数组
     */
    void forceLogoff(String[] userIds);

    /**
     * 由于系统关闭而强制所有的用户下线
     */
    void logoffBySystemDown();

    /**
     * 查询所有的在线用户的id列表
     *
     * @return 用户id
     */
    List<String> queryOnlineUserIdList();

    /**
     * 查询所有在线的用户列表
     * 支持传递查询条件
     *
     * @return 登录日志列表（含用户信息）
     */
    List<LoginLogVo> queryOnlineUsers(LoginLogBo bo);
}
