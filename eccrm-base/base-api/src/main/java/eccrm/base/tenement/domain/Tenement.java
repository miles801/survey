package eccrm.base.tenement.domain;

import eccrm.base.attachment.AttachmentSymbol;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

import java.util.Date;

/**
 * @author miles
 * @datetime 2014-03-14
 */
public class Tenement implements EnumSymbol, AttachmentSymbol {
    private String id;
    private String name;
    private String code;
    private String companyScale;
    private String website;
    private String industry;
    private String province;
    private String city;
    private String county;
    private String address;
    private String rentType;
    private String userScale;
    //资源是否独占
    private Boolean singleResource;
    private Date startDate;
    private Date endDate;
    private String description;
    private String remark;
    private String principalEmployeeId;
    @EnumClass(TenementStatus.class)
    private Integer status;
    private String creatorId;
    private String creatorName;
    private String modifierId;
    private String modifierName;
    private Date createdDatetime;
    private Date modifiedDatetime;

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

    public String getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getUserScale() {
        return userScale;
    }

    public void setUserScale(String userScale) {
        this.userScale = userScale;
    }

    public Boolean getSingleResource() {
        return singleResource;
    }

    public void setSingleResource(Boolean singleResource) {
        this.singleResource = singleResource;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(Date modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    @Override
    public String businessId() {
        return id;
    }

    public String getPrincipalEmployeeId() {
        return principalEmployeeId;
    }

    public void setPrincipalEmployeeId(String principalEmployeeId) {
        this.principalEmployeeId = principalEmployeeId;
    }
}
