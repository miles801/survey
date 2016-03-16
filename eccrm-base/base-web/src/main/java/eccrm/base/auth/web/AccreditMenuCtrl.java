package eccrm.base.auth.web;

import com.google.gson.JsonObject;
import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.auth.service.AccreditMenuService;
import eccrm.base.menu.vo.MenuVo;
import eccrm.core.security.LoginInfo;
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
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/auth/accreditMenu"})
public class AccreditMenuCtrl extends BaseController {
    @Resource
    private AccreditMenuService accreditMenuService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/accredit/menu/accreditMenu_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "eccrm/base/auth/accreditMenu/edit/accreditMenu_edit";
    }


    /**
     * 保存授权：
     * 必须参数：deptId：岗位/部门id
     * 必须参数：menuIds：菜单id（多个值使用逗号分隔）
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public void queryByDept(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String deptId = jsonObject.get("deptId").getAsString();
        String menuIds[] = jsonObject.get("menuIds").getAsString().split(",");
        accreditMenuService.saveByDept(deptId, menuIds);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "eccrm/base/auth/accreditMenu/edit/accreditMenu_edit";
    }

    @RequestMapping(value = {"/queryByDept"}, params = {"deptId"}, method = RequestMethod.GET)
    public void queryByDept(@RequestParam String deptId, HttpServletResponse response) {
        List<MenuVo> menuVos = accreditMenuService.queryAccreditMenus(new String[]{deptId});
        GsonUtils.printData(response, menuVos);
    }

    /**
     * 查询指定岗位/部门下的所有被授权的菜单
     * 返回权限id集合
     *
     * @param deptId 岗位/部门id
     */
    @RequestMapping(value = {"/queryIdsByDept"}, params = {"deptId"}, method = RequestMethod.GET)
    public void queryIdsByDept(@RequestParam String deptId, HttpServletResponse response) {
        List<String> menuIds = accreditMenuService.queryAccreditMenuIds(new String[]{deptId});
        GsonUtils.printData(response, menuIds);
    }

    /**
     * 查询当前登录用户的权限菜单
     */
    @RequestMapping(value = {"/queryAccreditMenu"}, method = RequestMethod.GET)
    public void queryAccreditMenu(HttpServletRequest request, HttpServletResponse response) {
        List<MenuVo> menuVos = accreditMenuService.queryPersonalAccreditMenus((String) request.getSession().getAttribute(LoginInfo.USER));
        GsonUtils.printData(response, menuVos);
    }

    /**
     * 查询当前登录用户的被授权的有效的根权限菜单（子系统以及九宫格）
     */
    @RequestMapping(value = {"/queryAccreditMenuRoot"}, method = RequestMethod.GET)
    @ResponseBody
    public void queryAccreditMenuRoot(HttpServletRequest request, HttpServletResponse response) {
        List<MenuVo> menuVos = accreditMenuService.queryPersonalAccreditMenusRoot((String) request.getSession().getAttribute(LoginInfo.USER));
        GsonUtils.printData(response, menuVos);
    }

    /**
     * 查询当前登录用户被授权的指定根节点下的所有授权且有效的导航菜单
     */
    @RequestMapping(value = {"/queryAccreditMenus"}, params = {"parentId"}, method = RequestMethod.GET)
    @ResponseBody
    public void queryValidAccreditMenu(@RequestParam String parentId, HttpServletRequest request, HttpServletResponse response) {
        List<MenuVo> menuVos = accreditMenuService.queryPersonalAccreditMenus((String) request.getSession().getAttribute(LoginInfo.USER), parentId);
        GsonUtils.printData(response, menuVos);
    }


}
