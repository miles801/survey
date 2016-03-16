package eccrm.base.user.service;

/**
 * @author miles
 * @datetime 2014/6/27 14:26
 */
public class ValidateResult {
    private LoginErrorCode code;
    private String userId;
    private String tenementId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenementId() {
        return tenementId;
    }

    public void setTenementId(String tenementId) {
        this.tenementId = tenementId;
    }

    public LoginErrorCode getCode() {
        return code;
    }

    public void setCode(LoginErrorCode code) {
        this.code = code;
    }
}
