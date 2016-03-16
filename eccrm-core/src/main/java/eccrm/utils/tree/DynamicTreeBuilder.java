package eccrm.utils.tree;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

/**
 * @author miles
 * @datetime 2014/4/1 11:20
 */
public class DynamicTreeBuilder {
    private DynamicTreeDao dao;

    public DynamicTreeBuilder(DynamicTreeDao dao) {
        if (dao == null) {
            throw new IllegalArgumentException("DynamicTreeDao must not be null!");
        }
        this.dao = dao;
    }

    /**
     * 用于在保存树之后重新设置path
     *
     * @param tree
     */
    public void save(DynamicTree tree) {
        if (tree == null) {
            throw new IllegalArgumentException("DynamicTree must not be null!");
        }
        Serializable id = tree.getId();
        if (id == null) {
            throw new IllegalArgumentException("This method can only be called after entity saved!");
        }
        DynamicTree parent = tree.getParent();
        if (parent != null && parent.getId() != null) {
            DynamicTree p = dao.findById(parent.getId());
            if (p == null) {
                throw new EntityNotFoundException(DynamicTree.class.getName() + ":" + parent.getId().toString());
            }
            p.setLeaf(false);
            //如果不是使用的hibernate，这里还需要手动的调用更新方法
        }
        //是否拥有孩子
        long size = dao.childSize(tree.getId());
        if (size > 0) {
            tree.setLeaf(false);
        } else {
            tree.setLeaf(true);
        }
    }

    public void update(DynamicTree tree) {
        if (tree == null) {
            throw new IllegalArgumentException("DynamicTree must not be null!");
        }
        Serializable id = tree.getId();
        if (id == null) {
            throw new IllegalArgumentException("ID of DynamicTree must not be null or empty!");
        }
        DynamicTree newParent = tree.getParent();
        DynamicTree old = dao.findById(tree.getId());
        if (old == null) {
            throw new EntityNotFoundException(tree.getId().toString());
        }
        DynamicTree oldParent = old.getParent();
        Serializable newPid = null, oldPid = null;
        if (newParent != null) {
            newPid = newParent.getId();
            newParent = dao.findById(newPid);
            if (newParent == null) {
                throw new EntityNotFoundException(newPid.toString());
            }
        }
        if (oldParent != null) {
            oldPid = oldParent.getId();
        }
        //移除上级
        if (newPid == null && oldPid != null) {
            if (dao.childSize(oldPid) < 2) {
                oldParent.setLeaf(true);
            }
        }
        //新增上级
        if (newPid != null && oldPid == null) {
            newParent.setLeaf(false);
        }
        //更新上级
        if (newPid != null && oldPid != null && !newPid.equals(oldPid)) {
            newParent.setLeaf(false);
            if (dao.childSize(oldPid) < 2) {
                oldParent.setLeaf(true);
            }
        }
        dao.evict(old);
        //如果不是使用的hibernate，需要手动的更新状态
    }

    /**
     * 注意：次方法需要在真正的删除方法之前执行
     *
     * @param treeId
     */
    public void delete(Serializable treeId) {
        if (treeId == null) {
            throw new IllegalArgumentException("treeId must not be null! ");
        }
        DynamicTree tree = dao.findById(treeId);
        if (tree == null) {
            throw new EntityNotFoundException(treeId.toString());
        }
        DynamicTree parent = tree.getParent();
        if (parent != null && dao.childSize(parent.getId()) < 2) {
            parent.setLeaf(true);
        }
        //如果不是使用hibernate，那么在这里还需要调用update方法去更新Tree的状态
    }
}
