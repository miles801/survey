package eccrm.base.auth.domain;

/**
 * 常量类，用于保存数据授权的类型
 *
 * @author Michael
 */
public interface AccreditDataType {
    /**
     * 数据授权类型：全部
     */
    public static final String ALL = "0";
    /**
     * 数据授权类型：个人权限
     * 只能查看是自己创建的或者分配给自己的
     */
    public static final String PERSONAL = "1";
    /**
     * 数据授权类型：岗位权限
     * 可以查询跟自己在同一岗位下的所有人创建的或被分配的权限
     */
    public static final String POSITION = "2";

    /**
     * 数据授权类型：机构及所有下级机构
     */
    public static final String ORG_AND_CHILDREN = "3";
    /**
     * 数据授权类型：机构权限
     * 可以查询跟自己在同一直接机构下的所有人创建的或被分配的权限
     */
    public static final String ORG = "4";

    /**
     * 数据权限：系统权限
     * 可以查看跟自己在同一系统的所有人创建或被分配的权限
     */
    public static final String PARAM = "6";
}
