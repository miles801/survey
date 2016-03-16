package eccrm.base.handlelog.web;

import com.ycrl.core.web.BaseController;
import eccrm.core.constant.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.handlelog.bo.HandleLogBo;
import eccrm.base.handlelog.domain.HandleLog;
import eccrm.base.handlelog.service.HandleLogService;
import eccrm.base.handlelog.vo.HandleLogVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
* @author: wangsd
* @datetime: 2014-04-21
*/

@Controller
@RequestMapping(value = {"/handleLog"})
public class HandleLogCtrl extends BaseController {
    @Resource
    private HandleLogService handleLogService;

    @RequestMapping(value = {"/", ""}, method=RequestMethod.GET )
    public String toList() {
        return "base/handleLog/list/handleLog_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/handleLog/edit/handleLog_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response) {
        HandleLog handleLog = GsonUtils.wrapDataToEntity(request, HandleLog.class);
        handleLogService.save(handleLog);
        GsonUtils.printSuccess(response);
        return null;
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.GET)
    public String toModify(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/handleLog/edit/handleLog_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response) {
        HandleLog handleLog = GsonUtils.wrapDataToEntity(request, HandleLog.class);
        handleLogService.update(handleLog);
        GsonUtils.printSuccess(response);
        return null;
    }


    @RequestMapping(value = {"/{id}/detail"}, method = RequestMethod.GET)
    public String toDetail(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/handleLog/edit/handleLog_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable String id, HttpServletResponse response) {
        HandleLogVo vo = handleLogService.findById(id);
        GsonUtils.printJsonObject(response, vo);
        return null;
    }


    @ResponseBody
    @RequestMapping(value = "/query")
    public String query(HttpServletRequest request, HttpServletResponse response) {
        HandleLogBo bo = GsonUtils.wrapDataToEntity(request, HandleLogBo.class);
        PageVo pageVo = handleLogService.query(bo);
        GsonUtils.printJsonObject(response, pageVo);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"})
    public String deleteByIds(String ids, HttpServletResponse response) {
        if (ids == null || "".equals(ids.trim())) {
            throw new IllegalArgumentException("批量删除时，没有获得对应的ID列表（使用逗号分隔的字符串）!");
        }
        String[] idArr = ids.split(",");
        handleLogService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
        return null;
    }

}
