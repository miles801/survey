package eccrm.utils.codeutils;

/**
 * Created by miles on 14-3-12.
 */
public enum FileType {
    POM_ROOT(0),
    //接口层
    POM_API(100),
    ENTITY(101),
    VO(102),
    BO(103),
    DAO(104),
    SERVICE(105),
    MAPPING(106),

    //实现层
    POM_IMPL(200),
    DAO_IMPL(202),
    SERVICE_IMPL(203),
    JUNIT_DAO(204),
    JUNIT_SERVICE(205),

    //web层
    POM_WEB(300),
    CONTROLLER(301);

    private Integer type;

    FileType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
