package eccrm.base.role.bo;

import eccrm.base.role.domain.Role;

/**
 * @author miles
 * @datetime 2014-03-26
 */

public class RoleBo extends Role {
    private String groupId;
    private String userId;
    private Boolean valid;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
