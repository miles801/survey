package eccrm.base.im.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import eccrm.base.im.bo.NewsBo;
import eccrm.base.im.bo.NewsRealReceiverBo;
import eccrm.base.im.dao.NewsDao;
import eccrm.base.im.dao.NewsRealReceiverDao;
import eccrm.base.im.domain.News;
import eccrm.base.im.domain.NewsRealReceiver;
import eccrm.base.im.service.NewsRealReceiverService;
import eccrm.base.im.vo.NewsRealReceiverVo;
import eccrm.base.im.vo.NewsVo;
import eccrm.base.parameter.service.ParameterContainer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("newsRealReceiverService")
public class NewsRealReceiverServiceImpl implements NewsRealReceiverService, BeanWrapCallback<News, NewsVo> {
    @Resource
    private NewsRealReceiverDao newsRealReceiverDao;

    @Resource
    private NewsDao newsDao;

    @Override
    public String save(NewsRealReceiver newsRealReceiver) {
        String id = newsRealReceiverDao.save(newsRealReceiver);
        return id;
    }

    @Override
    public void update(NewsRealReceiver newsRealReceiver) {
        newsRealReceiverDao.update(newsRealReceiver);
    }

    @Override
    public PageVo pageQuery(NewsRealReceiverBo bo) {
        PageVo vo = new PageVo();
        Long total = newsRealReceiverDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<NewsRealReceiver> newsRealReceiverList = newsRealReceiverDao.query(bo);
        List<NewsRealReceiverVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(newsRealReceiverList, NewsRealReceiverVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public NewsRealReceiverVo findById(String id) {
        NewsRealReceiver newsRealReceiver = newsRealReceiverDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(newsRealReceiver, NewsRealReceiverVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            newsRealReceiverDao.deleteById(id);
        }
    }

    @Override
    public void read(String newsId) {
        // 查询这条公告
        NewsRealReceiverBo bo = new NewsRealReceiverBo();
        bo.setNewsId(newsId);
        bo.setReceiverId(SecurityContext.getUserId());
        bo.setHasRead(false);
        List<NewsRealReceiver> news = newsRealReceiverDao.query(bo);

        // 设置状态
        if (news != null && !news.isEmpty()) {
            NewsRealReceiver nrr = news.get(0);
            nrr.setHasRead(true);
            nrr.setReadTime(new Date());
        }
    }


    @Override
    public void star(String newsId) {
        // 查询这条公告
        NewsRealReceiverBo bo = new NewsRealReceiverBo();
        bo.setNewsId(newsId);
        bo.setReceiverId(SecurityContext.getUserId());
        bo.setHasStar(false);
        List<NewsRealReceiver> news = newsRealReceiverDao.query(bo);
        // 更改状态
        if (news != null && !news.isEmpty()) {
            NewsRealReceiver nrr = news.get(0);
            nrr.setHasStar(true);
            nrr.setStarTime(new Date());
        }
    }

    @Override
    public PageVo personalStarNews(NewsRealReceiverBo bo) {
        PageVo vo = new PageVo();
        if (bo == null) {
            bo = new NewsRealReceiverBo();
        }
        bo.setReceiverId(SecurityContext.getUserId());  // 设置个人
        bo.setHasStar(true);        // 设置收藏
        Long total = newsRealReceiverDao.getTotal(bo);
        if (total == null || total == 0) {
            return vo;
        }
        vo.setTotal(total);
        List<NewsRealReceiver> realReceivers = newsRealReceiverDao.query(bo);
        vo.setData(BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<NewsRealReceiver, NewsRealReceiverVo>() {
                    @Override
                    public void doCallback(NewsRealReceiver o, NewsRealReceiverVo vo) {
                        ParameterContainer container = ParameterContainer.getInstance();
                        String categoryName = container.getBusinessName(News.CATEGORY, o.getCategory());
                        vo.setCategoryName(categoryName);
                    }
                })
                .wrapList(realReceivers, NewsRealReceiverVo.class));
        return vo;
    }

    @Override
    public PageVo personalReadNews(NewsBo bo) {
        PageVo vo = new PageVo();
        NewsRealReceiverBo nrrBo = new NewsRealReceiverBo();
        nrrBo.setReceiverId(SecurityContext.getUserId());  // 设置个人
        nrrBo.setHasRead(true);        // 设置已读
        Long total = newsRealReceiverDao.getTotal(nrrBo);
        if (total == null || total == 0) {
            return vo;
        }
        vo.setTotal(total);
        List<News> newsList = newsDao.personalReadNews(bo);
        vo.setData(BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(newsList, NewsVo.class));
        return vo;
    }

    @Override
    public PageVo personalNews(NewsBo bo) {
        PageVo vo = new PageVo();
        NewsRealReceiverBo nrrBo = new NewsRealReceiverBo();
        nrrBo.setReceiverId(SecurityContext.getUserId());  // 设置个人
        Long total = newsRealReceiverDao.getTotal(nrrBo);
        if (total == null || total == 0) {
            return vo;
        }
        vo.setTotal(total);
        List<News> newsList = newsDao.personalNews(bo);
        vo.setData(BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(newsList, NewsVo.class));
        return vo;
    }

    @Override
    public PageVo personalUnreadNews(NewsBo bo) {
        PageVo vo = new PageVo();
        NewsRealReceiverBo nrrBo = new NewsRealReceiverBo();
        nrrBo.setReceiverId(SecurityContext.getUserId());  // 设置个人
        nrrBo.setHasRead(false);        // 设置未读
        Long total = newsRealReceiverDao.getTotal(nrrBo);
        if (total == null || total == 0) {
            return vo;
        }
        vo.setTotal(total);
        List<News> newsList = newsDao.personalUnreadNews(bo);
        vo.setData(BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(newsList, NewsVo.class));
        return vo;
    }


    @Override
    public void removeStar(String newsId) {
        // 查询这条公告
        NewsRealReceiverBo bo = new NewsRealReceiverBo();
        bo.setNewsId(newsId);
        bo.setReceiverId(SecurityContext.getUserId());
        bo.setHasStar(true);
        List<NewsRealReceiver> news = newsRealReceiverDao.query(bo);
        // 更改状态
        if (news != null && !news.isEmpty()) {
            NewsRealReceiver nrr = news.get(0);
            nrr.setHasStar(false);
            nrr.setStarTime(null);
        }
    }

    @Override
    public void doCallback(News news, NewsVo newsVo) {
        ParameterContainer container = ParameterContainer.getInstance();
        String categoryName = container.getBusinessName(News.CATEGORY, news.getCategory());
        newsVo.setCategoryName(categoryName);
        String stateName = container.getSystemName(News.PUBLISH_STATE, news.getPublishState());
        newsVo.setPublishStateName(stateName);
    }
}
