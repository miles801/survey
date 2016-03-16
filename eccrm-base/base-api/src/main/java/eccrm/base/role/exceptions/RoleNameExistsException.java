package eccrm.base.role.exceptions;

/**
 * Created on 2014/8/3 0:39
 *
 * @author miles
 */
public class RoleNameExistsException extends RoleException {

    public RoleNameExistsException(String message) {
        super("角色名称重复:" + message);
    }

}
