package eccrm.base.org.dao.impl;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.org.dao.OrganizationDao;
import eccrm.base.org.domain.Organization;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class OrganizationDaoImplTest {
    private OrganizationDao organizationDao;

    @Before
    public void setUp() throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath*:applicationContext-*.xml");
        SystemContainer.newInstance(beanFactory);
        String userId = "8a47d2ca4c0c16c0014c0c6f283602fb";
        SecurityContext.set(userId, "kongfanpeng", "1");
        organizationDao = beanFactory.getBean(OrganizationDao.class);
    }

    @Test
    public void testPermissionRootQuery() throws Exception {
        Assert.assertNotNull(organizationDao);
        List<Organization> orgs = organizationDao.permissionRootQuery();
        Assert.assertNotNull(orgs);
        Assert.assertEquals(1, orgs.size());
    }
}