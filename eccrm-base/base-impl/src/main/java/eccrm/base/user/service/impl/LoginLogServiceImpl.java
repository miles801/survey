package eccrm.base.user.service.impl;

import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import eccrm.base.user.bo.LoginLogBo;
import eccrm.base.user.dao.LoginLogDao;
import eccrm.base.user.domain.LoginLog;
import eccrm.base.user.domain.LogoutType;
import eccrm.base.user.domain.User;
import eccrm.base.user.service.LoginLogService;
import eccrm.base.user.vo.LoginLogVo;
import eccrm.core.VoHelper;
import eccrm.utils.date.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-04-14
 */
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {
    @Resource
    private LoginLogDao loginLogDao;

    @Override
    public String save(LoginLog loginLog) {
        return loginLogDao.save(loginLog);
    }

    @Override
    public void update(LoginLog loginLog) {
        loginLogDao.update(loginLog);
    }

    @Override
    public PageVo query(LoginLogBo bo) {
        PageVo vo = new PageVo();
        Long total = loginLogDao.getTotal(bo);
        if (total == 0) return vo;
        vo.setTotal(total);
        List<LoginLog> loginLogs = loginLogDao.query(bo);
        vo.setData(VoHelper.wrapVos(loginLogs, this));
        return vo;
    }

    @Override
    public LoginLogVo findById(String id) {
        return wrap(loginLogDao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            loginLogDao.deleteById(id);
        }
    }

    @Override
    public void logoffByTimeout(String userId, long lastAccessedTime) {
        LoginLog log = loginLogDao.last(userId);
        if (log==null || log.getLogoutDatetime() != null) {
            return;
        }
        Date logoutDatetime = new Date(lastAccessedTime);
        log.setLogoutDatetime(logoutDatetime);
        log.setDuration(DateUtils.duration(log.getCreatedDatetime(), logoutDatetime));
        log.setLogoutType(LogoutType.TIMEOUT.getValue());
    }

    @Override
    public void logoff() {
        LoginLog log = logout();
        if (log == null) return;
        log.setLogoutType(LogoutType.REGULAR.getValue());
    }

    @Override
    public void logoffBySystemDown() {
        loginLogDao.logoutAllBySystemDown();
    }

    @Override
    public List<String> queryOnlineUserIdList() {
        return loginLogDao.queryAllOnlineUserId();
    }


    @Override
    public void forceLogoff(String[] userIds) {
        if (userIds == null || userIds.length < 1) {
            return;
        }
        Date now = new Date();
        for (String userId : userIds) {
            LoginLog loginLog = loginLogDao.last(userId);
            if (loginLog != null) {
                loginLog.setLogoutDatetime(now);
                loginLog.setDuration(DateUtils.duration(loginLog.getCreatedDatetime(), now));
                loginLog.setLogoutType(LogoutType.FORCE.getValue());
            }

        }
    }

    @Override
    public List<LoginLogVo> queryOnlineUsers(LoginLogBo bo) {
        List<LoginLog> vos = loginLogDao.queryAllOnlineUser(bo);

        return VoHelper.wrapVos(vos, this);
    }

    private LoginLog logout() {
        String userId = SecurityContext.getUserId();
        LoginLog log = loginLogDao.last(userId);
        String username = SecurityContext.getUsername();
        if (log == null) {
            throw new RuntimeException("当前用户[" + username + "]还没有进行过登录!");
        }
        if (log.getLogoutDatetime() != null) {
            return null;
//            throw new RuntimeException("当前用户[" + username + "]已经退出系统!");
        }
        Date now = new Date();
        log.setLogoutDatetime(now);
        log.setDuration(DateUtils.duration(log.getCreatedDatetime(), now));
        return log;
    }

    @Override
    public LoginLogVo wrap(LoginLog loginLog) {
        if (loginLog == null) return null;
        LoginLogVo vo = new LoginLogVo();
        BeanUtils.copyProperties(loginLog, vo);
        User user = loginLog.getUser();
        vo.setPosition(user.getPosition());
        vo.setDuty(user.getDuty());
        vo.setEmployeeName(user.getEmployeeName());
        vo.setEmployeeNo(user.getEmployeeNo());
        vo.setUsername(user.getUsername());
        vo.setUserId(user.getId());
        return vo;
    }
}
