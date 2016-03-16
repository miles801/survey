package eccrm.base.log;

/**
 * @author Michael
 */
public enum OperateType {


    ADD("add"),
    MODIFY("modify"),
    DELETE("delete");
    private String type;

    OperateType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
