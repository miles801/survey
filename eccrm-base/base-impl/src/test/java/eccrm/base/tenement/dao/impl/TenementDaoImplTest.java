package eccrm.base.tenement.dao.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.tenement.dao.TenementDao;
import eccrm.base.tenement.domain.Tenement;
import junit.framework.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class TenementDaoImplTest extends AbstractTestWrapper {

    @Resource
    private TenementDao dao;

    @Test
    public void testFindByCode() throws Exception {
        Assert.assertNotNull(dao);
        Tenement tenement = dao.findByCode("上海优创");
        Assert.assertNotNull(tenement);
    }
}