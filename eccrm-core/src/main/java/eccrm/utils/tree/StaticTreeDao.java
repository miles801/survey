package eccrm.utils.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @author miles
 * @datetime 2014/3/28 17:27
 */
public interface StaticTreeDao<T extends StaticTree> {
    /**
     * 根据path查询所有的孩子节点，并且不包括自身
     *
     * @param id
     */
    List<T> queryChildren(Serializable id);

    T findById(Serializable id);

    void evict(T t);
}
