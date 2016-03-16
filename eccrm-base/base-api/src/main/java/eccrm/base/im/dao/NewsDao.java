package eccrm.base.im.dao;

import eccrm.base.im.bo.NewsBo;
import eccrm.base.im.domain.News;

import java.util.List;

/**
 * @author Michael
 */
public interface NewsDao {

    String save(News news);

    void update(News news);

    /**
     * 高级查询接口
     */
    List<News> query(NewsBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(NewsBo bo);

    News findById(String id);


    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的
     */
    void delete(News news);

    /**
     * 查询个人未读新闻公告
     *
     * @param bo 高级查询条件,可选
     */
    List<News> personalUnreadNews(NewsBo bo);

    /**
     * 查询个人已读新闻公告
     *
     * @param bo 高级查询条件,可选
     */
    List<News> personalReadNews(NewsBo bo);

    /**
     * 查询个人新闻公告
     *
     * @param bo 高级查询条件,可选
     */
    List<News> personalNews(NewsBo bo);
}
