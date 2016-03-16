package eccrm.base.user.service.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.user.service.UserGroupService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by miles on 2014-07-03.
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 */
public class UserGroupServiceImplTest extends AbstractTestWrapper {

    @Resource
    private UserGroupService service;

    private Logger logger = Logger.getLogger(UserGroupServiceImplTest.class);

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
