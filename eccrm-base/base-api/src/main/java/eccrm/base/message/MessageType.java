package eccrm.base.message;

/**
 * 消息类型
 *
 * @author Michael
 */
public enum MessageType {
    EMAIL("email"),
    OA("oa"),
    NOTICE("notice"),
    MESSAGE("message"),
    EVENT("event");

    private String type;

    MessageType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
