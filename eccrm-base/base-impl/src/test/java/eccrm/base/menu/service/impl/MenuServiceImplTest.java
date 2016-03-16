package eccrm.base.menu.service.impl;

import com.ycrl.core.SystemContainer;
import eccrm.base.menu.domain.Menu;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author miles
 * @datetime 2014/4/14 15:02
 */
public class MenuServiceImplTest {
    private static Logger logger = Logger.getLogger(MenuServiceImplTest.class);

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath*:applicationContext-*.xml");
        SystemContainer.newInstance(beanFactory);
        SessionFactory sessionFactory = beanFactory.getBean(SessionFactory.class);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Menu> menus = session.createCriteria(Menu.class).list();
        for (Menu menu : menus) {
//            String url = menu.getUrl();
//            if (StringUtils.isNotEmpty(url)) {
//                Resource resource = menu.getResource();
//                resource.setUrl(url);
//                session.update(resource);
//            }
        }
        session.getTransaction().commit();
    }

}
