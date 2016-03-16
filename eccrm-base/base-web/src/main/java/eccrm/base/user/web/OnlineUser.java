package eccrm.base.user.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael
 */
public class OnlineUser {
    private static OnlineUser ourInstance = new OnlineUser();

    private Map<String, String> users = new HashMap<String, String>();

    public static OnlineUser getInstance() {
        return ourInstance;
    }

    private OnlineUser() {
    }

    /**
     * 新增在线用户
     *
     * @param userId   用户id
     * @param username 用户名
     */
    public void add(String userId, String username) {
        if (userId == null || "".equals(userId.trim())) {
            return;
        }
        users.put(userId, username);
    }

    /**
     * 删除在线用户
     *
     * @param userId 用户id
     */
    public void remove(String userId) {
        if (userId == null || "".equals(userId.trim())) {
            return;
        }
        users.remove(userId);
    }

    /**
     * 在线用户数
     */
    public int count() {
        return users.size();
    }

    /**
     * 返回所有在线用户集合
     */
    public Map<String, String> allUsers() {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(users);
        return map;
    }
}
