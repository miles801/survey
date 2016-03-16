package eccrm.base.datadic.domain;

import com.ycrl.base.common.CommonDomain;

import javax.persistence.Transient;
import java.util.List;

/**
 * 数据字典
 *
 * @author Michael
 */
public class DataDic extends CommonDomain {

    private String name;    // 名称

    private String className;   // 要作用的实体类名称

    private String code;    // 编号，唯一，客户端需要

    @Transient
    private List<DataDicItem> items;

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<DataDicItem> getItems() {
        return items;
    }

    public void setItems(List<DataDicItem> items) {
        this.items = items;
    }
}
