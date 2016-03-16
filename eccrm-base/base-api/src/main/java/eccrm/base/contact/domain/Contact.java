package eccrm.base.contact.domain;

import com.ycrl.base.common.CommonDomain;

import java.util.Date;

/**
 * 联络记录
 */
public class Contact extends CommonDomain {
    private String resourceType;    // 客户类型*
    private String resourceId;      // 客户ID*
    private String resourceName;    // 客户姓名
    private String code;            // 编号
    private String linkmanId;       // 联系人ID
    private String linkmanName;     // 联系人姓名
    private String contactType;     // 联系人类型
    private String contactID;       // 联络方式（例如：email，微信，短信，电话）*
    private String country;         // 国家
    private String province;        // 省
    private String city;            // 市
    private String district;        // 区
    private String address;         // 地址或内容*
    private String postCode;        // 邮编
    private Date startDatetime;     // 开始时间*
    private Date endDatetime;       // 结束时间
    private String contactItem;     // 联络事项1
    private String contactItem2;    // 联络事项2
    private String contactItem3;    // 联络事项3
    private String ndoeId;          // 节点
    private String ndoeName;
    private String niceId;          // 录音ID
    private String niceUrl;         // 录音地址
    private String stageId;
    private String stageName;
    private Integer isBespeak;
    private String bespeakPhone;
    private String contactDirect;   // 联络方向*
    private String contactMethod;   // 联络方式
    private String content;         // 描述
    private String resultType;      // 结果类型
    private String remark;          // 处理说明
    private String nextLinkmanId;   // 联系人ID
    private String nextLinkmanName; // 联系人姓名
    private String nextContactType; // 联系类型
    private String nextContactMethod;// 下次联系方式
    private String nextCountry;     // 国家
    private String nextProvince;    // 省
    private String nextCity;        // 市
    private String nextDistrict;    // 区
    private String nextAddress;     // 国家
    private String nextPostCode;    // 邮编
    private Date nextTime;          // 结束时间
    private String satisfaction;    // 满意度

    private String businessId;      // 业务ID
    private String flowKey;         // 流程编号（工单编号）
    private String flowType;        // 流程类型

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getIsBespeak() {
        return isBespeak;
    }

    public void setIsBespeak(Integer isBespeak) {
        this.isBespeak = isBespeak;
    }

    public String getBespeakPhone() {
        return bespeakPhone;
    }

    public void setBespeakPhone(String bespeakPhone) {
        this.bespeakPhone = bespeakPhone;
    }

    public String getNiceId() {
        return niceId;
    }

    public void setNiceId(String niceId) {
        this.niceId = niceId;
    }

    public String getNiceUrl() {
        return niceUrl;
    }

    public void setNiceUrl(String niceUrl) {
        this.niceUrl = niceUrl;
    }

    public String getNdoeId() {
        return ndoeId;
    }

    public void setNdoeId(String ndoeId) {
        this.ndoeId = ndoeId;
    }

    public String getNdoeName() {
        return ndoeName;
    }

    public void setNdoeName(String ndoeName) {
        this.ndoeName = ndoeName;
    }

    public String getContactItem2() {
        return contactItem2;
    }

    public void setContactItem2(String contactItem2) {
        this.contactItem2 = contactItem2;
    }

    public String getContactItem3() {
        return contactItem3;
    }

    public void setContactItem3(String contactItem3) {
        this.contactItem3 = contactItem3;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getNextContactMethod() {
        return nextContactMethod;
    }

    public void setNextContactMethod(String nextContactMethod) {
        this.nextContactMethod = nextContactMethod;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getContactItem() {
        return contactItem;
    }

    public void setContactItem(String contactItem) {
        this.contactItem = contactItem;
    }

    public String getContactDirect() {
        return contactDirect;
    }

    public void setContactDirect(String contactDirect) {
        this.contactDirect = contactDirect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

   /* public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

    public String getNextLinkmanId() {
        return nextLinkmanId;
    }

    public void setNextLinkmanId(String nextLinkmanId) {
        this.nextLinkmanId = nextLinkmanId;
    }

    public String getNextLinkmanName() {
        return nextLinkmanName;
    }

    public void setNextLinkmanName(String nextLinkmanName) {
        this.nextLinkmanName = nextLinkmanName;
    }

    public String getNextContactType() {
        return nextContactType;
    }

    public void setNextContactType(String nextContactType) {
        this.nextContactType = nextContactType;
    }

    public String getNextCountry() {
        return nextCountry;
    }

    public void setNextCountry(String nextCountry) {
        this.nextCountry = nextCountry;
    }

    public String getNextProvince() {
        return nextProvince;
    }

    public void setNextProvince(String nextProvince) {
        this.nextProvince = nextProvince;
    }

    public String getNextCity() {
        return nextCity;
    }

    public void setNextCity(String nextCity) {
        this.nextCity = nextCity;
    }

    public String getNextDistrict() {
        return nextDistrict;
    }

    public void setNextDistrict(String nextDistrict) {
        this.nextDistrict = nextDistrict;
    }

    public String getNextAddress() {
        return nextAddress;
    }

    public void setNextAddress(String nextAddress) {
        this.nextAddress = nextAddress;
    }

    public String getNextPostCode() {
        return nextPostCode;
    }

    public void setNextPostCode(String nextPostCode) {
        this.nextPostCode = nextPostCode;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }
}
