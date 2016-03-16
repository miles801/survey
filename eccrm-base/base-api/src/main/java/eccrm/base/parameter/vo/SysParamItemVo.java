package eccrm.base.parameter.vo;

import eccrm.base.tenement.vo.CrmBaseVo;

/**
 * @author miles
 * @datetime 2014-06-20
 */

public class SysParamItemVo extends CrmBaseVo {

    private String name;
    private String value;
    private String status;
    private String statusName;
    /**
     * 级联类型编号
     */
    private String cascadeTypeCode;
    /**
     * 级联类型名称
     */
    private String cascadeTypeName;
    /**
     * 级联参数值（编号）
     */
    private String cascadeItemValue;
    /**
     * 级联参数名称
     */
    private String cascadeItemName;
    /**
     * 所属类型的编号
     */
    private String type;
    private String typeName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCascadeTypeName() {
        return cascadeTypeName;
    }

    public void setCascadeTypeName(String cascadeTypeName) {
        this.cascadeTypeName = cascadeTypeName;
    }

    public String getCascadeItemName() {
        return cascadeItemName;
    }

    public void setCascadeItemName(String cascadeItemName) {
        this.cascadeItemName = cascadeItemName;
    }
}
