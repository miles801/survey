package eccrm.base.user.dao.impl;

import com.ycrl.core.context.SecurityContext;
import eccrm.base.AbstractTestWrapper;
import eccrm.base.user.dao.LoginLogDao;
import eccrm.base.user.domain.LoginLog;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static junit.framework.Assert.*;

/**
 * Created by miles on 2014-04-14.
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 */
public class TestLoginLogDaoImpl extends AbstractTestWrapper {

    @Resource
    private LoginLogDao dao;

    private Logger logger = Logger.getLogger(TestLoginLogDaoImpl.class);

    @Before
    public void setUp() throws Exception {
        logger.info(" test eccrm.base.user.dao.impl.LoginLogDaoImpl ......");
    }

    @Test
    public void testSave() {
        String id = insert();
        assertNotNull(id);
    }

    @Test
    public void testFindById() {
        logger.info("test findById() ... ");
        String id = insert();
        LoginLog log = dao.findById(id);
        assertNotNull(log);
        assertNull(log.getLogoutType());
    }

    @Test
    public void testLast() throws Exception {
        logger.info("last() ... ");
        insert();
        insert();
        insert();
        try {
            dao.last(null);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
        LoginLog log = dao.last(SecurityContext.getUserId());
        assertNotNull(log);
        assertEquals(SecurityContext.getUserId(), log.getCreatorId());
        assertEquals(SecurityContext.getUsername(), log.getCreatorName());
    }

    @Test
    public void testQuery() throws Exception {
        logger.info("test query() .. ");
    }

    @Test
    public void testUpdate() throws Exception {
        logger.info("test update() ... ");
    }

    @Test
    public void testDeleteById() {
    }

    private String insert() {
        LoginLog log = getDefaultLoginLog();
        String id = dao.save(log);
        return id;
    }

    private LoginLog getDefaultLoginLog() {
        LoginLog loginLog = new LoginLog();
        return loginLog;
    }
}
