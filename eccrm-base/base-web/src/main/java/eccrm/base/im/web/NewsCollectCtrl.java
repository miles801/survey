package eccrm.base.im.web;

import com.ycrl.core.web.BaseController;
import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.im.bo.NewsCollectBo;
import eccrm.base.im.domain.NewsCollect;
import eccrm.base.im.service.NewsCollectService;
import eccrm.base.im.vo.NewsCollectVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/base/newsCollect"})
public class NewsCollectCtrl extends BaseController {
    @Resource
    private NewsCollectService newsCollectService;
    @RequestMapping(value = {""}, method=RequestMethod.GET )
    public String toList() {
        return "base/newsCollect/list/newsCollect_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/newsCollect/edit/newsCollect_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        NewsCollect newsCollect = GsonUtils.wrapDataToEntity(request, NewsCollect.class);
        newsCollectService.save(newsCollect);
        GsonUtils.printSuccess(response);
    }
    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/newsCollect/edit/newsCollect_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        NewsCollect newsCollect = GsonUtils.wrapDataToEntity(request, NewsCollect.class);
        newsCollectService.update(newsCollect);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/newsCollect/edit/newsCollect_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        NewsCollectVo vo = newsCollectService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        NewsCollectBo bo = GsonUtils.wrapDataToEntity(request, NewsCollectBo.class);
        PageVo pageVo = newsCollectService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        newsCollectService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

}
