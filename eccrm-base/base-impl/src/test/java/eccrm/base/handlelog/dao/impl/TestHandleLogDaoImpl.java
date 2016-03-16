package eccrm.base.handlelog.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.handlelog.dao.HandleLogDao;

/**
* Created by wangsd on 2014-04-16.
* 使用自动生成工具生成
* 请根据实际的业务情况添加需要测试的方法
*/
public class TestHandleLogDaoImpl extends AbstractTestWrapper{

    @Resource
    private HandleLogDao dao;

    private Logger logger = Logger.getLogger(TestHandleLogDaoImpl.class);

    @Before
    public void setUp() throws Exception {
        logger.info(" test eccrm.base.handlelog.dao.impl.HandleLogDaoImpl ......");
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

}
