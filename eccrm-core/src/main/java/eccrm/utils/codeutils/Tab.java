package eccrm.utils.codeutils;

/**
 * @author miles
 * @datetime 13-12-24 上午10:21
 * 页签
 */
public class Tab {
    /**
     * id字符串
     */
    private String name;
    /**
     * 页签的名称
     */
    private String label;
    /**
     * 页签的url
     */
    private String url;

    public Tab() {
    }

    public Tab(String name, String label, String url) {
        this.name = name;
        this.label = label;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
