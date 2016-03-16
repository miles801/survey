package eccrm.base.role.web;

import com.google.gson.JsonObject;
import eccrm.base.menu.vo.MenuVo;
import eccrm.base.role.service.RoleResourceService;
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
@RequestMapping(value = {"/base/role/resources"})
public class RoleResourceCtrl {
    @Resource
    private RoleResourceService roleResourceService;

    /**
     * 保存角色与资源的关联关系
     * 参数：
     * roleId：角色id
     * resourceIds：资源id数组
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String roleId = JsonObjectUtils.getStringProperty(jsonObject, "roleId");
        String resourceIds = JsonObjectUtils.getStringProperty(jsonObject, "resourceIds");
        roleResourceService.save(roleId, resourceIds.split(","));
        GsonUtils.printSuccess(response);
    }

    /**
     * 根据id列表删除
     *
     * @param ids 使用逗号分隔的多个id字符串
     */
    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        roleResourceService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    /**
     * 根据角色id删除对应的所有资源
     *
     * @param roleId 角色id
     */
    @ResponseBody
    @RequestMapping(value = "/delete", params = {"roleId"}, method = RequestMethod.DELETE)
    public void deleteByRoleId(@RequestParam String roleId, HttpServletResponse response) {
        roleResourceService.deleteByRoleId(roleId);
        GsonUtils.printSuccess(response);
    }


    /**
     * 根据角色id查询所有的资源集合
     *
     * @param roleId 角色id
     */
    @ResponseBody
    @RequestMapping(value = "/query", params = {"roleId"}, method = RequestMethod.GET)
    public void queryByRoleId(@RequestParam String roleId, HttpServletResponse response) {
        List<MenuVo> vos = roleResourceService.queryByRoleId(roleId);
        GsonUtils.printData(response, vos);
    }

    /**
     * 根据用户id查询所有的资源集合
     *
     * @param userId 用户id
     */
    @ResponseBody
    @RequestMapping(value = "/query", params = {"userId"}, method = RequestMethod.GET)
    public void queryByUserId(@RequestParam String userId, HttpServletResponse response) {
        List<MenuVo> vos = roleResourceService.queryByUserId(userId);
        GsonUtils.printData(response, vos);
    }
}
