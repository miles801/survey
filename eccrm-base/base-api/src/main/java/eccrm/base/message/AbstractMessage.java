package eccrm.base.message;

/**
 * 抽象消息实现
 *
 * @author Michael
 */
public abstract class AbstractMessage implements Message {
    private String id;
    private String title;
    private String describe;
    private String content;

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
