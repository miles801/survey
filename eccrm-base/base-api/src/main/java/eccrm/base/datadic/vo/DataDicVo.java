package eccrm.base.datadic.vo;

import com.ycrl.base.common.CommonVo;

import java.util.List;

/**
 * @author Michael
 */
public class DataDicVo extends CommonVo {

    private String name;    // 名称

    private String code;    // 编号，唯一，客户端需要

    private String className;   // 类名称

    private List<DataDicItemVo> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataDicItemVo> getItems() {
        return items;
    }

    public void setItems(List<DataDicItemVo> items) {
        this.items = items;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
