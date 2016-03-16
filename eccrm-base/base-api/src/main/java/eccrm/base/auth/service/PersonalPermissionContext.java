package eccrm.base.auth.service;

import eccrm.base.auth.domain.AccreditData;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael
 */
public class PersonalPermissionContext {
    public static final String DATA_RESOURCE = "PERSONAL_DATA_RESOURCE";
    public static final String ORGS = "ORGS";
    public static final String ORG_AND_CHILDREN = "ORG_AND_CHILDREN";// 机构及所有的下级机构id
    public static final String POSITIONS = "POSITIONS";
    public static final String PARAMS = "PARAMS";
    /**
     * key为:数据资源编号
     * value：编号为key值被授予的权限的集合
     */
    private static ThreadLocal<Map<String, List<AccreditData>>> data = new ThreadLocal<Map<String, List<AccreditData>>>();
    private static ThreadLocal<Set<String>> orgs = new ThreadLocal<Set<String>>();
    private static ThreadLocal<Set<String>> positions = new ThreadLocal<Set<String>>();
    private static ThreadLocal<Set<String>> params = new ThreadLocal<Set<String>>();

    public static void setDataResource(Map<String, List<AccreditData>> accreditData) {
        data.set(accreditData);
    }

    public static void remove() {
        data.remove();
        orgs.remove();
        positions.remove();
        params.remove();
    }

    public static Map<String, List<AccreditData>> getDataResource() {
        return data.get();
    }

    public static void setOrgs(Set<String> org) {
        orgs.set(org);
    }

    public static void removeOrgs() {
        orgs.remove();
    }

    public static Set<String> getOrgs() {
        return orgs.get();
    }

    public static void setParams(Set<String> param) {
        params.set(param);
    }

    public static void removeParams() {
        params.remove();
    }

    public static Set<String> getParams() {
        return params.get();
    }

    public static void setPositions(Set<String> position) {
        positions.set(position);
    }

    public static void removePositions() {
        positions.remove();
    }

    public static Set<String> getPositions() {
        return positions.get();
    }

}
