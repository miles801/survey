package eccrm.base.parameter.service.impl;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.pager.PageVo;
import eccrm.base.AbstractTestWrapper;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.parameter.domain.SysParamType;
import eccrm.base.parameter.service.SysParamTypeService;
import eccrm.base.parameter.vo.SysParamTypeVo;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by miles on 2014-06-20.
 * 使用自动生成工具生成
 * 请根据实际的业务情况添加需要测试的方法
 */
public class SysParamTypeServiceImplTest extends AbstractTestWrapper {

    @Resource
    private SysParamTypeService service;


    private Logger logger = Logger.getLogger(SysParamTypeServiceImplTest.class);

    @Before
    public void setUp() throws Exception {
        SystemContainer.newInstance(beanFactory);
        logger.info("test init ...");
        SysParamType type = new SysParamType();
        type.setName("测试");
        type.setCode("test");
        type.setStatus(CommonStatus.ACTIVE.getValue());
        type.setSequenceNo(1);
        service.save(type);

    }

    @Test
    public void testSave() {
        SysParamType type = new SysParamType();
        type.setStatus(CommonStatus.ACTIVE.getValue());
        type.setName("用户类型");
        type.setCode("user_code");
        type.setSequenceNo(1);
        String id = service.save(type);
        Assert.assertNotNull(id);

        SysParamTypeVo vo = service.findById(id);
        Assert.assertNotNull(vo);
        Assert.assertEquals("/" + id + "/", vo.getPath());
    }

    @Test
    public void testFindById() {
        logger.info("test findById() ... ");
    }

    @Test
    public void testQuery() throws Exception {
        logger.info("test query() .. ");
        PageVo vos = service.query(null);
        System.out.println(vos.getTotal());
    }

    @Test
    public void testUpdate() throws Exception {
        logger.info("test update() ... ");
    }

    @Test
    public void testGetName() {
        String name = service.getName("test");
        Assert.assertNotNull(name);
        Assert.assertEquals("测试", name);
    }

}
