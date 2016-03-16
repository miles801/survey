package eccrm.base.parameter.service.impl;

import eccrm.base.message.Message;
import eccrm.base.message.MessageHandler;
import eccrm.base.parameter.service.ParameterContainer;

/**
 * 系统参数消息处理器
 * 消息的内容为要重新加载的系统参数的类型
 *
 * @author Michael
 */
public class SysParamMessageHandler implements MessageHandler {
    public static final String TYPE = "SYS_PARAM";
    @Override
    public void invoke(Message message) {
        String type = message.getContent();
        if (type == null || "".equals(type)) {
            return;
        }
        ParameterContainer.getInstance().reloadSystem(type);
    }

}
