package eccrm.base.user.service;

import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.ResponseData;
import eccrm.base.user.bo.UserBo;
import eccrm.base.user.domain.User;
import eccrm.base.user.vo.UserVo;
import eccrm.core.VoWrapper;

/**
 * @author miles
 * @datetime 2014-03-14
 */
public interface UserService extends VoWrapper<User, UserVo> {
    public static final String SP_USER_TYPE = "SP_USER_TYPE";
    public static final String SP_USER_STATE = "SP_USER_STATE";

    /**
     * 保存的时候会初始化密码；
     * 如果没有设置用户类型，默认为临时用户；
     * 如果没有设置用户状态，默认为采集状态
     * 如果没有指定ID，则会使用UUID随机生成一个
     */
    String save(User user);

    /**
     * 注册
     * 同时保存用户和员工信息
     *
     * @param user 用户
     */
    void register(User user);

    void update(User user);

    PageVo query(UserBo bo);

    /**
     * 查询在有效期内、状态为启用的所有用户
     *
     * @param bo 高级查询条件
     */
    PageVo queryValid(UserBo bo);

    UserVo findById(String id);

    /**
     * 更改当前用户的密码
     *
     * @param oldPwd 原始密码
     * @param newPwd 新密码
     */
    ResponseData updatePassword(String oldPwd, String newPwd);

    /**
     * 批量删除用户：
     * 如果用户的类型为临时用户，则直接删除
     * 如果用户的类型为正式用户：状态为采集中的，直接删除；状态为启用的，状态改为注销；状态为注销的，不做操作
     *
     * @param ids 使用逗号分隔的多个id字符串
     */
    void deleteByIds(String... ids);

    void resetPassword(String... ids);

    /**
     * 检查指定用户的密码是否正确
     *
     * @param id       用户id
     * @param password md5加密的密码
     */
    boolean checkPassword(String id, String password);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱地址
     */
    UserVo findByEmail(String email);

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

    UserVo findByUsername(String username);

    ValidateResult validate(String username, String password, String tenementCode);

    /**
     * 根据用户名查询员工id
     *
     * @param username 用户名
     */
    String findEmployeeId(String username);
}
