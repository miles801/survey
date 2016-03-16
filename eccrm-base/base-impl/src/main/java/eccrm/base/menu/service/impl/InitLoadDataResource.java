package eccrm.base.menu.service.impl;

import com.ycrl.core.spring.SpringLoadListener;
import eccrm.base.menu.service.DataResourceContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;

/**
 * <p>在Spring启动完成后，加载所有的需要授权的数据资源编号</p>
 * <p>该实例需要配置在初始化了SystemContainer之后配置</p>
 *
 * @author Michael
 */
public class InitLoadDataResource implements SpringLoadListener {
    private Logger logger = Logger.getLogger(InitLoadDataResource.class);

    @Override
    public void execute(BeanFactory beanFactory) {
        logger.info("加载需要授权的数据资源的编号集合....");
        DataResourceContext.getInstance().reload();
    }
}
