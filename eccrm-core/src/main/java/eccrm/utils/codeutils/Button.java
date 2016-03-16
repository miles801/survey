package eccrm.utils.codeutils;

/**
 * @author miles
 * @datetime 13-12-24 上午10:31
 */
public class Button {
    /**
     * 按钮名称
     */
    private String name;
    /**
     * 按钮对应的url
     */
    private String url;
    /**
     * 单击按钮时的事件
     */
    private String click;
    /**
     * 按钮图标
     */
    private String icon;
    /**
     * 额外的样式类
     */
    private String extraClass;

    /**
     * 格式为name:icon:url:click:extraClass
     *
     * @param configs
     */
    public Button(String... configs) {
        if (configs != null && configs.length > 0) {
            for (String btnConfig : configs) {
                //name:type:length:addOnIcon
                String foo[] = btnConfig.split(":");
                for (int i = 0; i < foo.length; i++) {
                    String temp = foo[i];
                    if ("".equals(temp.trim())) continue;
                    switch (i) {
                        case 0:
                            this.name = temp;
                            break;
                        case 1:
                            this.icon = temp;
                            break;
                        case 2:
                            this.url = temp;
                            break;
                        case 3:
                            this.click = temp;
                            break;
                        case 4:
                            this.extraClass = temp;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getExtraClass() {
        return extraClass;
    }

    public void setExtraClass(String extraClass) {
        this.extraClass = extraClass;
    }
}
