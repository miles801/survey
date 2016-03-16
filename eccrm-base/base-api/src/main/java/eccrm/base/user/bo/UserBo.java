package eccrm.base.user.bo;

import eccrm.base.user.domain.User;

/**
 * @author miles
 * @datetime 2014-03-14
 */

public class UserBo extends User {
    /**
     * 如果该值为true，表示查询有效期内的用户
     */
    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
