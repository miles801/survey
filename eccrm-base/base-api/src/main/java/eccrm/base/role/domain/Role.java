package eccrm.base.role.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

import java.util.Date;

/**
 * @author miles
 * @datetime 2014-03-26
 */
public class Role extends CrmBaseDomain implements EnumSymbol {

    private String name;
    private String code;
    private Date startDate;
    private String pinyin;
    private Date endDate;

    @EnumClass(RoleStatus.class)
    private String status;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
