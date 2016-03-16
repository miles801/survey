package com.ycrl.utils.tree;

import java.lang.annotation.*;

/**
 * 树节点的上级ID
 *
 * @author Michael
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface TreeParentId {
}
