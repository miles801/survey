package eccrm.base;

import org.hibernate.engine.FilterDefinition;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询时对租户的过滤器，过滤器名称默认使用“tenementFilter”，需要的参数为tenement_id，值为租户的id
 *
 * @author miles
 * @datetime 2014/3/24 12:55
 */
public class TenementFilter extends FilterDefinition {
    public static final String FILTER_NAME = "tenementFilter";
    private static Map<String, Type> map;

    /**
     * Construct a new FilterDefinition instance.
     *
     * @param name             The name of the filter for which this configuration is in effect.
     * @param defaultCondition
     * @param parameterTypes
     */
    private TenementFilter(String name, String defaultCondition, Map parameterTypes) {
        super(name, defaultCondition, parameterTypes);
    }

    static {
        map = new HashMap<String, Type>();
        map.put(SystemPropertyConstant.TENEMENT_ID, new StringType());
    }

    public TenementFilter() {
        super(FILTER_NAME, SystemFieldConstant.TENEMENT_ID + "=:" + SystemPropertyConstant.TENEMENT_ID, map);
    }

}
