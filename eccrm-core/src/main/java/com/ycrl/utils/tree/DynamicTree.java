package com.ycrl.utils.tree;


/**
 * 动态树，每次只加载指定节点下的所有直接孩子节点
 * 在hibernate实现中，通过一对多双向关联来处理和加载子节点的问题（通过获得孩子节点的个数来判断当前节点是否为叶子节点）
 * 适用于每次请求加载直接子节点的情况
 *
 * @author miles
 * @datetime 2014/3/28 17:43
 */
public interface DynamicTree extends Tree {
    /**
     * 设置是否为叶子节点
     *
     * @param leaf 叶子节点
     */
    void setLeaf(boolean leaf);

    DynamicTree getParent();
}
