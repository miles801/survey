package eccrm.utils.tree;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;

/**
 * @author miles
 * @datetime 2014/6/29 11:31
 */
public class PathTreeBuilder {

    @SuppressWarnings("unchecked")
    public static <T extends PathTree, K extends Serializable> void buildAfterSave(PathTreeDao dao, PathTree<T, K> tree) {
        if (tree == null) {
            throw new IllegalArgumentException("tree must not be null!");
        }
        Serializable id = tree.getId();
        if (id == null) {
            throw new IllegalArgumentException("This method can only be called after entity saved!");
        }
        PathTree parent = tree.getParent();
        if (parent != null && parent.getId() != null) {
            PathTree p = dao.findById(parent.getId());
            if (p == null) {
                throw new EntityNotFoundException(parent.getId().toString());
            }
            tree.setPath(p.getPath() + id + "/");
        } else {
            tree.setPath("/" + id + "/");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends PathTree, K extends Serializable> void buildBeforeUpdate(PathTreeDao dao, PathTree<T, K> pathTree) {
        Serializable id = pathTree.getId();
        //获取新上级菜单路径
        PathTree newParent = pathTree.getParent();
        String newPath = "/" + id + "/";
        if (newParent != null && newParent.getId() != null) {
            String path = dao.findById(newParent.getId()).getPath();
            newPath = path + id + "/";
        }

        //设置自身path
        pathTree.setPath(newPath);

        //根据id查询所有孩子节点，遍历孩子节点，设置新的path
        List<T> children = dao.queryChildren(id);
        if (children != null && children.size() > 0) {
            for (T child : children) {
                child.setPath(newPath + child.getId() + "/");
            }
        }

    }
}
