package eccrm.base;

import com.ycrl.core.SystemContainer;
import eccrm.base.tenement.service.TenementService;
import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class SystemContainerTest extends AbstractTestWrapper {
    @Test
    public void testGetBean() throws Exception {
        TenementService service = SystemContainer.getInstance().getBean(TenementService.class);
        System.out.println(service);
        Assert.assertNotNull(service);
    }

    @Test
    public void testSystemContainer() throws Exception {
        TenementService service = SystemContainer.getInstance().getBean(TenementService.class);
        TenementService service2 = SystemContainer.getInstance().getBean(TenementService.class);
        TenementService service3 = SystemContainer.getInstance().getBean(TenementService.class);
        assertTrue(service == service2);
        assertTrue(service == service3);
        assertTrue(service2 == service3);
        assertNotNull(service);
    }
}