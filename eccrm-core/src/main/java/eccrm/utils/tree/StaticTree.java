package eccrm.utils.tree;

import java.io.Serializable;

/**
 * 静态树，一次性加载构建树的所有数据构建成树
 * 适用于数据层级较少的树的展示
 *
 * @author miles
 * @datetime 2014/3/28 17:43
 */
public interface StaticTree<T extends StaticTree, K extends Serializable> extends Tree<T, K> {

    void setParent(T parent);

    /**
     * 往树的枝干中添加孩子
     */
    T addChild(T child);

}
