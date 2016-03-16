package eccrm.base.parameter.domain;

import eccrm.base.common.enums.CommonStatus;
import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

/**
 * @author miles
 * @datetime 2014-06-20
 */
public class SysParamItem extends CrmBaseDomain implements EnumSymbol {
    private String name;
    private String value;
    @EnumClass(CommonStatus.class)
    private String status;
    /**
     * 所属类型的编号
     */
    private String type;

    /**
     * 级联类型的编号
     */
    private String cascadeTypeCode;
    /**
     * 级联类型选项的值
     */
    private String cascadeItemValue;

    private Integer sequenceNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCascadeTypeCode() {
        return cascadeTypeCode;
    }

    public void setCascadeTypeCode(String cascadeTypeCode) {
        this.cascadeTypeCode = cascadeTypeCode;
    }

    public String getCascadeItemValue() {
        return cascadeItemValue;
    }

    public void setCascadeItemValue(String cascadeItemValue) {
        this.cascadeItemValue = cascadeItemValue;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
}
