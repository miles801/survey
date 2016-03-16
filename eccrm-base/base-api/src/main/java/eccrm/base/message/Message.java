package eccrm.base.message;

/**
 * 抽象消息接口
 *
 * @author Michael
 */
public interface Message {

    String getId();

    String getTitle();

    String getDescribe();

    String getContent();
}
