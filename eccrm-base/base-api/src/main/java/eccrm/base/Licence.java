package eccrm.base;

import java.io.Serializable;
import java.util.Date;

/**
 * @author miles
 * @datetime 2014/4/24 17:57
 */
public class Licence implements Serializable {
    private String macAddress;
    private Date startDate;
    private Date endDate;

    protected Licence(String macAddress, Date startDate, Date endDate) {
        this.macAddress = macAddress;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
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

}
