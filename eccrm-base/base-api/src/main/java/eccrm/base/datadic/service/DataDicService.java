package eccrm.base.datadic.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.datadic.bo.DataDicBo;
import eccrm.base.datadic.dao.DataDicCondition;
import eccrm.base.datadic.domain.DataDic;
import eccrm.base.datadic.vo.DataDicVo;

import java.util.List;

/**
 * @author Michael
 */
public interface DataDicService {

    /**
     * 保存
     */
    String save(DataDic dataDic);

    /**
     * 更新
     */
    void update(DataDic dataDic);

    /**
     * 分页查询
     */
    PageVo pageQuery(DataDicBo bo);

    /**
     * 根据ID查询对象的信息
     */
    DataDicVo findById(String id);

    /**
     * 根据ID查询，并携带字典项
     */
    DataDicVo detail(String id);


    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);


    /**
     * 根据数据字典编号查询数据字典信息，且包含字典项明细
     *
     * @param code 数据字典编号
     */
    DataDicVo findByCode(String code);

    List queryData(DataDicCondition condition);
}
