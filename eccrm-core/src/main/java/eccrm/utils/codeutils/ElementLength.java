package eccrm.utils.codeutils;

/**
 * @author miles
 * @datetime 13-12-24 上午10:37
 */
public enum ElementLength {
    COL_1("col-1"),
    COL_2("col-2"),
    COL_3("col-3"),
    COL_4("col-4"),
    COL_5("col-5"),
    COL_6("col-6"),
    COL_7("col-7"),
    COL_8("col-8"),
    COL_9("col-9"),
    COL_10("col-10"),
    COL_1_HALF("col-1-half"),
    COL_2_HALF("col-2-half"),
    COL_3_HALF("col-3-half"),
    COL_4_HALF("col-4-half"),
    COL_5_HALF("col-5-half"),
    COL_6_HALF("col-6-half"),
    COL_7_HALF("col-7-half"),
    COL_8_HALF("col-8-half"),
    COL_9_HALF("col-9-half");
    private String length;

    ElementLength(String length) {
        this.length = length;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
