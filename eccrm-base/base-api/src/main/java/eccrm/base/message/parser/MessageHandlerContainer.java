package eccrm.base.message.parser;

import eccrm.base.message.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息解析器容器
 *
 * @author Michael
 */
public class MessageHandlerContainer {
    private static MessageHandlerContainer ourInstance = new MessageHandlerContainer();

    public static MessageHandlerContainer getInstance() {
        return ourInstance;
    }

    private Map<String, MessageParser> parserMap = new HashMap<String, MessageParser>();
    private Map<String, MessageHandler> handlerMap = new HashMap<String, MessageHandler>();

    private MessageHandlerContainer() {
    }

    /**
     * 注册消息解析器
     *
     * @param messageType 消息类型/key，不能重复
     * @param parser      解析器
     */
    public void register(String messageType, MessageParser parser, MessageHandler handler) {
        if (messageType == null || "".equals(messageType.trim()) || parser == null || handler == null) {
            return;
        }
        if (parserMap.containsKey(messageType)) {
            throw new RuntimeException("不允许注册重复的消息解析器：" + messageType);
        }
        if (handlerMap.containsKey(messageType)) {
            throw new RuntimeException("不允许注册重复的消息处理器：" + messageType);
        }
        parserMap.put(messageType, parser);
        handlerMap.put(messageType, handler);
    }

    /**
     * 根据key获取已经注册的消息解析器
     *
     * @param messageType 消息类型/key
     * @return 消息解析器实例
     */
    public MessageParser getParser(String messageType) {
        if (messageType == null || "".equals(messageType.trim())) {
            return null;
        }
        return parserMap.get(messageType);
    }

    public MessageHandler getHandler(String messageType) {
        if (messageType == null || "".equals(messageType.trim())) {
            return null;
        }
        return handlerMap.get(messageType);
    }

}
