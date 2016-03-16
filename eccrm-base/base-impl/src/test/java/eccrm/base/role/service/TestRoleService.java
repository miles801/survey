package eccrm.base.role.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.role.bo.RoleBo;
import eccrm.base.role.domain.Role;
import eccrm.base.role.vo.RoleVo;
import eccrm.utils.date.DateUtils;
import com.ycrl.utils.gson.GsonUtils;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by miles on 2014-03-26.
 * 使用自动生成工具生成
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-*.xml")
public class TestRoleService {

    @Resource
    private RoleService roleService;

    private Logger logger = Logger.getLogger(TestRoleService.class);

    @Before
    public void setUp() throws Exception {

    }

    String id = null;

    @Test
    public void testInit() {
        logger.info("test init ...");
        Assert.assertNotNull(roleService);
    }

    @Test
    public void testSave() {
        logger.info("test save() ... ");
        Role role = new Role();
        role.setName("项目经理");
        role.setCode("PM");
        role.setStartDate(DateUtils.getDate(2014, 0, 1));
        role.setEndDate(DateUtils.getDate(2015, 0, 1));
        id = roleService.save(role);
        Assert.assertNotNull(id);
        logger.info("test save() ... ");
    }

    @Test
    public void testFindById() {
        logger.info("test findById() ... ");
        RoleVo vo = roleService.findById("40288aeb44fd0b680144fd0b6dad0000");
        Assert.assertNotNull(vo);
    }

    @Test
    public void testQuery() throws Exception {
        logger.info("test query() .. ");
        RoleBo bo = new RoleBo();
        PageVo vo = roleService.query(bo);
        Assert.assertNotNull(vo);
        Assert.assertTrue(vo.getTotal() > 0);
        logger.info(GsonUtils.toJson(vo));
    }

    @Test
    public void testUpdate() throws Exception {
        RoleVo vo = roleService.findById("40288aeb44fd0b680144fd0b6dad0000");
        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        role.setStartDate(new Date(DateUtils.getDate(2014, 0, 1).getTime()));
        role.setEndDate(new Date(DateUtils.getDate(2015, 0, 1).getTime()));
        roleService.update(role);
        logger.info("test update() ... ");
    }

    @Test
    public void testDeleteById() {
        String id = "40288aeb44fd36930144fd3698da0000,40288aeb44fd3c630144fd3c67c10000";
        roleService.deleteByIds(id.split(","));
    }

}
