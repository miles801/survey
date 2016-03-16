package eccrm.base.log.vo;

import com.ycrl.base.common.CommonVo;

/**
 * @author Michael
 */
public class OperateLogVo extends CommonVo {

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
