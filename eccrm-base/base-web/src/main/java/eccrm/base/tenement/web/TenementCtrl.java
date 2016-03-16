package eccrm.base.tenement.web;

import eccrm.base.tenement.bo.TenementBo;
import eccrm.base.tenement.domain.Tenement;
import eccrm.base.tenement.service.TenementService;
import eccrm.base.tenement.vo.TenementVo;
import eccrm.core.constant.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: miles
 * @datetime: 2014-03-14
 */

@Controller
@RequestMapping(value = {"/base/tenement"})
public class TenementCtrl extends BaseController {
    @Resource
    private TenementService tenementService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/tenement/list/tenement_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/tenement/edit/tenement_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response) {
        Tenement tenement = GsonUtils.wrapDataToEntity(request, Tenement.class);
        String id = tenementService.save(tenement);
        GsonUtils.printJson(response, "id", id);
        return null;
    }

    @RequestMapping(value = "/modify", params = "id", method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/tenement/edit/tenement_edit";
    }

    //关闭租户
    @RequestMapping(value = "/close", params = "id", method = RequestMethod.POST)
    @ResponseBody
    public String close(@RequestParam String id, HttpServletResponse response) {
        tenementService.close(id);
        GsonUtils.printSuccess(response);
        return null;
    }

    //暂停租户
    @RequestMapping(value = "/pause", params = "id", method = RequestMethod.POST)
    @ResponseBody
    public String pause(@RequestParam String id, HttpServletResponse response) {
        tenementService.pause(id);
        GsonUtils.printSuccess(response);
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response) {
        Tenement tenement = GsonUtils.wrapDataToEntity(request, Tenement.class);
        tenementService.update(tenement);
        GsonUtils.printSuccess(response);
        return null;
    }


    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/tenement/edit/tenement_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public String findById(@RequestParam String id, HttpServletResponse response) {
        TenementVo vo = tenementService.findById(id);
        GsonUtils.printJsonObject(response, vo);
        return null;
    }


    @ResponseBody
    @RequestMapping(value = "/query")
    public String query(HttpServletRequest request, HttpServletResponse response) {
        TenementBo bo = GsonUtils.wrapDataToEntity(request, TenementBo.class);
        PageVo pageVo = tenementService.query(bo);
        GsonUtils.printJsonObject(response, pageVo);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"})
    public String deleteByIds(String ids, HttpServletResponse response) {
        if (ids == null || "".equals(ids.trim())) {
            throw new RuntimeException("ids");
        }
        String[] idArr = ids.split(",");
        tenementService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
        return null;
    }

}
