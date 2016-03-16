package eccrm.base.menu;

import com.ycrl.utils.uuid.UUIDGenerator;
import eccrm.base.auth.domain.AccreditMenu;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.menu.domain.Menu;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 给指定的岗位授予（全部）资源权限
 *
 * @author Michael
 */
public class GrantResourcePermissionTest {
    private Logger logger = Logger.getLogger(GrantResourcePermissionTest.class);

    private Session session;

    @Before
    public void setUp() throws Exception {
        logger.info("初始化Hibernate环境....");
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGrantAllResourceToDept() throws Exception {
        // 岗位ID
        String deptId = "8a8140ef4c112cf6014c112d6efd0000";

        // 查询出所有有效的菜单的ID（在权限表中，资源的id对应的是菜单的id）
        List<String> resourceIds = session.createCriteria(Menu.class)
                .setProjection(Projections.id())
                .add(Restrictions.eq("status", CommonStatus.ACTIVE.getValue()))
                .add(Restrictions.eq("show", true))
                .list();
        Assert.assertNotNull("没有查询到菜单资源数据!", resourceIds);
        Assert.assertTrue("没有查询到菜单资源数据!", !resourceIds.isEmpty());

        logger.info("开始将[菜单资源]授予岗位：" + deptId);
        int i = 0;
        for (String id : resourceIds) {
            AccreditMenu accreditMenu = new AccreditMenu();
            accreditMenu.setResourceId(id);
            accreditMenu.setDeptId(deptId);
            accreditMenu.setId(UUIDGenerator.generate());
            session.save(accreditMenu);
            if (i++ % 10 == 0) {
                session.flush();
                session.clear();
            }
        }

    }

    @After
    public void tearDown() throws Exception {
        session.getTransaction().commit();
        session.close();
    }
}
