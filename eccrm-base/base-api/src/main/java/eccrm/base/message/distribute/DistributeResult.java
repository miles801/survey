package eccrm.base.message.distribute;

/**
 * @author Michael
 */
public class DistributeResult {
    // 状态码
    private String code;
    // 是否成功
    private boolean success;
    // 消息描述
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
