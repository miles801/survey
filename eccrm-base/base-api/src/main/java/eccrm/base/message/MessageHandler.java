package eccrm.base.message;

/**
 * 消息处理器
 *
 * @author Michael
 */
public interface MessageHandler {
    void invoke(Message message);
}
