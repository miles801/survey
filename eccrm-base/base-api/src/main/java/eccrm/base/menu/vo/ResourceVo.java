package eccrm.base.menu.vo;

import com.ycrl.base.common.CommonDomain;

/**
 * @author Michael
 */
public class ResourceVo extends CommonDomain {
    /* 资源名称 */
    private String name;
    /* 资源编号 */
    private String code;
    /* 所属模块 */
    private String module;
    private String moduleName;
    /* 资源类型 */
    private String type;
    private String typeName;
    private boolean isData;
    /* 是否显示，默认显示 */
    private Boolean show;

    /* 是否需要授权 */
    private Boolean authorization;

    /* 排序号，从0开始 */
    private Integer sequenceNo;
    /* 菜单对应的url地址 */
    private String url;

    /* 上级资源 */
    private String parentId;
    private String parentName;

    /* 用于查找节点 */
    private String path;
    private String statusName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Boolean authorization) {
        this.authorization = authorization;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isData() {
        return isData;
    }

    public void setData(boolean isData) {
        this.isData = isData;
    }
}
