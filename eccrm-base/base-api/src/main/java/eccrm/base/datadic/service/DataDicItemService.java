package eccrm.base.datadic.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.datadic.bo.DataDicItemBo;
import eccrm.base.datadic.domain.DataDicItem;
import eccrm.base.datadic.vo.DataDicItemVo;

import java.util.List;

/**
 * @author Michael
 */
public interface DataDicItemService {

    /**
     * 保存
     */
    String save(DataDicItem dataDicItem);

    /**
     * 更新
     */
    void update(DataDicItem dataDicItem);

    /**
     * 分页查询
     */
    PageVo pageQuery(DataDicItemBo bo);

    /**
     * 根据ID查询对象的信息
     */
    DataDicItemVo findById(String id);

    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);

    /**
     * 根据字典编号查询字典选项集合
     *
     * @param code 字典编号
     */
    List<DataDicItemVo> queryByDicCode(String code);

}
