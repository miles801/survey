package eccrm.base.tenement.dao;

import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.tenement.bo.TenementBo;
import eccrm.base.tenement.domain.Tenement;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-03-14
 */
public interface TenementDao {

    @LogInfo(type = OperateType.ADD, describe = "租户-新增")
    String save(Tenement tenement);

    @LogInfo(type = OperateType.MODIFY, describe = "租户-修改")
    void update(Tenement tenement);

    List<Tenement> query(TenementBo bo);

    Long getTotal(TenementBo bo);

    Tenement findById(String id);

    @LogInfo(type = OperateType.DELETE, describe = "租户-删除")
    void deleteById(String id);

    Tenement findByCode(String code);

}
