package com.ycrl.utils.tree;

import java.io.Serializable;

/**
 * @author miles
 * @datetime 2014/4/1 11:14
 */
public interface DynamicTreeDao<T extends DynamicTree> {

    void evict(T t);

    T findById(Serializable id);

    /**
     * 查询指定id的节点的孩子个数
     *
     * @param id
     * @return
     */
    long childSize(Serializable id);


}
