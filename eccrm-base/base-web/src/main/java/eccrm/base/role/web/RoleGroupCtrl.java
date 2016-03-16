package eccrm.base.role.web;

import com.google.gson.JsonObject;
import eccrm.base.role.service.RoleGroupService;
import eccrm.base.role.vo.RoleGroupVo;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.utils.gson.JsonObjectUtils;
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
 * Created on 2014/8/3 2:27
 *
 * @author miles
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"/base/role/groups"})
public class RoleGroupCtrl {
    @Resource
    private RoleGroupService roleGroupService;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String groupId = JsonObjectUtils.getStringProperty(jsonObject, "groupId");
        String roleIds = JsonObjectUtils.getStringProperty(jsonObject, "roleIds");
        roleGroupService.save(groupId, roleIds.split(","));
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        roleGroupService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/query", params = {"groupId"}, method = RequestMethod.GET)
    public void query(@RequestParam String groupId, HttpServletResponse response) {
        List<RoleGroupVo> vos = roleGroupService.queryByGroup(groupId);
        GsonUtils.printData(response, vos);
    }
}
