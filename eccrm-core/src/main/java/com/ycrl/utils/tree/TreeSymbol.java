package com.ycrl.utils.tree;

import java.lang.annotation.*;

/**
 * 表明此类为树形
 *
 * @author Michael
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface TreeSymbol {

    String id() default "id";

    String parentId() default "parentId";

    String path() default "path";
}
