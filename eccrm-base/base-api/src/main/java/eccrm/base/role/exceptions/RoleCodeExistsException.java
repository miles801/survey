package eccrm.base.role.exceptions;

/**
 * Created on 2014/8/3 0:39
 *
 * @author miles
 */
public class RoleCodeExistsException extends RoleException {
    public RoleCodeExistsException(String message) {
        super("角色编号重复:" + message);
    }
}
