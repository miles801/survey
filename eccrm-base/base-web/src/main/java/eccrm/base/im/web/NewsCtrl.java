package eccrm.base.im.web;

import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.im.bo.NewsBo;
import eccrm.base.im.bo.NewsRealReceiverBo;
import eccrm.base.im.service.NewsRealReceiverService;
import eccrm.base.im.service.NewsService;
import eccrm.base.im.vo.NewsVo;
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
@RequestMapping(value = {"/base/news"})
public class NewsCtrl extends BaseController {
    @Resource
    private NewsService newsService;

    @Resource
    private NewsRealReceiverService newsRealReceiverService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/im/news/news_list";
    }

    @RequestMapping(value = {"/unread"}, method = RequestMethod.GET)
    public String toUnread() {
        return "base/im/news/news_unread";
    }

    /**
     * 没有查询条件的未读公告
     */
    @RequestMapping(value = {"/unread/list"}, method = RequestMethod.GET)
    public String toUnread2() {
        return "base/im/news/news_unread_list";
    }


    @RequestMapping(value = {"/read"}, method = RequestMethod.GET)
    public String toRead() {
        return "base/im/news/news_read";
    }

    @RequestMapping(value = {"/star"}, method = RequestMethod.GET)
    public String toStar() {
        return "base/im/news/news_star";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "base/im/news/news_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        NewsTemp temp = GsonUtils.wrapDataToEntity(request, NewsTemp.class);
        newsService.save(temp.getNews(), temp.getReceivers());
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "base/im/news/news_edit";
    }

    /**
     * 发布
     */
    @RequestMapping(value = "/publish", params = {"id"}, method = RequestMethod.POST)
    public void publish(@RequestParam String id, HttpServletResponse response) {
        newsService.publish(id);
        NewsVo news = newsService.findById(id);
        GsonUtils.printSuccess(response);
    }


    /**
     * 注销
     */
    @RequestMapping(value = "/cancel", params = {"id"}, method = RequestMethod.POST)
    public void cancel(@RequestParam String id, HttpServletResponse response) {
        newsService.cancel(id);
        GsonUtils.printSuccess(response);
    }

    /**
     * 置顶
     */
    @RequestMapping(value = "/markTop", params = {"id"}, method = RequestMethod.POST)
    public void markTop(@RequestParam String id, HttpServletResponse response) {
        newsService.markTop(id);
        GsonUtils.printSuccess(response);
    }

    /**
     * 标记为已读
     */
    @RequestMapping(value = "/markRead", params = {"id"}, method = RequestMethod.POST)
    public void markRead(@RequestParam String id, HttpServletResponse response) {
        newsRealReceiverService.read(id);
        GsonUtils.printSuccess(response);
    }

    /**
     * 标记为收藏
     */
    @RequestMapping(value = "/markStar", params = {"id"}, method = RequestMethod.POST)
    public void markStar(@RequestParam String id, HttpServletResponse response) {
        newsRealReceiverService.star(id);
        GsonUtils.printSuccess(response);
    }

    /**
     * 取消收藏
     */
    @RequestMapping(value = "/removeStar", params = {"id"}, method = RequestMethod.POST)
    public void removeStar(@RequestParam String id, HttpServletResponse response) {
        newsRealReceiverService.removeStar(id);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        NewsTemp temp = GsonUtils.wrapDataToEntity(request, NewsTemp.class);
        newsService.update(temp.getNews(), temp.getReceivers());
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "base/im/news/news_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        NewsVo vo = newsService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        NewsBo bo = GsonUtils.wrapDataToEntity(request, NewsBo.class);
        PageVo pageVo = newsService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        newsService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/personalNews", method = RequestMethod.POST)
    public void personalNews(HttpServletRequest request, HttpServletResponse response) {
        NewsBo bo = GsonUtils.wrapDataToEntity(request, NewsBo.class);
        PageVo vo = newsRealReceiverService.personalNews(bo);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/personalUnreadNews", method = RequestMethod.POST)
    public void personalUnreadNews(HttpServletRequest request, HttpServletResponse response) {
        NewsBo bo = GsonUtils.wrapDataToEntity(request, NewsBo.class);
        PageVo vo = newsRealReceiverService.personalUnreadNews(bo);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/personalReadNews", method = RequestMethod.POST)
    public void personalReadNews(HttpServletRequest request, HttpServletResponse response) {
        NewsBo bo = GsonUtils.wrapDataToEntity(request, NewsBo.class);
        PageVo vo = newsRealReceiverService.personalReadNews(bo);
        GsonUtils.printData(response, vo);
    }

    /**
     * 查询个人加入收藏的公告
     */
    @ResponseBody
    @RequestMapping(value = "/personalStarNews", method = RequestMethod.POST)
    public void personalStarNews(HttpServletRequest request, HttpServletResponse response) {
        NewsRealReceiverBo bo = GsonUtils.wrapDataToEntity(request, NewsRealReceiverBo.class);
        PageVo vo = newsRealReceiverService.personalStarNews(bo);
        GsonUtils.printData(response, vo);
    }

}
