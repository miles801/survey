package eccrm.utils.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @author miles
 * @datetime 2014/6/29 11:28
 */
public interface PathTreeDao<T extends PathTree, K extends Serializable> {


    T findById(K id);

    /**
     * 根据id查询所有子节点，包含隔代节点，不包含当前节点
     */
    List<T> queryChildren(K id);
}
