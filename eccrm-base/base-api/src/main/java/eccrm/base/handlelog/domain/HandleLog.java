package eccrm.base.handlelog.domain;

import java.util.Date;
import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.base.tenement.domain.Tenement;
import eccrm.base.user.domain.User;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
/**
* @author wangsd
* @datetime 2014-04-16
*/
public class HandleLog extends CrmBaseDomain  implements  EnumSymbol {

    //只写业务属性即可
	private  String handleType;//操作类型
	@EnumClass(HandleLogResult.class)
	private  String handleResult;//处理结果
	private  String handleContent;//处理内容
	private Date startDate;
	private Date endDate;
	
	private  String businessId;//一个标识字段  用来确定这条处理记录是属于那一个对应的具体记录    如确定是  属于哪一天日报的处理记录
	@EnumClass(HandleLogBusinessType.class)
	private String  businessType;//业务分类  用来确定这条记录属于哪一种类型
	
	
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	public String getHandleContent() {
		return handleContent;
	}
	public void setHandleContent(String handleContent) {
		this.handleContent = handleContent;
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
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
}
