package eccrm.base;

import org.hibernate.engine.FilterDefinition;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统数据过滤器，用于查询与当前用户在同一系统下的数据
 *
 * @author Michael
 */
public class ParamFilter extends FilterDefinition {
    public static final String FILTER_NAME = "paramFilter";
    private static Map<String, Type> map;

    static {
        map = new HashMap<String, Type>();
        map.put(SystemPropertyConstant.CREATOR_ID, new StringType());
    }


    /**
     * @param name             过滤器名称
     * @param defaultCondition 查询条件
     * @param parameterTypes   查询参数类型
     */
    public ParamFilter(String name, String defaultCondition, Map parameterTypes) {
        super(name, defaultCondition, parameterTypes);
    }

    public ParamFilter() {
        super(FILTER_NAME, SystemFieldConstant.CREATOR_USER_ID + "=:" + SystemPropertyConstant.CREATOR_ID, map);
    }
}
