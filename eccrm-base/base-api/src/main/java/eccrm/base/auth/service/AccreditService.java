package eccrm.base.auth.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.auth.bo.AccreditBo;
import eccrm.base.auth.domain.Accredit;
import eccrm.base.auth.vo.AccreditVo;

/**
 * @author Michael
 */
public interface AccreditService {

    String save(Accredit accredit);

    void update(Accredit accredit);

    PageVo query(AccreditBo bo);

    AccreditVo findById(String id);

    void deleteByIds(String[] ids);

}
