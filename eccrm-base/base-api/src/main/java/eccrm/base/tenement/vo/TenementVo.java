package eccrm.base.tenement.vo;


import java.util.Date;

/**
 * @author miles
 * @datetime 2014-03-14
 */

public class TenementVo extends CrmBaseVo {

    private String name;
    private String code;
    private String companyScale;
    private String website;
    private String industry;
    private String province;
    private String provinceName;
    private String city;
    private String cityName;
    private String county;
    private String countyName;
    private String address;
    private String rentType;
    private String userScale;
    //资源是否独占
    private Boolean singleResource;
    private Date startDate;
    private Date endDate;
    private String remark;
    private String principalEmployeeId;
    private String principalEmployeeName;
    private String principalEmployeeCode;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrincipalEmployeeId() {
        return principalEmployeeId;
    }

    public void setPrincipalEmployeeId(String principalEmployeeId) {
        this.principalEmployeeId = principalEmployeeId;
    }

    public String getPrincipalEmployeeName() {
        return principalEmployeeName;
    }

    public void setPrincipalEmployeeName(String principalEmployeeName) {
        this.principalEmployeeName = principalEmployeeName;
    }

    public String getPrincipalEmployeeCode() {
        return principalEmployeeCode;
    }

    public void setPrincipalEmployeeCode(String principalEmployeeCode) {
        this.principalEmployeeCode = principalEmployeeCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
