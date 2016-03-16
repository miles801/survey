package eccrm.base.region.bo;

import eccrm.base.region.domain.Region;

/**
 * @author miles
 * @datetime 2014-03-25
 */

public class RegionBo extends Region {
    /**
     * 只查询根节点的数据
     */
    private Boolean root;
    /**
     * 上级id
     */
/*    private String parentId;*/

    /**
     * 有效的
     */
    private Boolean valid;

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

 /*   public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }*/

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
