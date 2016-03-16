package eccrm.base.user.web;

import com.google.gson.JsonObject;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.utils.gson.JsonObjectUtils;
import com.ycrl.utils.gson.ResponseData;
import eccrm.base.user.bo.UserBo;
import eccrm.base.user.domain.User;
import eccrm.base.user.service.UserService;
import eccrm.base.user.vo.UserVo;
import eccrm.core.constant.JspAccessType;
import eccrm.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author miles
 * @datetime 2014-03-14
 */

@Controller
@RequestMapping(value = {"/base/user"})
public class UserCtrl extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/user/list/user_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/user/edit/user_edit";
    }

    // 跳转到注册页面
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegister() {
        return "base/user/user_register";
    }

    // 注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(HttpServletRequest request, HttpServletResponse response) {
        User user = GsonUtils.wrapDataToEntity(request, User.class);
        userService.register(user);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        User user = GsonUtils.wrapDataToEntity(request, User.class);
        String id = userService.save(user);
        GsonUtils.printData(response, id);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/user/edit/user_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        User user = GsonUtils.wrapDataToEntity(request, User.class);
        userService.update(user);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/user/edit/user_edit";
    }

    /**
     * 判断指定名称的用户是否存在
     *
     * @param name 使用encodeURI进行两次编码
     */
    @ResponseBody
    @RequestMapping(value = "/hasName", params = "name", method = RequestMethod.GET)
    public void hasName(@RequestParam String name, HttpServletResponse response) {
        String decodedName = StringUtils.decodeByUTF8(name);
        GsonUtils.printData(response, userService.hasName(decodedName));
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        UserVo vo = userService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        UserBo bo = GsonUtils.wrapDataToEntity(request, UserBo.class);
        PageVo pageVo = userService.query(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/queryValid", method = RequestMethod.POST)
    public void queryValid(HttpServletRequest request, HttpServletResponse response) {
        UserBo bo = GsonUtils.wrapDataToEntity(request, UserBo.class);
        PageVo pageVo = userService.queryValid(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        userService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    /**
     * 检查当前用户的密码是否正确
     * 返回{success:true/false}
     */
    @RequestMapping(value = "/check-pwd", method = RequestMethod.POST)
    @ResponseBody
    public void checkPassword(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String password = jsonObject.get("password").getAsString();
        GsonUtils.printData(response, userService.checkPassword(SecurityContext.getUserId(), password));
    }

    /**
     * 更新当前登录用户的密码
     * 返回{success:true}
     */
    @RequestMapping(value = "/update-pwd", method = RequestMethod.POST)
    @ResponseBody
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String oldPwd = JsonObjectUtils.getStringProperty(jsonObject, "oldPwd");
        String newPwd = JsonObjectUtils.getStringProperty(jsonObject, "newPwd");
        ResponseData responseData = userService.updatePassword(oldPwd, newPwd);
        GsonUtils.printJsonObject(response, responseData);
    }

    @RequestMapping(value = "/reset-pwd", method = RequestMethod.POST, params = "ids")
    @ResponseBody
    public void resetPassword(@RequestParam String ids, HttpServletResponse response) {
        userService.resetPassword(ids.split(","));
        GsonUtils.printSuccess(response);
    }

}
