package eccrm.base.im.web;

import com.ycrl.core.web.BaseController;
import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.im.bo.NewsRealReceiverBo;
import eccrm.base.im.domain.NewsRealReceiver;
import eccrm.base.im.service.NewsRealReceiverService;
import eccrm.base.im.vo.NewsRealReceiverVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/base/newsRealReceiver"})
public class NewsRealReceiverCtrl extends BaseController {
    @Resource
    private NewsRealReceiverService newsRealReceiverService;
    @RequestMapping(value = {""}, method=RequestMethod.GET )
    public String toList() {
        return "base/newsRealReceiver/list/newsRealReceiver_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/newsRealReceiver/edit/newsRealReceiver_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        NewsRealReceiver newsRealReceiver = GsonUtils.wrapDataToEntity(request, NewsRealReceiver.class);
        newsRealReceiverService.save(newsRealReceiver);
        GsonUtils.printSuccess(response);
    }
    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/newsRealReceiver/edit/newsRealReceiver_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        NewsRealReceiver newsRealReceiver = GsonUtils.wrapDataToEntity(request, NewsRealReceiver.class);
        newsRealReceiverService.update(newsRealReceiver);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/newsRealReceiver/edit/newsRealReceiver_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        NewsRealReceiverVo vo = newsRealReceiverService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        NewsRealReceiverBo bo = GsonUtils.wrapDataToEntity(request, NewsRealReceiverBo.class);
        PageVo pageVo = newsRealReceiverService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        newsRealReceiverService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

}
