package eccrm.utils.codeutils;

/**
 * Created by miles on 13-12-31.
 */
public enum PageType {
    /**
     * 营销执行中的名单
     */
    MARKETING_EXECUTE_ORDER(1);
    private Integer type;

    PageType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
