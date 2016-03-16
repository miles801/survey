package eccrm.base.menu.dao.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.menu.dao.ResourceDao;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class ResourceDaoImplTest extends AbstractTestWrapper {
    private Logger logger = Logger.getLogger(ResourceDaoImplTest.class);
    @Resource
    private ResourceDao resourceDao;

    @Test
    public void testQueryAllLimitDataResource() throws Exception {
        logger.info("测试获取所有需要授权的数据资源的编号列表...（需要数据库中存在至少数据资源）");
        Assert.assertNotNull(resourceDao);
        List<String> codes = resourceDao.queryAllLimitDataResource();
        Assert.assertNotNull(codes);
        Assert.assertTrue(!codes.isEmpty());
        logger.info("获取需要授权的数据资源编号成功：" + codes);
    }
}