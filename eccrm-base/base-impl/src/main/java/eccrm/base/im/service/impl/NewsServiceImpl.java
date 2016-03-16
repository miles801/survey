package eccrm.base.im.service.impl;

import com.michael.cache.redis.RedisCommand;
import com.michael.cache.redis.RedisServer;
import com.ycrl.core.SystemContainer;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.employee.dao.EmployeeDao;
import eccrm.base.employee.domain.Employee;
import eccrm.base.im.bo.NewsBo;
import eccrm.base.im.bo.NewsReceiverBo;
import eccrm.base.im.dao.NewsDao;
import eccrm.base.im.dao.NewsRealReceiverDao;
import eccrm.base.im.dao.NewsReceiverDao;
import eccrm.base.im.domain.News;
import eccrm.base.im.domain.NewsRealReceiver;
import eccrm.base.im.domain.NewsReceiver;
import eccrm.base.im.service.NewsService;
import eccrm.base.im.vo.NewsVo;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.position.dao.PositionEmpDao;
import org.hibernate.cache.CacheProvider;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService, BeanWrapCallback<News, NewsVo> {
    @Resource
    private NewsDao newsDao;

    @Resource
    private NewsReceiverDao newsReceiverDao;

    @Resource
    private NewsRealReceiverDao newsRealReceiverDao;


    @Override
    public String save(News news, List<NewsReceiver> newsReceivers) {
        news.setPublishState(News.PUBLISH_STATE_UNPUBLISHED);
        String id = newsDao.save(news);

        // 保存接收对象
        saveReceiver(news, newsReceivers);
        return id;
    }

    @Override
    public void update(News news, List<NewsReceiver> newsReceivers) {
        newsDao.update(news);
        // 重新设置接收者对象
        newsReceiverDao.deleteByNewsId(news.getId());

        saveReceiver(news, newsReceivers);


    }

    private void saveReceiver(News news, List<NewsReceiver> newsReceivers) {
        // 保存接收对象
        if (newsReceivers != null && !newsReceivers.isEmpty()) {
            for (NewsReceiver newsReceiver : newsReceivers) {
                newsReceiver.setNewsId(news.getId());
                newsReceiver.setNewsTitle(news.getTitle());
                if (NewsReceiver.TYPE_POSITION.equals(news.getReceiverType())) {    // 机构岗位类型
                    // 获得机构岗位ID
                    String orgId = newsReceiver.getReceiverId2();
                    PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);
                    String orgPositionId = positionEmpDao.queryId(newsReceiver.getReceiverId(), orgId);
                    // 用description字段来存储结合的数据
                    newsReceiver.setDescription(orgPositionId);

                }
                newsReceiverDao.save(newsReceiver);
            }
        }
    }

    @Override
    public PageVo pageQuery(NewsBo bo) {
        PageVo vo = new PageVo();
        Long total = newsDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        // 设置排序
        List<News> newsList = newsDao.query(bo);
        List<NewsVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(newsList, NewsVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public NewsVo findById(String id) {
        News news = newsDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(news, NewsVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            News news = newsDao.findById(id);
            // 只有当状态为未发布时才可以删除
            if (news != null && News.PUBLISH_STATE_UNPUBLISHED.equals(news.getPublishState())) {
                newsDao.delete(news);
            }
        }
    }

    @Override
    public void markTop(String id) {
        News news = newsDao.findById(id);
        if (news != null) {
            news.setIsTop(true);
        }
    }

    @Override
    public void publish(String id) {
        News news = newsDao.findById(id);
        if (news == null) {
            throw new EntityNotFoundException("公告发布失败!ID为[" + id + "]的公告不存在!");
        }

        // 时间验证
        Date startTime = news.getStartTime();
        long now = new Date().getTime();
        if (startTime != null && startTime.getTime() > now) {
            throw new RuntimeException("公告发布失败!还未到发布时间!");
        }
        Date endTime = news.getEndTime();
        if (endTime != null && endTime.getTime() < now) {
            throw new RuntimeException("公告发布失败!发布时间已过期!");
        }
        // 设置状态、发布时间、发布人
        news.setPublishState(News.PUBLISH_STATE_PUBLISHED);
        news.setPublishTime(new Date());
        news.setPublisherId(SecurityContext.getUserId());
        news.setPublisherName(SecurityContext.getEmpName());

        String receiverType = news.getReceiverType();
        List<NewsRealReceiver> newsRealReceivers = new ArrayList<NewsRealReceiver>();
        if (NewsReceiver.TYPE_ALL.equals(receiverType)) {   // 全部
            EmployeeDao employeeDao = SystemContainer.getInstance().getBean(EmployeeDao.class);
            List<Employee> employees = employeeDao.query(null);
            for (Employee employee : employees) {
                NewsRealReceiver nrr = new NewsRealReceiver();
                nrr.setReceiverId(employee.getId());
                nrr.setReceiverName(employee.getEmployeeName());
                newsRealReceivers.add(nrr);
            }
        } else if (NewsReceiver.TYPE_ORG.equals(receiverType)) { // 指定机构
            List<String> orgIds = newsReceiverDao.findByNewsId(news.getId());
            if (orgIds != null) {
                PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);
                List<String> empIds = positionEmpDao.findOrgEmpIds(orgIds);
                for (String empId : empIds) {
                    NewsRealReceiver nrr = new NewsRealReceiver();
                    nrr.setReceiverId(empId);
                    newsRealReceivers.add(nrr);
                }
            }
        } else if (NewsReceiver.TYPE_POSITION.equals(receiverType)) {   // 机构岗位
            // 获得机构岗位ID
            NewsReceiverBo bo = new NewsReceiverBo();
            bo.setNewsId(news.getId());
            List<NewsReceiver> orgPositionIds = newsReceiverDao.query(bo);
            PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);
            for (NewsReceiver nr : orgPositionIds) {
                // 获得员工ID
                List<String> empIds = positionEmpDao.findEmpIds(nr.getReceiverId2(), nr.getReceiverId());
                if (empIds != null && !empIds.isEmpty()) {
                    for (String empId : empIds) {
                        NewsRealReceiver nrr = new NewsRealReceiver();
                        nrr.setReceiverId(empId);
                        newsRealReceivers.add(nrr);
                    }
                }
            }

        } else if (NewsReceiver.TYPE_PARAM.equals(receiverType)) {   // 业态
            List<String> params = newsReceiverDao.findByNewsId(news.getId());
            if (params != null) {
                PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);
                List<String> empIds = positionEmpDao.findParamEmpIds(params);
                for (String empId : empIds) {
                    NewsRealReceiver nrr = new NewsRealReceiver();
                    nrr.setReceiverId(empId);
                    newsRealReceivers.add(nrr);
                }
            }
        }

        // 根据接收对象，分配给真正的接收者
        CacheProvider cacheProvider = SystemContainer.getInstance().getBean(CacheProvider.class);
        for (final NewsRealReceiver nrr : newsRealReceivers) {
            nrr.setNewsId(news.getId());
            nrr.setNewsTitle(news.getTitle());
            nrr.setCategory(news.getCategory());
            nrr.setPublishTime(news.getPublishTime());
            nrr.setHasRead(false);
            nrr.setHasReply(false);
            nrr.setHasStar(false);
            newsRealReceiverDao.save(nrr);

            // 添加到消息池中
            RedisServer redisServer = SystemContainer.getInstance().getBean(RedisServer.class);
            redisServer.execute(new RedisCommand<Object>() {
                @Override
                public Object invoke(ShardedJedis shardedJedis, ShardedJedisPool pool) {
                    shardedJedis.rpush("MSG:" + nrr.getReceiverId(), GsonUtils.toJson(nrr));
                    return null;
                }
            });
        }

    }

    @Override
    public void cancel(String id) {
        News news = newsDao.findById(id);
        if (news != null) {
            // 设置状态
            news.setPublishState(News.PUBLISH_STATE_CANCELED);
        }
    }

    @Override
    public List<NewsVo> personalReadNews(NewsBo bo) {
        List<News> news = newsDao.personalReadNews(bo);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(news, NewsVo.class);
    }

    @Override
    public List<NewsVo> personalUnreadNews(NewsBo bo) {
        List<News> news = newsDao.personalUnreadNews(bo);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(news, NewsVo.class);
    }


    @Override
    public void doCallback(News news, NewsVo o) {
        ParameterContainer container = ParameterContainer.getInstance();
        String categoryName = container.getBusinessName(News.CATEGORY, news.getCategory());
        o.setCategoryName(categoryName);
        String stateName = container.getSystemName(News.PUBLISH_STATE, news.getPublishState());
        o.setPublishStateName(stateName);
    }
}
