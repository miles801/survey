package eccrm.base.menu.web;

import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.utils.string.StringUtils;
import eccrm.base.menu.bo.ResourceBo;
import eccrm.base.menu.domain.Resource;
import eccrm.base.menu.service.ResourceService;
import eccrm.base.menu.vo.ResourceVo;
import eccrm.core.constant.JspAccessType;
import com.ycrl.core.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * 菜单的web层
 * Created by miles on 13-11-22.
 */
@Controller
@RequestMapping(value = {"/base/resource"})
public class ResourceCtrl extends BaseController {

    public static final String PAGE_TYPE = "pageType";

    @javax.annotation.Resource
    private ResourceService resourceService;

    /**
     * 跳转到Resource列表页面
     */
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/resource/resource_list";
    }


    /**
     * 根据id查询所有的叶子节点（包括隔代节点）
     * 返回{data:[]}
     *
     * @param id 当前节点
     */
    @ResponseBody
    @RequestMapping(value = "/children", params = {"id"}, method = RequestMethod.GET)
    public void allChildren(@RequestParam String id, HttpServletResponse response) {
        PageVo vo = resourceService.queryAllChildren(id);
        GsonUtils.printData(response, vo);
    }

    /**
     * 根据条件查询所有符合条件的菜单，并组装成树，返回
     * {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public void tree(HttpServletResponse response, HttpServletRequest request) {
        ResourceBo bo = GsonUtils.wrapDataToEntity(request, ResourceBo.class);
        List<ResourceVo> resources = resourceService.tree(bo);
        GsonUtils.printData(response, resources);
    }

    /**
     * 查询所有有效的功能集合，并构建成树
     * {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = {"/queryValid"}, method = RequestMethod.GET)
    public void queryValid(HttpServletResponse response) {
        List<ResourceVo> resources = resourceService.queryValid();
        GsonUtils.printData(response, resources);
    }

    /**
     * 查询所有有效的菜单，并构建成树
     * 导航时用
     * {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = {"/queryValidResource"}, method = RequestMethod.GET)
    public void queryValidResource(HttpServletResponse response) {
        List<ResourceVo> resources = resourceService.queryAllValidMenu();
        GsonUtils.printData(response, resources);
    }

    /**
     * 查询所有有效的页面元素资源
     * 一般用于授权时的选择
     * {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = {"/queryAllValidElement"}, method = RequestMethod.GET)
    public void queryAllValidElement(HttpServletResponse response) {
        List<ResourceVo> resources = resourceService.queryAllValidElement();
        GsonUtils.printData(response, resources);
    }

    /**
     * 查询所有有效的数据资源
     * 一般用于授权时的选择
     * {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = {"/queryAllValidData"}, method = RequestMethod.GET)
    public void queryAllValidData(HttpServletResponse response) {
        List<ResourceVo> resources = resourceService.queryAllValidData();
        GsonUtils.printData(response, resources);
    }

    /**
     * 查询所有有效的菜单，并构建成树
     * 可选参数id
     * {success:true,data:[]}
     */
    @ResponseBody
    @RequestMapping(value = {"/queryOther"}, method = RequestMethod.GET)
    public void queryOther(@RequestParam(required = false) String id, HttpServletResponse response) {
        List<ResourceVo> resources = resourceService.queryOtherTree(id);
        GsonUtils.printData(response, resources);
    }

    /**
     * 根据id查询具体的某一个菜单
     */
    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        ResourceVo resourceVo = resourceService.findById(id);
        if (resourceVo == null) {
            throw new EntityNotFoundException(id);
        }
        GsonUtils.printData(response, resourceVo);
    }

    /**
     * 跳转到Resource编辑页面（新增）
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(PAGE_TYPE, JspAccessType.ADD);
        return "base/resource/resource_edit";
    }

    /**
     * 保存菜单
     * 返回保存的结果,json对象
     * 保存成功：{success:true,id:1}
     * 保存失败:{fail:'失败的错误信息',exception:'异常信息'}
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response) {
        Resource resource = GsonUtils.wrapDataToEntity(request, Resource.class);
        resourceService.save(resource);
        GsonUtils.printSuccess(response);
        return null;
    }

    /**
     * 跳转到更新页面
     * 并设置参数
     * {id:业务主键,pageType:'modify'}
     */
    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/resource/resource_edit";
    }

    /**
     * 跳转到查看页面
     * 并设置参数
     * { id:业务主键, pageType:'detail' }
     */
    @RequestMapping(value = "/detail", params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/resource/resource_edit";
    }

    /**
     * 更新操作，返回更新结果的json数据：
     * 更新成功：{success:true}
     * 更新失败：{fail:"更新失败的错误信息"}
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(HttpServletRequest request, HttpServletResponse response) {
        Resource resource = GsonUtils.wrapDataToEntity(request, Resource.class);
        resourceService.update(resource);
        GsonUtils.printJson(response, "success", true);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public String delete(@RequestParam String ids, HttpServletResponse response) {
        if (ids == null || "".equals(ids.trim())) {
            throw new InvalidParameterException("删除时请指定要删除的id列表(多个值使用逗号进行分隔）");
        }
        String[] resourceIds = ids.split(",");
        resourceService.delete(resourceIds);
        GsonUtils.printSuccess(response);
        return null;
    }

    /**
     * @param name 必须，使用URIEncoder()编码两次的名称
     */
    @ResponseBody
    @RequestMapping(value = "/hasName", params = {"name"}, method = RequestMethod.GET)
    public void hasName(
            @RequestParam(required = false) String id,
            @RequestParam String name, HttpServletResponse response) {
        String decodedName = StringUtils.decodeByUTF8(name);
        GsonUtils.printData(response, resourceService.hasName(null, decodedName));
    }


    /**
     * @param code 必须，使用URIEncoder()编码两次的名称
     */
    @ResponseBody
    @RequestMapping(value = "/hasCode", params = {"code"}, method = RequestMethod.GET)
    public void hasCode(@RequestParam String code, HttpServletResponse response) {
        String decodedName = StringUtils.decodeByUTF8(code);
        GsonUtils.printData(response, resourceService.hasCode(decodedName));
    }

}
