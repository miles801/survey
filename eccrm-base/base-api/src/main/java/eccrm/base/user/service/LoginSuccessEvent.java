package eccrm.base.user.service;

import eccrm.base.user.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录成功后要触发的事件
 *
 * @author Michael
 */
public interface LoginSuccessEvent {
    public void execute(HttpServletRequest request, UserVo userVo);
}
