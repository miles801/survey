package eccrm.utils.codeutils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author miles
 * @datetime 13-12-23 下午3:19
 */
public class Module {
    /**
     * 模块名称
     */
    private String name;
    /**
     * 模块中文名称
     */
    private String cnName;
    private String entity;
    private String className;
    /**
     * 弹出层
     */
    private boolean modal = false;
    /**
     * 参数
     */
    private boolean param = true;
    /**
     * 作者
     */
    private String author;
    /**
     * 创建时间
     */
    private String current;

    /**
     * 额外的路径，用于模块中的子模块嵌套路径的问题
     */
    private String extraPath;
    private String path;
    private ListPage listPage;
    private EditPage editPage;
    private boolean permitOverride = false;

    public Module(String moduleName, String entity, String chinese_name) {
        this.name = moduleName;
        this.cnName = chinese_name;
        this.entity = entity;
        this.className = entity.substring(0, 1).toUpperCase() + entity.substring(1);
        this.current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ModuleThreadLocal.setModule(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getClassName() {
        return className;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCurrent() {
        return current;
    }

    public ListPage getListPage() {
        return listPage;
    }

    public void setListPage(ListPage listPage) {
        this.listPage = listPage;
    }

    public EditPage getEditPage() {
        return editPage;
    }

    public void setEditPage(EditPage editPage) {
        this.editPage = editPage;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isPermitOverride() {
        return permitOverride;
    }

    public void setPermitOverride(boolean permitOverride) {
        this.permitOverride = permitOverride;
    }

    public String getExtraPath() {
        return extraPath;
    }

    public void setExtraPath(String extraPath) {
        this.extraPath = extraPath;
    }


    public boolean isModal() {
        return modal;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    public boolean isParam() {
        return param;
    }

    public void setParam(boolean param) {
        this.param = param;
    }

    public String getPath() {
        if (extraPath == null || "".equals(extraPath.trim())) {
            return name + "/" + entity;
        }
        return (name + "/" + extraPath + "/" + entity).replaceAll("//", "/");
    }
}
