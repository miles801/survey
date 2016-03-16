package eccrm.utils.codeutils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author miles
 * @datetime 13-12-24 下午4:51
 */
public class TabEditPage extends EditPage {
    private List<Tab> tabs;

    public TabEditPage() {
        super.setTab(true);
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public TabEditPage addTab(String id, String name, String url) {
        if (tabs == null) {
            tabs = new ArrayList<Tab>();
        }
        tabs.add(new Tab(id, name, url));
        return this;
    }

    /**
     * 根据页签的描述，添加一组tab
     *
     * @param tabDescriptions
     * @return
     */
    public TabEditPage addTab(String... tabDescriptions) {
        if (tabDescriptions == null || tabDescriptions.length < 1) {
            return this;
        }
        for (String des : tabDescriptions) {
            String tab[] = des.split(":");
            if (tab.length == 1) {
                addTab("temp_" + new Date().getTime(), tab[0], null);
            } else if (tab.length == 2) {
                addTab(tab[0], tab[1], null);
            } else if (tab.length == 3) {
                if (tab[2].equals("#")) {
                    addTab(tab[0], tab[1], null);
                } else {
                    addTab(tab[0], tab[1], tab[2]);
                }
            } else {
                throw new RuntimeException("错误的页签描述信息，必须符合[id:name:url]的格式!");
            }
        }
        return this;
    }
}
