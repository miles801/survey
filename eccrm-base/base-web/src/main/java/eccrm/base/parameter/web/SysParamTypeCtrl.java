package eccrm.base.parameter.web;

import eccrm.base.parameter.bo.SysParamTypeBo;
import eccrm.base.parameter.domain.SysParamType;
import eccrm.base.parameter.service.SysParamTypeService;
import eccrm.base.parameter.vo.SysParamTypeVo;
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
 * @author: miles
 * @datetime: 2014-06-20
 */

@Controller
@RequestMapping(value = {"/base/parameter/type/system"})
public class SysParamTypeCtrl extends BaseController {
    @Resource
    private SysParamTypeService sysParamTypeService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/parameter/system/sysParamType_list";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        SysParamType sysParamType = GsonUtils.wrapDataToEntity(request, SysParamType.class);
        sysParamTypeService.save(sysParamType);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        SysParamType sysParamType = GsonUtils.wrapDataToEntity(request, SysParamType.class);
        sysParamTypeService.update(sysParamType);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        SysParamTypeVo vo = sysParamTypeService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        SysParamTypeBo bo = GsonUtils.wrapDataToEntity(request, SysParamTypeBo.class);
        PageVo pageVo = sysParamTypeService.query(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/queryUsing", method = RequestMethod.GET)
    public void queryUsing(HttpServletResponse response) {
        List<SysParamTypeVo> vos = sysParamTypeService.queryUsingTree();
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/queryValid", method = RequestMethod.GET)
    public void queryValid(HttpServletResponse response) {
        List<SysParamTypeVo> vos = sysParamTypeService.queryValidTree();
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public void allForTree(HttpServletRequest request, HttpServletResponse response) {
        List<SysParamTypeVo> tree = sysParamTypeService.allForTree();
        GsonUtils.printData(response, tree);
    }

    @ResponseBody
    @RequestMapping(value = "/children", params = {"id"}, method = RequestMethod.GET)
    public void children(@RequestParam String id, HttpServletResponse response) {
        List<SysParamTypeVo> tree = sysParamTypeService.queryChildren(id, true);
        GsonUtils.printData(response, tree);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        sysParamTypeService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/hasCode", params = "code", method = RequestMethod.GET)
    public void hasCode(@RequestParam String code, HttpServletResponse response) {
        boolean has = sysParamTypeService.hasCode(StringUtils.decodeByUTF8(code));
        GsonUtils.printData(response, has);
    }

    /**
     * 查询指定层级下的名称是否重复
     * 必须参数：name
     * 可选参数：id
     */
    @ResponseBody
    @RequestMapping(value = "/hasName", params = {"name"}, method = RequestMethod.GET)
    public void hasName(@RequestParam(required = false) String id,
                        @RequestParam String name, HttpServletResponse response) {
        boolean has = sysParamTypeService.hasName(id, StringUtils.decodeByUTF8(name));
        GsonUtils.printData(response, has);
    }

    /**
     * 查询非当前节点及子节点的所有数据（选择上级菜单时用）
     * 返回树形[]数据
     *
     * @param cid 当前节点的id
     */
    @ResponseBody
    @RequestMapping(value = "/other", method = RequestMethod.GET)
    public void queryOther(@RequestParam(required = false) String cid, HttpServletResponse response) {
        List<SysParamTypeVo> vos = sysParamTypeService.queryOther(cid);
        GsonUtils.printData(response, vos);
    }

    /**
     * 根据系统参数类型的编号获取对应的名称
     *
     * @param code 基础参数类型的编号
     */
    @ResponseBody
    @RequestMapping(value = "/name", params = "code", method = RequestMethod.GET)
    public void getSystemName(@RequestParam String code, HttpServletResponse response) {
        String name = sysParamTypeService.getName(code);
        GsonUtils.printData(response, name);
    }
}
