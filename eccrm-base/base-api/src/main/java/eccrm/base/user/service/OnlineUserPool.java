package eccrm.base.user.service;

import com.ycrl.core.context.SecurityContext;
import eccrm.base.user.vo.UserVo;
import eccrm.utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 在线用户池
 * 提供获取在线用户、踢出用户（不对session进行操作）
 * Created by Michael on 2014/9/2.
 */
public class OnlineUserPool {
    public static final String DEFAULT_TENEMENT_ID = "default_tenement";
    private static OnlineUserPool ourInstance = new OnlineUserPool();

    private Map<String, Set<UserVo>> pool = new HashMap<String, Set<UserVo>>();

    public static OnlineUserPool getInstance() {
        return ourInstance;
    }

    private OnlineUserPool() {
    }

    /**
     * 添加在线用户
     *
     * @param userVo 用户信息
     */
    public void add(UserVo userVo) {
        String tenementId = SecurityContext.getTenementId();
        Set<UserVo> vos = pool.get(tenementId);
        if (vos == null) {
            vos = new HashSet<UserVo>();
            pool.put(tenementId, vos);
        }
        vos.add(userVo);
    }

    /**
     * 从在线池中踢出用户
     *
     * @param userId 用户id
     */
    public void remove(String userId) {
        String tenementId = SecurityContext.getTenementId();
        Set<UserVo> vos = pool.get(tenementId);
        if (vos != null) {
            UserVo vo = new UserVo();
            vo.setId(userId);// 必须要求UserVo实现equals和hasCode方法，并通过id判断两个对象是否相等
            vos.remove(vo);
        }
    }

    /**
     * 查询所有的在线用户集合
     *
     * @return 如果没有返回null
     */
    public Set<UserVo> onlineUsers() {
        return pool.get(SecurityContext.getTenementId());
    }

    /**
     * 查询用户是否在线
     * 如果userId为空，返回false
     *
     * @param userId 用户id
     * @return true在线，false不在线
     */
    public boolean isOnline(String userId) {
        if (StringUtils.isEmpty(userId)) return false;
        String tenementId = SecurityContext.getTenementId();
        Set<UserVo> vos = pool.get(tenementId);
        if (vos == null) return false;
        UserVo vo = new UserVo();
        vo.setId(userId);
        return vos.contains(vo);
    }

}
