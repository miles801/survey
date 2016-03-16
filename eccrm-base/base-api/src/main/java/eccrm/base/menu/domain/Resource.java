package eccrm.base.menu.domain;


import com.ycrl.base.common.CommonDomain;
import eccrm.utils.tree.PathTree;

import java.io.Serializable;

/**
 * 资源
 */
public class Resource extends CommonDomain implements PathTree<Resource, String>,Serializable {
    public static final String PARAM_TYPE = "SP_RESOURCE_TYPE";
    /**
     * 系统参数：模块
     */
    public static final String SYS_MODULE = "SP_MODULE";

    /**
     * URL
     */
    public static final String TYPE_URL = "1";
    /**
     * ELEMENT
     */
    public static final String TYPE_ELEMENT = "2";
    /**
     * DATA
     */
    public static final String TYPE_DATA = "3";


    /* 资源名称 */
    private String name;

    /* 资源编号 */
    private String code;

    /**
     * 所属模块，系统参数：SP_MODULE
     */
    private String module;
    /**
     * 将上面的常量
     */
    private String type;

    /* 是否显示，默认显示 */
    private Boolean show;

    /* 是否需要授权 */
    private Boolean authorization;

    /* 排序号，从0开始 */
    private Integer sequenceNo;

    /* 资源对应的url地址 */
    private String url;

    /* 上级资源 */
    private Resource parent;

    /* 用于查找节点 */
    private String path;

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

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
