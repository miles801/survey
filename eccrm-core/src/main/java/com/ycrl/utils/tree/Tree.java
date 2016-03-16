package com.ycrl.utils.tree;

import java.io.Serializable;

/**
 * 树接口，实现了该类后可以公共TreeFactory将一个集合封装成一棵树型结构
 *
 * @author miles
 * @datetime 13-12-13 下午1:40
 */
public interface Tree<T extends Tree, K extends Serializable> {

    K getId();

    T getParent();
}
