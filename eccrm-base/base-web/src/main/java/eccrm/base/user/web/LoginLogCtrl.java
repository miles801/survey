package eccrm.base.user.web;

import eccrm.base.user.bo.LoginLogBo;
import eccrm.base.user.service.LoginLogService;
import eccrm.base.user.vo.LoginLogVo;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-04-14
 */

@Controller
@RequestMapping(value = {"/base/user/loginlog"})
public class LoginLogCtrl extends BaseController {
    @Resource
    private LoginLogService loginLogService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/user/loginlog/loginlog";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public String findById(@RequestParam String id, HttpServletResponse response) {
        LoginLogVo vo = loginLogService.findById(id);
        GsonUtils.printJsonObject(response, vo);
        return null;
    }


    /**
     * 登录日志查询
     */
    @ResponseBody
    @RequestMapping(value = "/query")
    public String query(HttpServletRequest request, HttpServletResponse response) {
        LoginLogBo bo = GsonUtils.wrapDataToEntity(request, LoginLogBo.class);
        PageVo pageVo = loginLogService.query(bo);
        GsonUtils.printJsonObject(response, pageVo);
        return null;
    }

    /**
     * 查询所有在线的用户
     * 高级查询条件可以通过POST的方式传递
     */
    @ResponseBody
    @RequestMapping(value = "/onlineUsers", method = RequestMethod.POST)
    public void onlineUsers(HttpServletRequest request, HttpServletResponse response) {
        LoginLogBo bo = GsonUtils.wrapDataToEntity(request, LoginLogBo.class);
        List<LoginLogVo> vos = loginLogService.queryOnlineUsers(bo);
        GsonUtils.printData(response, vos);
    }

}
