package eccrm.base.datadic.domain;

import com.ycrl.base.common.CommonDomain;

/**
 * 数据字段选项
 *
 * @author Michael
 */
public class DataDicItem extends CommonDomain {

    // 显示名称
    private String label;

    // 属性名称
    private String fieldName;

    // 条件类型（多个值使用逗号进行分隔）：> 、<、=、in
    private String conditionType;

    // 值类型：int/double/string
    private String valueType;

    // 值来源：文本输入、系统参数、业务参数、员工、机构、岗位、时间
    private String valueSource;

    private String value;

    // 所属数据字典
    private String dicId;   // 字典ID
    private String dicCode; // 字典编号
    private String dicName; // 字典名称，冗余

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

    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
