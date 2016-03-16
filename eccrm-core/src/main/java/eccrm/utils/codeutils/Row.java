package eccrm.utils.codeutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 13-12-23 下午4:00
 */
public class Row {

    /**
     * 要符合name:type:length:addOnIcon的格式
     * 其中只有name为必填
     *
     * @param elementsArr
     */
    public Row(String... elementsArr) {
        if (elementsArr != null && elementsArr.length > 0) {
            for (String element : elementsArr) {
                //name:type:length:addOnIcon
                String foo[] = element.split(":");
                Element e = new Element();
                for (int i = 0; i < foo.length; i++) {
                    String temp = foo[i];
                    if ("".equals(temp.trim())) continue;
                    switch (i) {
                        case 0:
                            e.setName(temp);
                            break;
                        case 1:
                            if ("checkbox".equals(temp) || "radio".equals(temp)) {
                                String items = foo[0];
                                items = items.replace("|",",");
                                if (items.indexOf("(") > 0 && items.indexOf(")") > 0) {
                                    String t = items.substring(items.indexOf("(")+1, items.lastIndexOf(")"));
                                    e.addItem(t);
                                }
                            }
                            e.setType(temp);
                            break;
                        case 2:
                            e.setLength(temp);
                            break;
                        case 3:
                            e.setAddOnIcon(temp);
                            break;
                        default:
                            break;
                    }
                }
                if (e.getType() == null || "".equals(e.getType().trim())) {
                    e.setType(ElementType.TEXT.getType());
                }
                elements.add(e);
            }
        }
    }

    private List<Element> elements = new ArrayList<Element>();

    public void addLabel(String labelName) {
        addElement(labelName, ElementType.LABEL, ElementLength.COL_1_HALF);
    }

    public void addText(String id) {
        addElement(id, ElementType.TEXT, ElementLength.COL_2);
    }

    public void addSelect(String id) {
        addElement(id, ElementType.SELECT, ElementLength.COL_2);
    }

    public void addTextarea(String id) {
        addElement(id, ElementType.TEXTAREA, ElementLength.COL_9);
    }

    public void addDate(String id) {
        addElement(id, ElementType.DATE, ElementLength.COL_2);
    }

    public void addTime(String id) {
        addElement(id, ElementType.TIME, ElementLength.COL_2);
    }

    public void addDateTime(String id) {
        addElement(id, ElementType.DATETIME, ElementLength.COL_2);
    }

    public void addElement(String id, ElementType type) {
        addElement(id, type, null, null);
    }

    public void addElement(String id, ElementType type, ElementLength length) {
        addElement(id, type, length, null);
    }

    public void addElement(String id, ElementType type, ElementLength length, String addOnIcon) {
        if (id == null) {
            id = "t_" + System.currentTimeMillis();
        }
        if (type == null) {
            type = ElementType.TEXT;
        }
        if (length == null) {
            length = ElementLength.COL_2;
        }
        Element element = new Element(id, type, length);
        if (addOnIcon != null && !"".equals(addOnIcon.trim())) {
            element.setAddOnIcon(addOnIcon);
        }
        addElement(element);
    }

    public void addElement(Element element) {
        if (elements == null) {
            elements = new ArrayList<Element>();
        }
        elements.add(element);
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    /**
     * @author miles
     * @datetime 13-12-24 上午10:19
     */
    public static class TabEditPage extends EditPage {

        /**
         * 页签的名字
         */
        private List<Tab> tabs = new ArrayList<Tab>();

        public List<Tab> getTabs() {
            return tabs;
        }

        public void addTab(Tab tab) {
            if (tabs == null) {
                tabs = new ArrayList<Tab>();
            }
            tabs.add(tab);
        }

        public void addTab(String id, String label, String url) {
            addTab(new Tab(id, label, url));
        }

        public void setTabs(List<Tab> tabs) {
            this.tabs = tabs;
        }
    }
}
