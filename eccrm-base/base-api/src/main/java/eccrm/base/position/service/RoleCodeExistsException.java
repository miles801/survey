package eccrm.base.position.service;

/**
 * Created on 2014/8/3 0:39
 *
 * @author miles
 */
public class RoleCodeExistsException extends RoleException {
    public RoleCodeExistsException(String message) {
        super("岗位编号重复:" + message);
    }
}
