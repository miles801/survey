package eccrm.base;

import org.hibernate.engine.FilterDefinition;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人数据权限过滤，用于查询自己创建的数据
 * 数据要求必须包含字段：creator_id
 * 启用过滤器时必须设置参数：creatorId
 * 在Hibernate的映射文件中加入<filter name="personalFilter"/>
 *
 * @author miles
 * @see SystemPropertyConstant
 */
public class PersonalFilter extends FilterDefinition {
    public static final String FILTER_NAME = "personalFilter";
    private static Map<String, Type> map;

    /**
     * @param name             数据权限过滤器的名称
     * @param defaultCondition 默认的查询条件
     * @param parameterTypes   参数
     */
    private PersonalFilter(String name, String defaultCondition, Map parameterTypes) {
        super(name, defaultCondition, parameterTypes);
    }

    static {
        map = new HashMap<String, Type>();
        map.put(SystemPropertyConstant.CREATOR_ID, new StringType());
    }

    public PersonalFilter() {
        super(FILTER_NAME, SystemFieldConstant.CREATOR_USER_ID + "=:" + SystemPropertyConstant.CREATOR_ID, map);
    }
}
