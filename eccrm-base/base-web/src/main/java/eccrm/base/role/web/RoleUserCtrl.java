package eccrm.base.role.web;

import com.google.gson.JsonObject;
import eccrm.base.role.service.RoleUserService;
import eccrm.base.role.vo.RoleUserVo;
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
 * Created on 2014/8/3 2:39
 *
 * @author miles
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"/base/role/users"})
public class RoleUserCtrl {
    @Resource
    private RoleUserService roleUserService;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String userId = JsonObjectUtils.getStringProperty(jsonObject, "userId");
        String roleIds = JsonObjectUtils.getStringProperty(jsonObject, "roleIds");
        roleUserService.save(userId, roleIds.split(","));
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        roleUserService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/query", params = {"userId"}, method = RequestMethod.GET)
    public void query(@RequestParam String userId, HttpServletResponse response) {
        List<RoleUserVo> vos = roleUserService.queryByUserId(userId);
        GsonUtils.printData(response, vos);
    }
}
