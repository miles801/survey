package eccrm.base.datadic.dao;

/**
 * @author Michael
 */
public class DataDicItemCondition {
    // AND / OR
    private String operate;
    // 属性名称
    private String fieldName;
    // 属性的值
    private String value;
    // 值类型：string、int、double
    private String valueType;

    // 值来源
    private String valueSource;

    // 条件运算符：>、=、>=、<、<=、in（如果是in，则将会以逗号将值进行分隔成数组）
    private String conditionType;


    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
}
