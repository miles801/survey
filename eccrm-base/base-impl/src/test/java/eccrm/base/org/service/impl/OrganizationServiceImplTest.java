package eccrm.base.org.service.impl;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.auth.domain.AccreditData;
import eccrm.base.auth.service.AccreditDataService;
import eccrm.base.auth.service.PersonalPermissionContext;
import eccrm.base.org.service.OrganizationService;
import eccrm.base.org.vo.OrganizationVo;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationServiceImplTest {
    private Logger logger = Logger.getLogger(OrganizationServiceImplTest.class);
    private OrganizationService organizationService;

    @Before
    public void setUp() throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath*:applicationContext-*.xml");
        SystemContainer.newInstance(beanFactory);
        String userId = "8a47d2ca4c2bacd1014c2bcaab7a001f";
        SecurityContext.set(userId, "tangjuan", "1");
        organizationService = beanFactory.getBean(OrganizationService.class);

        // 初始化个人权限
        // 查询个人被授权的所有数据权限的编号集合
        AccreditDataService ads = beanFactory.getBean(AccreditDataService.class);
        if (ads != null) {
            List<AccreditData> accreditData = ads.queryPersonalAllDataResource(userId);
            // key为数据资源的编号，value为数据资源对应的授权信息
            Map<String, List<AccreditData>> accreditDataMap = new HashMap<String, List<AccreditData>>();
            if (accreditData != null && !accreditData.isEmpty()) {
                for (AccreditData foo : accreditData) {
                    String code = foo.getResourceCode();
                    List<AccreditData> tmp = accreditDataMap.get(code);
                    if (tmp == null) {
                        tmp = new ArrayList<AccreditData>();
                        accreditDataMap.put(code, tmp);
                    }
                    tmp.add(foo);
                }
            }
            PersonalPermissionContext.setDataResource(accreditDataMap);

        }
    }

    @Test
    public void testPermissionPersonalParams() throws Exception {
        logger.info("测试获取个人系统集合...");
        Assert.assertNotNull(organizationService);
        List<OrganizationVo> vos = organizationService.permissionPersonalParams();
        Assert.assertNotNull(vos);
        Assert.assertEquals(7, vos.size());
    }
}