package eccrm.base.im.web;

import com.ycrl.core.web.BaseController;
import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.im.bo.NewsReceiverBo;
import eccrm.base.im.domain.NewsReceiver;
import eccrm.base.im.service.NewsReceiverService;
import eccrm.base.im.vo.NewsReceiverVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/base/newsReceiver"})
public class NewsReceiverCtrl extends BaseController {
    @Resource
    private NewsReceiverService newsReceiverService;
    @RequestMapping(value = {""}, method=RequestMethod.GET )
    public String toList() {
        return "base/newsReceiver/list/newsReceiver_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/newsReceiver/edit/newsReceiver_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        NewsReceiver newsReceiver = GsonUtils.wrapDataToEntity(request, NewsReceiver.class);
        newsReceiverService.save(newsReceiver);
        GsonUtils.printSuccess(response);
    }
    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/newsReceiver/edit/newsReceiver_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        NewsReceiver newsReceiver = GsonUtils.wrapDataToEntity(request, NewsReceiver.class);
        newsReceiverService.update(newsReceiver);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/newsReceiver/edit/newsReceiver_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        NewsReceiverVo vo = newsReceiverService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        NewsReceiverBo bo = GsonUtils.wrapDataToEntity(request, NewsReceiverBo.class);
        PageVo pageVo = newsReceiverService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        newsReceiverService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

}
