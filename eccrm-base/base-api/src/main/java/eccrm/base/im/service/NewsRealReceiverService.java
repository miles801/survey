package eccrm.base.im.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.im.bo.NewsBo;
import eccrm.base.im.bo.NewsRealReceiverBo;
import eccrm.base.im.domain.NewsRealReceiver;
import eccrm.base.im.vo.NewsRealReceiverVo;

/**
 * @author Michael
 */
public interface NewsRealReceiverService {

    /**
     * 保存
     */
    String save(NewsRealReceiver newsRealReceiver);

    /**
     * 更新
     */
    void update(NewsRealReceiver newsRealReceiver);

    /**
     * 分页查询
     */
    PageVo pageQuery(NewsRealReceiverBo bo);

    /**
     * 根据ID查询对象的信�?
     */
    NewsRealReceiverVo findById(String id);

    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);

    /**
     * 将指定的公告设置为已读
     * 注意：这里会联合本地登录用户去查找未读公告
     *
     * @param newsId 新闻公告的ID
     */
    void read(String newsId);

    /**
     * 将指定的公告加入收藏
     * 注意：这里会联合本地登录用户去查找对应的公告
     *
     * @param newId 新闻公告ID
     */
    void star(String newId);


    /**
     * 查询个人收藏的公告
     *
     * @param bo 高级查询
     */
    PageVo personalStarNews(NewsRealReceiverBo bo);

    /**
     * 查询个人已读公告
     *
     * @param bo 高级查询
     */
    PageVo personalReadNews(NewsBo bo);

    /**
     * 查询个人未读公告
     *
     * @param bo 高级查询
     */
    PageVo personalUnreadNews(NewsBo bo);

    /**
     * 查询个人所有公告
     *
     * @param bo 高级查询
     */
    PageVo personalNews(NewsBo bo);

    /**
     * 取消收藏
     *
     * @param newsId 新闻公告ID
     */
    void removeStar(String newsId);
}
