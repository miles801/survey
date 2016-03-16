package eccrm.base.user.dao.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.user.dao.GroupDao;
import eccrm.base.user.domain.Group;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 * <p/>
 * Created by miles on 2014-07-03.
 */
public class GroupDaoImplTest extends AbstractTestWrapper {

    @Resource
    private GroupDao dao;

    private Logger logger = Logger.getLogger(GroupDaoImplTest.class);

    @Before
    public void setUp() throws Exception {
        logger.info(" test eccrm.base.user.dao.impl.GroupDaoImpl ......");
    }

    @Test
    @Rollback(false)
    public void testSave() {
        logger.info(" save ......");
        Group group = new Group();
        group.setName("研发组");
        group.setStatus(CommonStatus.ACTIVE.getValue());
        String id = dao.save(group);
        Assert.assertNotNull(id);
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
