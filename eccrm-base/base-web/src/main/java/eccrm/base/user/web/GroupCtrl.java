package eccrm.base.user.web;

import eccrm.base.user.bo.GroupBo;
import eccrm.base.user.domain.Group;
import eccrm.base.user.service.GroupService;
import eccrm.base.user.vo.GroupVo;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import eccrm.utils.StringUtils;
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
 * @datetime 2014-07-03
 */

@Controller
@RequestMapping(value = {"/base/group"})
public class GroupCtrl extends BaseController {

    @Resource
    private GroupService service;


    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/usergroup/list/usergroup_list";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Group group = GsonUtils.wrapDataToEntity(request, Group.class);
        service.save(group);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Group group = GsonUtils.wrapDataToEntity(request, Group.class);
        service.update(group);
        GsonUtils.printSuccess(response);
    }






    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        GroupVo vo = service.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        GroupBo bo = GsonUtils.wrapDataToEntity(request, GroupBo.class);
        PageVo pageVo = service.query(bo);
        GsonUtils.printData(response, pageVo);
    }

    /**
     * 查询有效的用户组，并组装成树形
     */
    @ResponseBody
    @RequestMapping(value = "/validTree", method = RequestMethod.GET)
    public void queryValid(HttpServletRequest request, HttpServletResponse response) {
        List<GroupVo> vos = service.validTree();
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.POST)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        service.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/hasName", params = {"name"}, method = RequestMethod.GET)
    public void hasName(@RequestParam(required = false) String id,
                        @RequestParam String name, HttpServletResponse response) {
        String decodeName = StringUtils.decodeByUTF8(name);
        GsonUtils.printData(response, service.hasName(id, decodeName));
    }


    @ResponseBody
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public void tree(HttpServletResponse response) {
        List<GroupVo> tree = service.tree();
        GsonUtils.printData(response, tree);
    }


}
