package eccrm.base.user.dao.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.user.dao.UserGroupDao;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 * <p/>
 * Created by miles on 2014-07-03.
 */
public class UserGroupDaoImplTest extends AbstractTestWrapper {

    @Resource
    private UserGroupDao dao;

    private Logger logger = Logger.getLogger(UserGroupDaoImplTest.class);

    @Before
    public void setUp() throws Exception {
        logger.info(" test eccrm.base.user.dao.impl.UserGroupDaoImpl ......");
    }

    @Test
    public void testQueryGroupIds() throws Exception {
        Assert.assertNotNull(dao);
        List<String> groupIds = dao.queryGroupIds("1");
        Assert.assertNotNull(groupIds);
        Assert.assertTrue(groupIds.size() > 0);
    }
}
