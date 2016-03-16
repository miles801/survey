package eccrm.base.im;

/**
 * @author Michael
 */
public interface Message {
    /**
     * 消息ID
     */
    String getId();

    /**
     * 标题
     */
    String getTitle();

    /**
     * 内容
     */
    String getContent();

    /**
     * 接收人
     */
    String getReceiver();

}
