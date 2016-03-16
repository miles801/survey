package eccrm.base.auth.dao;

import eccrm.base.auth.bo.AccreditBo;
import eccrm.base.auth.domain.Accredit;

import java.util.List;

/**
 * @author Michael
 */
public interface AccreditDao {

    String save(Accredit accredit);

    void update(Accredit accredit);

    List<Accredit> query(AccreditBo bo);

    long getTotal(AccreditBo bo);

    Accredit findById(String id);

    void deleteById(String id);
}
