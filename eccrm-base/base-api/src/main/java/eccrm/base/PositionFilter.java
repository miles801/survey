package eccrm.base;

import org.hibernate.engine.FilterDefinition;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael
 */
public class PositionFilter extends FilterDefinition {
    public static final String FILTER_NAME = "orgFilter";
    private static Map<String, Type> map;

    static {
        map = new HashMap<String, Type>();
        map.put(SystemPropertyConstant.CREATOR_ID, new StringType());
    }


    public PositionFilter() {
        super(FILTER_NAME, SystemFieldConstant.CREATOR_USER_ID + "=:" + SystemPropertyConstant.CREATOR_ID, map);
    }

    /**
     * Construct a new FilterDefinition instance.
     *
     * @param name             The name of the filter for which this configuration is in effect.
     * @param defaultCondition
     * @param parameterTypes
     */
    public PositionFilter(String name, String defaultCondition, Map parameterTypes) {
        super(name, defaultCondition, parameterTypes);
    }
}
