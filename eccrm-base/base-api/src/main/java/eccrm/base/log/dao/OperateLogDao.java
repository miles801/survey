package eccrm.base.log.dao;

import eccrm.base.log.bo.OperateLogBo;
import eccrm.base.log.domain.OperateLog;

import java.util.List;

/**
 * @author Michael
 */
public interface OperateLogDao {

    String save(OperateLog operateLog);

    void update(OperateLog operateLog);

    /**
     * 高级查询接口
     */
    List<OperateLog> query(OperateLogBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(OperateLogBo bo);

    OperateLog findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(OperateLog operateLog);
}
