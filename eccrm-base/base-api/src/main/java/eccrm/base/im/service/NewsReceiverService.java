package eccrm.base.im.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.im.bo.NewsReceiverBo;
import eccrm.base.im.domain.NewsReceiver;
import eccrm.base.im.vo.NewsReceiverVo;

/**
 * @author Michael
 * 
 */
public interface NewsReceiverService {

    /**
     * 保存
     */
    String save(NewsReceiver newsReceiver);

    /**
     * 更新
     */
    void update(NewsReceiver newsReceiver);

    /**
     * 分页查询
     */
    PageVo pageQuery(NewsReceiverBo bo);

    /**
     * 根据ID查询对象的信息
     */
    NewsReceiverVo findById(String id);

    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);

}
