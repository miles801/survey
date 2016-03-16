package com.ycrl.core.spring;

import com.michael.licence.Licence;
import com.michael.licence.LicenceVerify;
import com.ycrl.core.SystemContainer;
import com.ycrl.core.pool.ThreadPool;
import eccrm.utils.NetUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import redis.clients.jedis.ShardedJedisPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.SocketException;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义Spring启动器
 * <p>
 * 在Spring启动之后可以通过实现com.ycrl.core.SpringLoadListener接口做一些额外的操作
 * </p>
 * <p>
 * 是Spring卸载时，可以通过实现com.ycrl.core.spring.SpringUnloadListener接口来做一些额外的操作，例如关闭连接（释放资源）、停止定时器等
 * </p>
 *
 * @author Michael
 */
public class SpringContextLoader extends ContextLoader implements ServletContextListener {
    private Logger logger = Logger.getLogger(SpringContextLoader.class);
    private ContextLoader contextLoader;
    private BeanFactory beanFactory;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("开始启动系统....");

        // 验证licence
//        verifyLicence();


        // 启动spring
        logger.info("启动Spring...");
        this.contextLoader = this;
        WebApplicationContext webContext = this.contextLoader.initWebApplicationContext(servletContextEvent.getServletContext());
        SystemContainer.newInstance(webContext);
        beanFactory = webContext;
        logger.info("Spring启动成功....");

        // 执行其他操作
        SpringLoadListenerContainer container = (SpringLoadListenerContainer) beanFactory.getBean("springLoadListenerContainer");
        if (container == null) {
            logger.warn(String.format("没有注册Spring启动后的事件服务[%s]", SpringLoadListenerContainer.class.getName()));
        } else {
            container.execute(webContext);
        }
    }

    private void verifyLicence() {
        logger.info("验证Licence....");
        Licence licence = LicenceVerify.getInstance().isValidLicence();
        if (licence == null) {
            logger.error("Licence文件验证失败!");
            System.exit(1);
        }
        Date endDate = licence.getEndDate();
        long time = endDate.getTime() - new Date().getTime();
        if (time < 0) {
            logger.error("Licence已过期!");
            System.exit(1);
        }
        // 验证IP
        Set<String> ips = licence.getMacAddress();
        if (ips != null && !ips.isEmpty()) {
            boolean flag = false;
            for (String ip : ips) {
                if (ip.equals("ALL")) {
                    flag = true;
                    break;
                } else {
                    // 获取本机IP
                    try {
                        Set<String> localIps = NetUtils.getLocalMacAddress();
                        if (localIps.contains(ip)) {
                            flag = true;
                            break;
                        }
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!flag) {
                logger.error("当前主机未授权!");
                System.exit(1);
            }
        }
        // 启动定时器，当Licence过期时退出系统
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.error("Licence已过期!");
                System.exit(1);
            }
        }, time);
        logger.info("Licence验证成功!将在[" + (time / (1000 * 60 * 60 * 24)) + "]天后过期!");
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("系统即将关闭，开始调用已注册的卸载程序....");
        SpringUnloadListenerContainer container = SystemContainer.getInstance().getBean(SpringUnloadListenerContainer.class);
        container.execute(servletContextEvent.getServletContext());
        logger.info("系统即将关闭，开始调用已注册的卸载程序....OK");

        // 关闭redis
        ShardedJedisPool redisPool = SystemContainer.getInstance().getBean(ShardedJedisPool.class);
        if (redisPool != null) {
            redisPool.destroy();
        }

        // 关闭线程池
        ThreadPool.getInstance().shutdown();

    }
}
