package eccrm.base.tenement.dao.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.tenement.dao.DocumentDao;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 * <p/>
 * Created by miles on 2014-07-01.
 */
public class DocumentDaoImplTest extends AbstractTestWrapper {

    @Resource
    private DocumentDao dao;

    private Logger logger = Logger.getLogger(DocumentDaoImplTest.class);

    @Before
    public void setUp() throws Exception {
        logger.info(" test eccrm.base.tenement.dao.impl.DocumentDaoImpl ......");
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
