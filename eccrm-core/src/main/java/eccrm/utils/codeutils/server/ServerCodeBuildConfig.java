package eccrm.utils.codeutils.server;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author miles
 * @datetime 14-3-12 下午1:11
 * 服务端代码生成器的配置文件
 */
public class ServerCodeBuildConfig {
    /**
     * 工程路径
     */
    private String workspace;
    /**
     * 模块名称（会拼接成module-api,module-impl,module-web)
     */
    private String module;
    /**
     * 实体类的名称
     */
    private String entity;
    private String lowerEntity;
    /**
     * 包路径
     */
    private String packagePath;
    /**
     * 作者
     */
    private String author;

    private boolean staticTree;
    private boolean dynamicTree;
    private String current = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private String tableName;
    /**
     * 是否开启租户过滤器:默认情况所有的映射都需要（关联表可以不需要）
     */
    private boolean tenementFilter = true;

    /**
     * 权限过滤器
     */
    private boolean userFilter = true;
    private boolean userGroupFilter = true;
    private boolean departmentFilter = true;
    private boolean noFilter = true;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntity() {
        return entity;
    }


    public void setEntity(String entity) {
        this.entity = entity;
        this.lowerEntity = entity.substring(0, 1).toLowerCase() + entity.substring(1);
    }

    public String getLowerEntity() {
        return lowerEntity;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCurrent() {
        return current;
    }

    public boolean isNoFilter() {
        return noFilter;
    }

    public void setNoFilter(boolean noFilter) {
        this.noFilter = noFilter;
    }

    public boolean isDepartmentFilter() {
        return departmentFilter;
    }

    public void setDepartmentFilter(boolean departmentFilter) {
        this.departmentFilter = departmentFilter;
    }

    public boolean isUserGroupFilter() {
        return userGroupFilter;
    }

    public void setUserGroupFilter(boolean userGroupFilter) {
        this.userGroupFilter = userGroupFilter;
    }

    public boolean isUserFilter() {
        return userFilter;
    }

    public void setUserFilter(boolean userFilter) {
        this.userFilter = userFilter;
    }

    public boolean isTenementFilter() {
        return tenementFilter;
    }

    public void setTenementFilter(boolean tenementFilter) {
        this.tenementFilter = tenementFilter;
    }

    public boolean isStaticTree() {
        return staticTree;
    }

    public void setStaticTree(boolean staticTree) {
        this.staticTree = staticTree;
    }

    public boolean isDynamicTree() {
        return dynamicTree;
    }

    public void setDynamicTree(boolean dynamicTree) {
        this.dynamicTree = dynamicTree;
    }
}
