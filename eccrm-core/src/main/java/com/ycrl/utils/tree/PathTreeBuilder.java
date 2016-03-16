package com.ycrl.utils.tree;


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

    /**
     * 更新当前实体类（StaticTree的实例）之前，同时更新当前实体类下的所有子节点的path属性
     *
     * @param dao      当前实体类对应的dao接口，实现了两个必须的接口
     * @param pathTree 实体类对象，必须实现PathTree接口
     * @param <T>      实体类的真正类型
     * @param <K>      实体类的id的类型，一般为String
     */
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
                // 原path
                String oldPath = child.getPath();
                // 从原path中，截取到.../id/，将这部分替换成新的path
                String p = oldPath.replaceAll("(/.+)*/" + id + "/", newPath);
                child.setPath(p);
            }
        }

    }
}
