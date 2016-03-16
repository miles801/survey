package eccrm.base.im;

import com.ycrl.utils.string.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息池
 *
 * @author Michael
 */
public class MessagePool {
    private static MessagePool ourInstance = new MessagePool();

    public static MessagePool getInstance() {
        return ourInstance;
    }

    private final Map<String, List<Message>> messageMap = new HashMap<String, List<Message>>();

    private MessagePool() {
    }

    /**
     * 添加消息
     */
    public MessagePool push(Message message) {
        String id = message.getId();
        synchronized (messageMap) {
            List<Message> messageList = messageMap.get(id);
            if (messageList == null) {
                messageList = new ArrayList<Message>();
                messageMap.put(id, messageList);
            }
            messageList.add(message);
            return this;
        }
    }

    /**
     * 取出某一个接收者的消息
     *
     * @param user 接收者的ID
     */
    public List<Message> pop(String user) {
        if (StringUtils.isNotEmpty(user)) {
            synchronized (messageMap) {
                List<Message> messageList = messageMap.get(user);
                messageMap.remove(user);
                return messageList;
            }
        }
        return null;
    }

}
