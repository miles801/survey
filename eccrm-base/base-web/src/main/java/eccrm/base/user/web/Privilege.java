package eccrm.base.user.web;

import java.util.Set;

/**
 * Created on 2014/7/21 5:06
 *
 * @author miles
 */
public class Privilege {
    private Set<String> privileges;

    public Privilege() {
    }

    public Privilege(Set<String> privileges) {
        this.privileges = privileges;
    }

    public boolean hasRightByCode(String code) {
        if (privileges == null) return false;
        return privileges.contains(code);
    }
}
