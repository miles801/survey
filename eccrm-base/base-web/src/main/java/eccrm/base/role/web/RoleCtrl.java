package eccrm.base.role.web;

import eccrm.base.role.bo.RoleBo;
import eccrm.base.role.domain.Role;
import eccrm.base.role.service.RoleService;
import eccrm.base.role.vo.RoleVo;
import eccrm.core.constant.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import eccrm.utils.StringUtils;
import com.ycrl.utils.gson.GsonUtils;
import org.springframework.context.annotation.Scope;
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
 * @datetime 2014-03-26
 */

@Controller
@Scope("prototype")
@RequestMapping(value = {"/base/role"})
public class RoleCtrl extends BaseController {
    @Resource
    private RoleService roleService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/role/list/role_list";
    }

    // 授权设置页面
    @RequestMapping(value = {"/privilege"}, method = RequestMethod.GET)
    public String toPrivilege() {
        return "base/role/privilege";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/role/edit/role_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response) {
        Role role = GsonUtils.wrapDataToEntity(request, Role.class);
        roleService.save(role);
        GsonUtils.printSuccess(response);
        return null;
    }

    @RequestMapping(value = "/modify", params = "id", method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/role/edit/role_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Role role = GsonUtils.wrapDataToEntity(request, Role.class);
        roleService.update(role);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = {"/detail"}, params = "id", method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/role/edit/role_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        RoleVo vo = roleService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query")
    public void query(HttpServletRequest request, HttpServletResponse response) {
        RoleBo bo = GsonUtils.wrapDataToEntity(request, RoleBo.class);
        PageVo pageVo = roleService.query(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/queryValid", method = RequestMethod.GET)
    public void queryValid(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String groupId, HttpServletResponse response) {
        RoleBo bo = new RoleBo();
        bo.setName(StringUtils.decodeByUTF8(name));
        bo.setCode(StringUtils.decodeByUTF8(code));
        bo.setUserId(userId);
        bo.setGroupId(groupId);
        List<RoleVo> roles = roleService.queryValid(bo);
        GsonUtils.printData(response, roles);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        roleService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }


}
