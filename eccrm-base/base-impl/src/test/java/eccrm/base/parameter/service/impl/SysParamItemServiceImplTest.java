package eccrm.base.parameter.service.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.parameter.service.SysParamItemService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by miles on 2014-06-20.
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 */
public class SysParamItemServiceImplTest extends AbstractTestWrapper {

    @Resource
    private SysParamItemService service;

    private Logger logger = Logger.getLogger(SysParamItemServiceImplTest.class);

    @Before
    public void setUp() throws Exception {
        logger.info("test init ...");
    }

    @Test
    public void testSave() {
        logger.info("test save() ... ");
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
