package eccrm.base.parameter.dao.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.parameter.dao.SysParamTypeDao;
import eccrm.base.parameter.domain.SysParamType;
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
public class SysParamTypeDaoImplTest extends AbstractTestWrapper {

    @Resource
    private SysParamTypeDao dao;

    private Logger logger = Logger.getLogger(SysParamTypeDaoImplTest.class);

    @Before
    public void setUp() throws Exception {
        logger.info(" test eccrm.base.parameter.dao.impl.SysParamTypeDaoImpl ......");
        SysParamType type = new SysParamType();
        type.setName("测试");
        type.setCode("test");
        type.setStatus(CommonStatus.ACTIVE.getValue());
        type.setSequenceNo(1);
        dao.save(type);
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
    public void testQueryChildren() throws Exception {
        List<SysParamType> types = dao.queryChildren("1");
        Assert.assertNotNull(types);
        Assert.assertEquals(0, types.size());
    }

    @Test
    public void testGetName() throws Exception {
        String name = dao.getName("test");
        Assert.assertNotNull(name);
        Assert.assertEquals("测试", name);
    }
}
