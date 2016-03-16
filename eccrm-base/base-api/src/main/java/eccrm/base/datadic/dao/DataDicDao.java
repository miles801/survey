package eccrm.base.datadic.dao;

import eccrm.base.datadic.bo.DataDicBo;
import eccrm.base.datadic.domain.DataDic;

import java.util.List;

/**
 * @author Michael
 */
public interface DataDicDao {

    String save(DataDic dataDic);

    void update(DataDic dataDic);

    /**
     * 高级查询接口
     */
    List<DataDic> query(DataDicBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(DataDicBo bo);

    /**
     * 根据ID查找
     *
     * @param id 数据字典ID
     */
    DataDic findById(String id);

    /**
     * 根据ID查找，会使用缓存
     * @param id id，必须
     */
    DataDic load(String id);

    /**
     * 根据数据字典编号查找
     *
     * @param code 数据字典编号
     */
    DataDic findByCode(String code);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的
     */
    void delete(DataDic dataDic);

    /**
     * 根据组合条件查询结果集
     *
     * @param clazz     要返回的实体
     * @param condition 集合
     * @param <T>       实体类型
     */
    public <T> List<T> queryData(Class<T> clazz, DataDicCondition condition);
}
