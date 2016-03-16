package eccrm.core.enums;

/**
 * @author miles
 * @datetime 2014/4/2 11:45
 */
public class EnumException extends RuntimeException {
    public EnumException() {
    }

    public EnumException(String message) {
        super(message);
    }

    public EnumException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumException(Throwable cause) {
        super(cause);
    }
}
