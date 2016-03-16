package eccrm.utils.tree;

import java.io.Serializable;

/**
 * @author miles
 * @datetime 2014/6/29 11:25
 */
public interface PathTree<T extends PathTree, K extends Serializable> extends Tree<T, K> {

    String getPath();

    void setPath(String path);

}
