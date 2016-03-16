package eccrm.base.user.web;

import com.google.gson.JsonObject;
import eccrm.base.user.service.UserGroupService;
import eccrm.base.user.vo.UserGroupVo;
import eccrm.utils.Argument;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.utils.gson.JsonObjectUtils;
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
 * 用户与组的关联关系
 * Created on 2014/8/12 4:00
 */
@Controller
@RequestMapping(value = {"/base/usergroup"})
public class UserGroupCtrl {

    @Resource
    private UserGroupService userGroupService;

    /**
     * 查询指定用户组下的所有用户信息
     *
     * @param groupId  用户组id
     * @param response {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = {"/queryUsers"}, params = "groupId", method = RequestMethod.GET)
    public void queryUsers(@RequestParam String groupId, HttpServletResponse response) {
        List<UserGroupVo> users = userGroupService.queryUser(groupId);
        GsonUtils.printData(response, users);
    }

    /**
     * 查询指定用户的所有组
     *
     * @param userId   用户id
     * @param response {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = {"/queryGroups"}, params = "userId", method = RequestMethod.GET)
    public void queryGroups(@RequestParam String userId, HttpServletResponse response) {
        List<UserGroupVo> groups = userGroupService.queryGroup(userId);
        GsonUtils.printData(response, groups);
    }

    /**
     * 删除指定组下的所有用户
     *
     * @param groupId 用户组id
     */
    @ResponseBody
    @RequestMapping(value = {"/deleteByGroup"}, params = {"groupId"}, method = RequestMethod.DELETE)
    public void deleteByGroupId(@RequestParam String groupId, HttpServletResponse response) {
        userGroupService.deleteByGroupId(groupId);
        GsonUtils.printSuccess(response);
    }

    /**
     * 删除指定用户的所有组
     *
     * @param userId 用户id
     */
    @ResponseBody
    @RequestMapping(value = {"/deleteByUser"}, params = {"userId"}, method = RequestMethod.DELETE)
    public void deleteByUser(@RequestParam String userId, HttpServletResponse response) {
        userGroupService.deleteByUserId(userId);
        GsonUtils.printSuccess(response);
    }


    /**
     * 保存一组用户和组的关系
     * <p/>
     * 必须参数：
     * groupId 组id
     * userIds 用逗号分隔多个用户id的字符串
     */
    @ResponseBody
    @RequestMapping(value = {"/save"}, params = {"groupId", "userIds"}, method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String groupId = JsonObjectUtils.getStringProperty(jsonObject, "groupId");
        Argument.isEmpty(groupId, "保存用户与组的关系时,组ID不能为空!");
        String userIds = JsonObjectUtils.getStringProperty(jsonObject, "userIds");
        Argument.isEmpty(userIds, "保存用户与组的关系时,用户ID不能为空!");
        userGroupService.saveByGroup(groupId, userIds.split(","));
        GsonUtils.printSuccess(response);
    }
}


