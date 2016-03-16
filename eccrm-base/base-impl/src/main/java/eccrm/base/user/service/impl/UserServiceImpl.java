package eccrm.base.user.service.impl;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.hibernate.validator.ValidatorUtils;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.ResponseData;
import eccrm.base.employee.dao.EmployeeDao;
import eccrm.base.employee.domain.Employee;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.position.dao.PositionDao;
import eccrm.base.position.domain.Position;
import eccrm.base.tenement.dao.TenementDao;
import eccrm.base.user.bo.UserBo;
import eccrm.base.user.dao.PasswordPolicyDao;
import eccrm.base.user.dao.UserDao;
import eccrm.base.user.dao.UserGroupDao;
import eccrm.base.user.domain.*;
import eccrm.base.user.enums.UserStatus;
import eccrm.base.user.enums.UserType;
import eccrm.base.user.service.LoginErrorCode;
import eccrm.base.user.service.UserService;
import eccrm.base.user.service.ValidateResult;
import eccrm.base.user.vo.UserVo;
import eccrm.core.VoHelper;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import eccrm.utils.UUIDGenerator;
import eccrm.utils.md5.MD5Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-03-14
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Resource
    private UserDao userDao;
    @Resource
    private TenementDao tenementDao;
    @Resource
    private PasswordPolicyDao policyDao;

    @Resource
    private UserGroupDao userGroupDao;

    @Resource
    private EmployeeDao employeeDao;

    @Override
    public String save(User user) {
        PasswordPolicy passwordPolicy = policyDao.get();
        user.setPasswordDeadline(new PasswordPolicyHelper(passwordPolicy).nextDeadline());
        user.setPassword(MD5Utils.encode(passwordPolicy.getDefaultPassword()));
        // 设置默认的用户类型和状态
        if (user.getType() == null) {
            user.setType(UserType.TEMP.getValue());
        }
        if (user.getStatus() == null) {
            user.setStatus(UserStatus.INACTIVE.getValue());
        }
        // 设置ID
        String userId = user.getId();
        if (StringUtils.isEmpty(userId)) {
            userId = UUIDGenerator.generate();
            user.setId(userId);
        }
        userDao.save(user);

        // 保存用户与组的关联关系
        saveGroups(user, user.getGroupIds());
        return userId;
    }

    @Override
    public void register(User user) {
        Assert.notNull(user);
        Assert.hasText(user.getEmployeeName(), "注册失败：姓名不能为空!");
        // 保存员工
        Employee employee = new Employee();
        String empId = UUIDGenerator.generate();
        employee.setId(empId);
        employee.setEmployeeName(user.getEmployeeName());
        employee.setStatus("2");
        employee.setTenementId("1");
        // 设置员工的岗位（注册时默认添加到编号为TGB的岗位）
        PositionDao positionDao = SystemContainer.getInstance().getBean(PositionDao.class);
        List<Position> positions = positionDao.findByCode("TGB");
        if (positions != null && !positions.isEmpty()) {
            employee.setPositionId(positions.get(0).getId());
            employee.setPositionName(positions.get(0).getName());
            employee.setPositionCode("TGB");
        }
        employeeDao.save(employee);
        // 保存用户
        user.setEmployeeId(empId);
        user.setEmployeeName(employee.getEmployeeName());
        user.setStatus(UserStatus.ACTIVE.getValue());
        user.setTenementId("1");
        ValidatorUtils.validate(user);
        // 验证用户名是否重复
        User oldUser = userDao.findByUsername(user.getUsername());
        Assert.isNull(oldUser, "注册失败：用户名已经存在!");

        userDao.save(user);
    }

    /**
     * 根据用户id和用户组id列表批量创建关联关系
     *
     * @param user     用户
     * @param groupIds 用逗号分隔的多个用户组id
     */
    private void saveGroups(User user, String groupIds) {
        if (StringUtils.isNotEmpty(groupIds)) {
            int i = 1;
            for (String groupId : groupIds.split(",")) {
                if (StringUtils.isEmpty(groupId)) continue;
                UserGroup ug = new UserGroup();
                ug.setUser(user);
                Group group = new Group();
                group.setId(groupId);
                ug.setGroup(group);
                ug.setSequenceNo(i++);
                userGroupDao.save(ug);
            }
        }
    }

    @Override
    public void update(User user) {
        // 删除用户的所有组
        userGroupDao.deleteByUserId(user.getId());

        // 新建用户的所有组
        saveGroups(user, user.getGroupIds());
        //更新用户
        userDao.update(user);
    }

    @Override
    public PageVo query(UserBo bo) {
        PageVo vo = new PageVo();
        Long total = userDao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<User> users = userDao.query(bo);
        vo.setData(VoHelper.wrapVos(users, this));
        return vo;
    }

    @Override
    public PageVo queryValid(UserBo bo) {
        if (bo == null) {
            bo = new UserBo();
        }
        //只查询正常状态、有效期内的正式用户
        bo.setStatus(UserStatus.ACTIVE.getValue());
        bo.setValid(true);
        bo.setType(UserType.OFFICIAL.getValue());
        bo.setValid(true);
        return query(bo);
    }


    @Override
    public UserVo findById(String id) {
        return wrap(userDao.findById(id));
    }

    @Override
    public ResponseData updatePassword(String oldPwd, String newPwd) {
        Assert.hasText(oldPwd);
        Assert.hasText(newPwd);
        User user = userDao.findByUsername(SecurityContext.getUsername());
        Assert.notNull(user, "更改密码失败：用户不存在!");
        ResponseData responseData = new ResponseData();
        if (!oldPwd.equals(user.getPassword())) {
            responseData.setFail(true);
            responseData.setMessage("密码错误!");
        } else {
            PasswordPolicy passwordPolicy = policyDao.get();
            userDao.updatePassword(user.getId(), newPwd, new PasswordPolicyHelper(passwordPolicy).nextDeadline());
            responseData.setSuccess(true);
        }
        return responseData;
    }


    @Override
    public void resetPassword(String... ids) {
        if (ids == null || ids.length < 1) {
            throw new IllegalArgumentException("重置密码时,用户ID不能为空!");
        }
        PasswordPolicy policy = policyDao.get();
        if (policy == null) {
            throw new SecurityException("没有获得密码策略!");
        }
        String password = MD5Utils.encode(policy.getDefaultPassword());
        Date deadline = new PasswordPolicyHelper(policy).nextDeadline();
        for (String id : ids) {
            userDao.updatePassword(id, password, deadline);
        }
    }

    @Override
    public UserVo findByEmail(String email) {
        return wrap(userDao.findByEmail(email));
    }

    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            if ("".equals(id.trim())) continue;
            User user = userDao.findById(id);
            if (user == null) {
                throw new EntityNotFoundException("用户不存在或者已经被删除,请刷新页面后重试!");
            }
            //临时用户
            if (user.getType().equals(UserType.TEMP.getValue())) {
                userDao.delete(user);
                continue;
            }
            //正式用户：采集状态-->删除
            if (user.getStatus().equals(UserStatus.INACTIVE.getValue())) {
                userDao.delete(user);
                userGroupDao.deleteByUserId(id);// 删除用户所属组
                continue;
            }
            //正式用户：启用-->注销
            if (user.getStatus().equals(UserStatus.ACTIVE.getValue())) {
                user.setStatus(UserStatus.CANCELED.getValue());
            }
        }
    }

    @Override
    public ValidateResult validate(String username, String password, String tenementCode) {
        if (username == null || "".equals(username.trim())) {
            throw new RuntimeException("用户登录时,没有获得用户名!");
        }
        if (password == null || "".equals(password.trim())) {
            throw new RuntimeException("用户登录时,密码不能为空!");
        }
        //租户不存在
        ValidateResult result = new ValidateResult();
        String tenementId = "1";
        SecurityContext.set(null, null, tenementId);
        result.setTenementId(tenementId);
        User user = userDao.findByUsername(username);
        if (user == null) {
            result.setCode(LoginErrorCode.ERROR_USER);
            return result;
        }
        result.setUserId(user.getId());
        //用户已过期
        Date startDate = user.getStartDate();
        Long now = new Date().getTime();
        Date endDate = user.getEndDate();
        if ((startDate != null && startDate.getTime() > now) || (endDate != null && endDate.getTime() < now)) {
            result.setCode(LoginErrorCode.EXPIRED);
            return result;
        }
        // 验证用户状态
        String status = user.getStatus();
        if (UserStatus.INACTIVE.getValue().equals(status)) {
            result.setCode(LoginErrorCode.INACTIVE);
            return result;
        } else if (UserStatus.CANCELED.getValue().equals(status)) {
            result.setCode(LoginErrorCode.DISABLED);
            return result;
        } else if (UserStatus.PAUSE.getValue().equals(status)) {
            result.setCode(LoginErrorCode.PAUSE);
            return result;
        }
        //密码错误
        if (!user.getPassword().equals(password)) {
            result.setCode(LoginErrorCode.ERROR_PASSWORD);
            return result;
        }

        //密码已过期
//        PasswordPolicy policy = policyDao.get();
        Date deadline = user.getPasswordDeadline();
        if (deadline != null && new Date().getTime() > deadline.getTime()) {
            result.setCode(LoginErrorCode.PASSWORD_EXPIRED);
            return result;
        }
        //验证成功
        result.setCode(LoginErrorCode.SUCCESS);
        return result;
    }

    @Override
    public UserVo wrap(User user) {
        if (user == null) return null;
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        ParameterContainer container = ParameterContainer.getInstance();
        vo.setTypeName(container.getSystemName(SP_USER_TYPE, vo.getType()));
        vo.setStatusName(container.getSystemName(SP_USER_STATE, vo.getStatus()));
        return vo;
    }

    @Override
    public boolean checkPassword(String id, String password) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("检查用户名密码是否正确时,参数不匹配!");
        }
        User user = userDao.findById(id);
        Argument.entityNotFound(user, id);
        return user.getPassword().equals(password);
    }

    @Override
    public boolean hasName(String username) {
        return userDao.hasName(username);
    }

    @Override
    public boolean hasCode(String code) {
        return userDao.hasCode(code);
    }

    @Override
    public UserVo findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return BeanWrapBuilder.newInstance()
                .wrap(user, UserVo.class);
    }

    @Override
    public String findEmployeeId(String username) {
        return userDao.findEmployeeId(username);
    }
}
