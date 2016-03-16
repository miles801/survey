package eccrm.base.log.domain;

import com.ycrl.base.common.CommonDomain;

/**
 * @author Michael
 * 
 */
public class OperateLog extends CommonDomain {
    private String operateType;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}
