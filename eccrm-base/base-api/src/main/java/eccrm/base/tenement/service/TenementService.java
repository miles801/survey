package eccrm.base.tenement.service;

import eccrm.base.tenement.bo.TenementBo;
import eccrm.base.tenement.domain.Tenement;
import eccrm.base.tenement.vo.TenementVo;
import com.ycrl.core.pager.PageVo;

/**
 * @author miles
 * @datetime 2014-03-14
 */
public interface TenementService {


    /**
     * 保存租户
     * 如果租户状态为空，则设置为默认值“采集中”
     *
     * @param tenement
     * @return
     */
    String save(Tenement tenement);

    void update(Tenement tenement);

    /**
     * 暂停租户
     *
     * @param id
     */
    void pause(String id);

    /**
     * 关闭租户
     *
     * @param id
     */
    void close(String id);

    PageVo query(TenementBo bo);

    TenementVo findById(String id);

    /**
     * 根据id列表删除/更改租户的状态
     * 无状态、采集中状态均直接删除；
     * 正常状态的将会将状态设置为已注销
     *
     * @param ids
     */
    void deleteByIds(String... ids);

}
