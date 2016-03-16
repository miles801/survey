package com.ycrl.core.hibernate.criteria;

/**
 * 条件匹配模式，默认是=
 *
 * @author Michael
 */
public enum MatchModel {
    /**
     * 等于（默认）
     */
    EQ,
    LIKE,
    NE,
    /**
     * 如果是IN，则值必须是Collection类型
     */
    IN,
    NOT,
    GT,
    LT,
    GE,
    LE,
    NOT_IN,
    NULL,
    EMPTY
}
