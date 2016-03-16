package eccrm.base.message.parser;

import eccrm.base.message.Message;

/**
 * @author Michael
 */
public interface MessageParser {
    /**
     * 将一个字符串转换为消息对象
     *
     * @param content 消息内容
     * @return 具体的消息实现
     */
    Message parse(String content);
}
