package eccrm.base.message.parser;

import com.google.gson.Gson;
import eccrm.base.message.BasicMessage;
import eccrm.base.message.Message;

/**
 * @author Michael
 */
public class JsonParser implements MessageParser {

    private Gson gson = new Gson();

    @Override
    public Message parse(String content) {
        if (content == null || "".equals(content.trim())) {
            return null;
        }
        return gson.fromJson(content, BasicMessage.class);
    }
}
