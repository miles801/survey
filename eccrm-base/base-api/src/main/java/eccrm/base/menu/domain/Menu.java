package eccrm.base.menu.domain;

import com.ycrl.base.common.CommonDomain;
import com.ycrl.utils.tree.PathTree;
import com.ycrl.utils.tree.StaticTree;
import eccrm.base.attachment.AttachmentSymbol;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能菜单
 */
public class Menu extends CommonDomain implements PathTree<Menu, String>, StaticTree<Menu, String>, AttachmentSymbol,Serializable {

    public static final String PARAM_TYPE = "SP_MENU_TYPE";
    /**
     * 菜单类型：子系统
     */
    public static final String TYPE_SYSTEM = "SYS_CDLX";
    /**
     * 菜单类型：九宫格
     */
    public static final String TYPE_NAV = "SYS_DHBQ";

    /**
     * 菜单类型：导航菜单
     */
    public static final String TYPE_MENU = "BP_CDGN";
    /* 菜单名称 */
    private String name;
    /* 菜单编号 */
    private String code;
    /**
     * 菜单类型：菜单、九宫格
     */
    private String type;

    /* 是否显示，默认显示 */
    private Boolean show;

    /* 是否需要授权 */
    private Boolean authorization;

    /* 排序号，从0开始 */
    private Integer sequenceNo;

    /**
     * 所属资源
     */
    private Resource resource;

    /* 上级菜单 */
    private Menu parent;

    private String parentId;

    /* 用于查找节点 */
    private String path;

    //    private String url;
    private String icon;
    /**
     * 提示信息
     */
    private String popInfo;

    // 是否全屏
    private Boolean fullScreen;

    @Transient
    private List<Menu> children;

    public Boolean getFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(Boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Boolean authorization) {
        this.authorization = authorization;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPopInfo() {
        return popInfo;
    }

    public void setPopInfo(String popInfo) {
        this.popInfo = popInfo;
    }

    @Override
    public String businessId() {
        return getId();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Menu addChild(Menu child) {
        if (child == null) return null;
        if (children == null) {
            children = new ArrayList<Menu>();
        }
        children.add(child);
        return this;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
