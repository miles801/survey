package eccrm.base.datadic.dao;

import eccrm.base.datadic.bo.DataDicItemBo;
import eccrm.base.datadic.domain.DataDicItem;

import java.util.List;

/**
 * @author Michael
 */
public interface DataDicItemDao {

    String save(DataDicItem dataDicItem);

    void update(DataDicItem dataDicItem);

    /**
     * 高级查询接口
     */
    List<DataDicItem> query(DataDicItemBo bo);


    /**
     * 查询总记录数
     */
    Long getTotal(DataDicItemBo bo);

    DataDicItem findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（�?��是get或�?load得到的对象）
     */
    void delete(DataDicItem dataDicItem);

    /**
     * 根据字典编号查询所有的字典项
     *
     * @param code 字典编号
     */
    List<DataDicItem> queryByDicCode(String code);

    /**
     * 删除指定字典下的所有字典项
     *
     * @param dicId 数据字典ID
     */
    void deleteByDicId(String dicId);
}
