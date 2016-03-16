package eccrm.utils.codeutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 13-12-24 上午10:40
 */
public class Element {
    /**
     * 类型
     */
    private String type;
    /**
     * 长度
     */
    private String length;
    /**
     * 名称
     */
    private String name;

    /**
     * 该字段只是在radio和checkbox时才会有效
     */
    private List<String> items;
    /**
     * 追加图标样式（只有在add-on类型时才起作用）
     */
    private String addOnIcon;

    public Element() {
    }

    public Element(String name) {
        this.name = name;
        type = ElementType.TEXT.getType();
        length = ElementLength.COL_2.getLength();
    }

    public Element(String name, ElementType type, ElementLength length) {
        this.name = name;
        this.type = type.getType();
        this.length = length.getLength();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getName() {
        if(name!=null){
            if(name.indexOf("(")>0){
                name=name.substring(0,name.indexOf("("));
            }
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddOnIcon() {
        return addOnIcon;
    }

    public void setAddOnIcon(String addOnIcon) {
        this.addOnIcon = addOnIcon;
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String... item) {
        if (item == null || item.length < 1) {
            return;
        }
        if (items == null) {
            items = new ArrayList<String>();
        }
        for (String foo : item) {
            String temp[] = foo.split(",");
            for (String t : temp) {
                if ("".equals(t.trim())) {
                    continue;
                }
                items.add(t);
            }
        }
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
