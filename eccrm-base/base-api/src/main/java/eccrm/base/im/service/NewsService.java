package eccrm.base.im.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.im.bo.NewsBo;
import eccrm.base.im.domain.News;
import eccrm.base.im.domain.NewsReceiver;
import eccrm.base.im.vo.NewsVo;

import java.util.List;

/**
 * @author Michael
 */
public interface NewsService {

    /**
     * 保存
     *
     * @param news          新闻公告
     * @param newsReceivers 新闻公告接收对象
     */
    String save(News news, List<NewsReceiver> newsReceivers);

    /**
     * 更新
     * 删掉之前的接收者数据，重新设置
     *
     * @param news          新闻公告
     * @param newsReceivers 新闻公告接收对象
     */
    void update(News news, List<NewsReceiver> newsReceivers);

    /**
     * 分页查询
     */
    PageVo pageQuery(NewsBo bo);

    /**
     * 根据ID查询对象的信息
     */
    NewsVo findById(String id);


    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);

    /**
     * 发布公告
     * 根据配置的公告接收对象，查询出真正的接收者，并分配到个人
     *
     * @param id 公告ID
     */
    void publish(String id);

    /**
     * 顶置公告
     *
     * @param id 公告ID
     */
    void markTop(String id);

    /**
     * 注销指定新闻公告
     *
     * @param id 新闻公告ID
     */
    void cancel(String id);

    /**
     * 查询个人未读新闻公告
     *
     * @param bo 高级查询条件,可选
     */
    List<NewsVo> personalUnreadNews(NewsBo bo);

    /**
     * 查询个人已读新闻公告
     *
     * @param bo 高级查询条件,可选
     */
    List<NewsVo> personalReadNews(NewsBo bo);
}
