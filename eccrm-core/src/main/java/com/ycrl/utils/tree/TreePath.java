package com.ycrl.utils.tree;

import java.lang.annotation.*;

/**
 * 树的访问路径
 *
 * @author Michael
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface TreePath {
}
