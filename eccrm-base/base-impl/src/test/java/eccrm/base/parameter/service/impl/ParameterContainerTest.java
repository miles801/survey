package eccrm.base.parameter.service.impl;

import com.ycrl.core.SystemContainer;
import eccrm.base.AbstractTestWrapper;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.parameter.domain.SysParamItem;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.parameter.service.SysParamItemService;
import eccrm.base.parameter.vo.SysParamItemVo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 测试参数容器
 * Created by Michael on 2014/9/4.
 */
public class ParameterContainerTest extends AbstractTestWrapper {
    @Before
    public void setUp() throws Exception {
        SystemContainer.newInstance(beanFactory);
    }

    @Test
    public void testGetItems() throws Exception {
        ParameterContainer container = ParameterContainer.getInstance();
        List<SysParamItemVo> vos = container.getSystemItems("user_type");
        List<SysParamItemVo> vos2 = container.getSystemItems("user_type");
        SysParamItem item = new SysParamItem();
        item.setName("测试用户");
        item.setType("user_type");
        item.setSequenceNo(3);
        item.setValue("3");
        item.setStatus(CommonStatus.ACTIVE.getValue());
        SystemContainer.getInstance().getBean(SysParamItemService.class)
                .save(item);
        container.reloadSystem("user_type");
        List<SysParamItemVo> vos3 = container.getSystemItems("user_type");
        System.out.println(vos3.size());

    }
}
