package eccrm.base.user.service.impl;

import eccrm.base.AbstractTestWrapper;
import eccrm.base.user.domain.User;
import eccrm.base.user.enums.UserStatus;
import eccrm.base.user.enums.UserType;
import eccrm.base.user.service.UserService;
import eccrm.base.user.vo.UserVo;
import com.ycrl.core.pager.PageVo;
import eccrm.utils.md5.MD5Utils;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.annotation.Resource;

import static junit.framework.Assert.*;

/**
 * @author miles
 * @datetime 2014/3/20 11:58
 */
public class UserServiceImplTest extends AbstractTestWrapper {
    @Resource
    private UserService userService;
    private Logger logger = Logger.getLogger(UserServiceImplTest.class);

    @Test
    public void testSave() throws Exception {
        logger.info("测试没有设置任何数据....");
        User user = new User();
        try {
            userService.save(user);
            Assert.fail();
        } catch (Exception e) {
            assertTrue(true);
        }

        logger.info("测试没有设置用户类型，应该为临时用户...");
        user.setUsername("张三");
        user.setCode("zhangs");
        String id = userService.save(user);
        assertNotNull(id);
        UserVo vo = userService.findById(id);
        assertNotNull(vo);
        assertEquals("张三", vo.getUsername());
        assertEquals("zhangs", vo.getCode());
        assertEquals(UserStatus.ACTIVE.getValue(), vo.getStatus());
        assertEquals(UserType.TEMP.getValue(), vo.getType());
    }


    @Test
    public void testQuery() throws Exception {
        PageVo vo = userService.query(null);
        assertNotNull(vo);
        assertTrue(vo.getTotal() > 0);
    }

    @Test
    public void testGet() throws Exception {
        UserVo vo = userService.findById("1");
        assertNotNull(vo);
    }

    @Test
    public void testDelete() throws Exception {
        //删除临时用户
        deleteTemp();

        //删除正式：采集中用户
        deleteOfficialCollecting();

        //删除正式：启用中/已注销用户
        deleteOfficialNormalAndCanceled();
    }

    private void deleteTemp() {
        User tempUser = getUser();
        String id = userService.save(tempUser);
        assertNotNull(id);
        UserVo foo = userService.findById(id);
        assertNotNull(foo);
        assertEquals(UserType.TEMP.getValue(), foo.getType());
        assertEquals(UserStatus.INACTIVE.getValue(), foo.getStatus());
        userService.deleteByIds(id);
        UserVo bar = userService.findById(id);
        assertNull(bar);
    }

    private void deleteOfficialCollecting() {
        User user = getUser();
        user.setType(UserType.OFFICIAL.getValue());
        String id = userService.save(user);
        assertNotNull(id);
        UserVo vo = userService.findById(id);
        assertNotNull(vo);
        userService.deleteByIds(id);
        vo = userService.findById(id);
        assertNull(vo);
    }

    private void deleteOfficialNormalAndCanceled() {
        User user = getUser();
        user.setType(UserType.OFFICIAL.getValue());
        user.setStatus(UserStatus.ACTIVE.getValue());
        String id = userService.save(user);
        assertNotNull(id);
        UserVo vo = userService.findById(id);
        assertNotNull(vo);
        userService.deleteByIds(id);
        vo = userService.findById(id);
        assertNotNull(vo);
        assertEquals(UserStatus.CANCELED.getValue(), vo.getStatus());
        userService.deleteByIds(id);
        vo = userService.findById(id);
        assertNotNull(vo);
        assertEquals(UserStatus.CANCELED.getValue(), vo.getStatus());

    }

    private User getUser() {
        User user = new User();
        user.setUsername("for test");
        return user;
    }

    @Test
    public void testGenPwd() throws Exception {
        String pwd = "yx80096951";
        System.out.println(MD5Utils.encode(pwd));
    }
}
