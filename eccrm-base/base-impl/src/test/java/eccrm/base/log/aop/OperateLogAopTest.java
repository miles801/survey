package eccrm.base.log.aop;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OperateLogAopTest{

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        SecurityContext.set("1", "kongfanpeng", "1");
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath*:applicationContext-*.xml");
        SystemContainer.newInstance(beanFactory);
        userService = beanFactory.getBean(UserService.class);
    }

    @Test
    public void testSaveLog() throws Exception {
        userService.deleteByIds("402881e54ce84335014ce843432a0000");
    }


}