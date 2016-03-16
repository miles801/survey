package eccrm.base.menu.bo;

import eccrm.base.menu.domain.Resource;

/**
 * @author Michael
 */
public class ResourceBo extends Resource {
    /**
     * 是否有效
     */
    private Boolean valid;
    /**
     * 是否为根节点
     */
    private Boolean root;
    private String parentId;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
