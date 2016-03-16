package eccrm.base.org;


import eccrm.base.org.bo.OrganizationBo;
import eccrm.base.org.dao.OrganizationDao;
import eccrm.base.org.domain.Organization;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by qy on 14-10-15.
 */
public class OrgHallper {

    @Resource
    private OrganizationDao organizationDao;
    public List<Organization> queryEffective(OrganizationBo bo) {
       return organizationDao.queryEffective(bo);
    }
    public OrgHallper() {
    }
}
