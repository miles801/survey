package eccrm.base.menu.service;

import com.ycrl.core.SystemContainer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据授权上下文
 *
 * @author Michael
 */
public class DataResourceContext {
    private static DataResourceContext context = new DataResourceContext();

    private Set<String> dataResourceCodes = new HashSet<String>();

    private DataResourceContext() {
        reload();
    }

    public static DataResourceContext getInstance() {
        return context;
    }

    /**
     * <p>更新当前缓存中已经存在的需要进行授权的数据资源的编号</p>
     */
    public void reload() {
        ResourceService resourceService = SystemContainer.getInstance().getBean(ResourceService.class);
        if (resourceService != null) {
            List<String> codes = resourceService.queryAllLimitDataResourceCode();
            if (codes != null && !codes.isEmpty()) {
                dataResourceCodes.clear();
                dataResourceCodes.addAll(codes);
            }
        }
    }

    /**
     * 获取缓存的所有的需要进行授权的数据资源的编号
     */
    public Set<String> getLimitDataResourceCodeList() {
        return dataResourceCodes;
    }


}
