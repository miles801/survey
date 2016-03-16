package eccrm.base.im.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.im.bo.NewsCollectBo;
import eccrm.base.im.domain.NewsCollect;
import eccrm.base.im.vo.NewsCollectVo;

/**
 * @author Michael
 * 
 */
public interface NewsCollectService {

    /**
     * 保存
     */
    String save(NewsCollect newsCollect);

    /**
     * 更新
     */
    void update(NewsCollect newsCollect);

    /**
     * 分页查询
     */
    PageVo pageQuery(NewsCollectBo bo);

    /**
     * 根据ID查询对象的信�?
     */
    NewsCollectVo findById(String id);

    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);

}
