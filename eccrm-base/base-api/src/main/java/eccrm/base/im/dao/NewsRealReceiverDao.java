package eccrm.base.im.dao;

import eccrm.base.im.bo.NewsRealReceiverBo;
import eccrm.base.im.domain.NewsRealReceiver;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * @author Michael
 */
public interface NewsRealReceiverDao {

    String save(NewsRealReceiver newsRealReceiver);

    void update(NewsRealReceiver newsRealReceiver);

    /**
     * 高级查询接口
     */
    List<NewsRealReceiver> query(NewsRealReceiverBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(NewsRealReceiverBo bo);

    NewsRealReceiver findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（�?��是get或�?load得到的对象）
     */
    void delete(NewsRealReceiver newsRealReceiver);

    /**
     * 获得个人未读公告的离线查询对象
     *
     * @param receiverId 员工ID
     * @return 只包含公告ID的离线查询对象
     */
    DetachedCriteria findPersonalUnreadNews(String receiverId);

    /**
     * 获得个人已读公告的离线查询对象
     *
     * @param receiverId 员工ID
     * @return 只包含公告ID的离线查询对象
     */
    DetachedCriteria findPersonalReadNews(String receiverId);

    /**
     * 获得个人所有的公告的离线查询对象
     *
     * @param receiverId 员工ID
     * @return 只包含公告ID的离线查询对象
     */
    DetachedCriteria findPersonalNews(String receiverId);


}
