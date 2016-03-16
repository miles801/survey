package eccrm.base.handlelog.service.impl;

import static junit.framework.Assert.assertNotNull;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.handlelog.domain.HandleLog;
import eccrm.base.handlelog.service.HandleLogService;
import eccrm.base.handlelog.vo.HandleLogVo;

/**
* Created by wangsd on 2014-04-16.
* 使用自动生成工具生成
* 请根据实际的业务情况添加需要测试的方法
*/
public class TestHandleLogServiceImpl extends AbstractTestWrapper{

    @Resource
    private HandleLogService service;

    private Logger logger = Logger.getLogger(TestHandleLogServiceImpl.class);
    
    private String id;
    
    @Before
    public void setUp() throws Exception {
    	
    	HandleLog handleLog=new HandleLog();
    	handleLog.setHandleContent("处理内容...");
    	handleLog.setDescription("处理记录的描述");
    	id = service.save(handleLog);
    	
        logger.info("test init ...");
    }

    @Test
    public void testSave() {
        logger.info("test save() ... ");
    }

    @Test
    public void testFindById() {
    	HandleLogVo handleLogVo = service.findById(id);
    	assertNotNull(handleLogVo);
    	logger.info(handleLogVo.getDescription());
    	logger.info(handleLogVo.getId());
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
