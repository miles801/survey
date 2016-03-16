package eccrm.base.tenement.bo;

import eccrm.base.tenement.domain.Tenement;

import java.util.Date;

/**
 * @author miles
 * @datetime 2014-03-14
 */

public class TenementBo extends Tenement {
    /**
     * 到期时间
     */
    private Date invalidStartDate;
    private Date invalidEndDate;
    /**
     * 所在区域
     */
    private String region;
    /**
     * 是否有效
     */
    private Boolean valid;

    public Date getInvalidStartDate() {
        return invalidStartDate;
    }

    public void setInvalidStartDate(Date invalidStartDate) {
        this.invalidStartDate = invalidStartDate;
    }

    public Date getInvalidEndDate() {
        return invalidEndDate;
    }

    public void setInvalidEndDate(Date invalidEndDate) {
        this.invalidEndDate = invalidEndDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
