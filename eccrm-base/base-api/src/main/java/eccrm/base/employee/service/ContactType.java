package eccrm.base.employee.service;

/**
 * Created by liuxq on 14-9-13.
 * 爱好特点所有的业务参数的code
 */
public interface ContactType {
    static String SEX = "BP_SEX" ;
    static String BP_GOAL_TYPE = "BP_GOAL_TYPE" ;//目标类型
    static String BP_CERTPAPER = "BP_CERTPAPER" ;//证件类型
    static String NATIONAL = "BP_NATION";//民族
    static String BP_COUNTRY = "BP_COUNTRY";//国籍
    static String BP_ZZMM = "BP_ZZMM";//政治面貌
    static String MARRIAGE = "BP_MARRIAGE";//婚姻状况
    static String BP_EDU = "BP_EDU";//学历
    static String BP_XW = "BP_XW";//学位
    static String BP_EMPTYPE = "BP_EMPTYPE";//员工类型
    static String BP_ZHIW = "BP_ZHIW";//职务
    //联系方式类型
    static String CONT_SUP = "SP_CHANNEL";
    //联系方式类型
    static String CONT_SUB = "SP_CHANNEL_TYPE";
    //联系方式类型状态
    static String CONT_TYPE_STATUS = "SP_COMMON_STATE";
    //状态
    static String CUS_CONT_INFO_STATUS = "SP_COMMON_STATE" ;
}
