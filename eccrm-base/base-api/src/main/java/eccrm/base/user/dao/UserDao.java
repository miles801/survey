package eccrm.base.user.dao;

import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.user.bo.UserBo;
import eccrm.base.user.domain.User;

import java.util.Date;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-03-14
 */
public interface UserDao {

    @LogInfo(type = OperateType.ADD, describe = "用户-新增")
    String save(User user);

    @LogInfo(type = OperateType.MODIFY, describe = "用户-修改")
    void update(User user);

    List<User> query(UserBo bo);

    Long getTotal(UserBo bo);

    User findById(String id);

    String getName(String id);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     */
    User findByUsername(String username);

    /**
     * 根据用户名查询员工id
     *
     * @param username 用户名
     */
    String findEmployeeId(String username);

    public User findByEmail(String email);

    @LogInfo(type = OperateType.DELETE, describe = "用户-删除")
    void delete(User user);

    /**
     * 更新密码
     *
     * @param id       用户id
     * @param password 使用md5加密的密码
     * @param deadline 下一个截止时间
     */
    boolean updatePassword(String id, String password, Date deadline);

    /**
     * 检测用户名是否存在
     * 存在：true  不存在：false
     */
    boolean hasName(String username);

    /**
     * 编号是否存在
     * 存在：true  不存在：false
     */
    boolean hasCode(String code);

}
