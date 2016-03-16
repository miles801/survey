package eccrm.base.org.dao.impl;

import eccrm.base.org.domain.Organization;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 重置机构的path
 *
 * @author Michael
 */
public class ResetOrgPathTest {
    private Logger logger = Logger.getLogger(ResetOrgPathTest.class);
    private Session session;

    @Before
    public void setUp() throws Exception {
        logger.info("初始化Hibernate环境....");
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

    }

    @Test
    public void testResetOrgPath() throws Exception {
        logger.info("重置" + Organization.class.getName() + "的PATH属性!");
        logger.info("加载所有的数据!");

        List<Organization> organizations = session.createQuery("from " + Organization.class.getName()).list();
        int i = 0;
        for (Organization o : organizations) {
            i++;
            String path = getPath("/" + o.getId() + "/", o.getParent());
            o.setPath(path.replaceAll("//", "/"));
            logger.info(o.getName() + "-->" + o.getPath());
        }
        logger.info("批量修改结束，共修改了[" + i + "]条数据!");

    }

    public String getPath(String path, Organization parent) {
        if (parent == null) {
            return path;
        }
        return getPath("/" + parent.getId() + "/" + path, parent.getParent());
    }

    @After
    public void tearDown() throws Exception {
        session.getTransaction().commit();
        session.close();
    }
}
