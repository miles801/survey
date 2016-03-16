package eccrm.base.datadic.dao;

import java.util.List;

/**
 * @author Michael
 */
public class DataDicCondition {
    private String className;
    private String code;
    private String name;
    private List<DataDicItemCondition> conditions;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataDicItemCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<DataDicItemCondition> conditions) {
        this.conditions = conditions;
    }
}
