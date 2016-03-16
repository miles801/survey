package eccrm.base.position.service;

/**
 * Created on 2014/8/3 0:38
 *
 * @author miles
 */
public class RoleException extends RuntimeException {
    public RoleException() {
        super();
    }

    public RoleException(String message) {
        super(message);
    }

    public RoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleException(Throwable cause) {
        super(cause);
    }
}
