package eccrm.base.user.web;

import eccrm.base.user.service.LoginLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登出，提供退出系统、强制踢出用户下线的功能
 *
 * @author miles
 * @datetime 2014/6/20 11:31
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/logout")
public class LogoutCtrl {
    @Resource
    private LoginLogService loginLogService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

}
