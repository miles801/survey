package eccrm.utils.codeutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 13-12-24 下午12:27
 */
public class Page {
    //block header内容
    private String headerText;
    //block button的按钮
    private List<Button> headerButtons;

    private int type;

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public List<Button> getHeaderButtons() {
        return headerButtons;
    }

    public void setHeaderButtons(List<Button> headerButtons) {
        this.headerButtons = headerButtons;
    }

    /**
     * 格式为name:icon:url:click:extraClass
     *
     * @param buttonNames
     */
    public void addHeaderButton(String... buttonNames) {
        if (buttonNames != null && buttonNames.length > 0) {
            for (String buttonName : buttonNames) {
                if ("".equals(buttonName.trim())) continue;
                addHeaderButton(new Button(buttonName));
            }
        }
    }

    public void addHeaderButton(Button button) {
        if (headerButtons == null) {
            headerButtons = new ArrayList<Button>();
        }
        headerButtons.add(button);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
