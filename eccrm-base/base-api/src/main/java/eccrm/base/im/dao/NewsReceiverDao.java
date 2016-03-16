package eccrm.base.im.dao;

import eccrm.base.im.bo.NewsReceiverBo;
import eccrm.base.im.domain.NewsReceiver;

import java.util.List;

/**
 * @author Michael
 */
public interface NewsReceiverDao {

    String save(NewsReceiver newsReceiver);

    void update(NewsReceiver newsReceiver);

    /**
     * 高级查询接口
     */
    List<NewsReceiver> query(NewsReceiverBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(NewsReceiverBo bo);

    NewsReceiver findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（�?��是get或�?load得到的对象）
     */
    void delete(NewsReceiver newsReceiver);

    /**
     * 删除某一公告所配置的接收对象
     *
     * @param newsId 公告ID
     */
    void deleteByNewsId(String newsId);

    /**
     * 查询指定公告的所有接收对象的ID
     *
     * @param newsId 公告ID
     */
    List<String> findByNewsId(String newsId);
}
