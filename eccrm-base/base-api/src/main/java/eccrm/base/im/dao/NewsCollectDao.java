package eccrm.base.im.dao;

import eccrm.base.im.bo.NewsCollectBo;
import eccrm.base.im.domain.NewsCollect;
import eccrm.base.im.vo.NewsCollectVo;
import java.util.List;

/**
 * @author Michael
 */
public interface NewsCollectDao {

    String save(NewsCollect newsCollect);

    void update(NewsCollect newsCollect);

    /**
     * 高级查询接口
     */
    List<NewsCollect> query(NewsCollectBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(NewsCollectBo bo);

    NewsCollect findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（�?��是get或�?load得到的对象）
     */
    void delete(NewsCollect newsCollect);
}
