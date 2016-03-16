package eccrm.base.parameter.dao.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.parameter.dao.SysParamItemDao;
import eccrm.base.parameter.domain.SysParamItem;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by miles on 2014-06-20.
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 */
public class SysParamItemDaoImplTest extends AbstractTestWrapper {

    @Resource
    private SysParamItemDao dao;

    private Logger logger = Logger.getLogger(SysParamItemDaoImplTest.class);

    @Before
    public void setUp() throws Exception {
        logger.info(" test eccrm.base.parameter.dao.impl.SysParamItemDaoImpl ......");
    }

    @Test
    public void testSave() {
        logger.info(" save ......");
    }

    @Test
    public void testFindById() {
        logger.info("test findById() ... ");
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

    @Test
    public void testFetchCascade() throws Exception {
        logger.info("测试查询级联数据...");
        List<SysParamItem> types = dao.fetchCascade("SP_USER_STATE", "ACTIVE");
        Assert.assertNotNull(types);
        Assert.assertEquals(2, types.size());
    }
}
