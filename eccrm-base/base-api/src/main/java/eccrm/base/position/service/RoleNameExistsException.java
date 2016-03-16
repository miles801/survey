package eccrm.base.position.service;

/**
 * Created on 2014/8/3 0:39
 *
 * @author miles
 */
public class RoleNameExistsException extends RoleException {

    public RoleNameExistsException(String message) {
        super("岗位名称重复:" + message);
    }

}
