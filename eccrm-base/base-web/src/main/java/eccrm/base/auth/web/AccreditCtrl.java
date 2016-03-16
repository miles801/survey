package eccrm.base.auth.web;

import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import eccrm.base.auth.bo.AccreditBo;
import eccrm.base.auth.domain.Accredit;
import eccrm.base.auth.service.AccreditService;
import eccrm.base.auth.vo.AccreditVo;
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
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/auth/accredit"})
public class AccreditCtrl extends BaseController {
    @Resource
    private AccreditService accreditService;

    /**
     * 跳转到授权页面
     */
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/accredit/accreditPosition";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "eccrm/base/auth/accredit/edit/accredit_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Accredit accredit = GsonUtils.wrapDataToEntity(request, Accredit.class);
        accreditService.save(accredit);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "eccrm/base/auth/accredit/edit/accredit_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Accredit accredit = GsonUtils.wrapDataToEntity(request, Accredit.class);
        accreditService.update(accredit);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "eccrm/base/auth/accredit/edit/accredit_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        AccreditVo vo = accreditService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        AccreditBo bo = GsonUtils.wrapDataToEntity(request, AccreditBo.class);
        PageVo pageVo = accreditService.query(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        accreditService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

}
