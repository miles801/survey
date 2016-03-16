package com.ycrl.utils.gson;

/**
 * @author miles
 * @datetime 2014/6/30 16:53
 */
public class ResponseData {
    /**
     * 操作成功后返回{success:true}
     */
    private Boolean success;
    /**
     * 操作异常后返回{error:''}
     */
    private Boolean error;
    /**
     * 操作失败后返回{fail:true}
     */
    private Boolean fail;
    /**
     * 其他数据
     */
    private Object data;
    /**
     * 消息描述
     */
    private String message;
    /**
     * 状态码
     */
    private String code;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getFail() {
        return fail;
    }

    public void setFail(Boolean fail) {
        this.fail = fail;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
