package eccrm.base.datadic.vo;

import com.ycrl.base.common.CommonVo;

/**
 * @author Michael
 */
public class DataDicItemVo extends CommonVo {

    // 显示名称
    private String label;

    // 属性名称
    private String fieldName;

    // 条件类型（多个值使用逗号进行分隔）：> 、<、=、in
    private String conditionType;

    // 值类型：input、select、radio
    private String valueType;

    private String value;

    // 值来源：文本输入、系统参数、业务参数、员工、机构、岗位、时间
    private String valueSource;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueSource() {
        return valueSource;
    }

    public void setValueSource(String valueSource) {
        this.valueSource = valueSource;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
