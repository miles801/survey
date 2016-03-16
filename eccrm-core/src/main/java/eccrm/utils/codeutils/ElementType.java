package eccrm.utils.codeutils;

/**
 * @author miles
 * @datetime 13-12-24 上午10:33
 */
public enum ElementType {
    /**
     * 文本
     */
    TEXT("text"),
    /**
     * 标签
     */
    LABEL("label"),
    /**
     * 单选框
     */
    RADIO("radio"),
    /**
     * 复选框
     */
    CHECKBOX("checkbox"),
    /**
     * 选择框
     */
    SELECT("select"),
    /**
     * 文本域
     */
    TEXTAREA("textarea"),
    /**
     * 日期类型
     */
    DATE("date"),
    /**
     * 时间类型
     */
    TIME("time"),
    /**
     * 日期时间
     */
    DATETIME("datetime"),
    /**
     * 只显示文本
     */
    TEXTONLY("text-only"),
    /**
     * 追加按钮
     */
    ADDON("add-on");
    private String type;

    ElementType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
