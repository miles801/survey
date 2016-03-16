package eccrm.core.constant;

/**
 * Created by miles on 2014/4/1.
 */
public enum CommonStatus {
    /**
     * 采集中
     */
    COLLECTING(1),
    /**
     * 未启用
     */
    NOT_START(2),
    /**
     * 正常
     */
    NORMAL(3),
    /**
     * 已关闭
     */
    CLOSED(4),
    /**
     * 已注销
     */
    CANCELED(5);
    private int status;

    CommonStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
