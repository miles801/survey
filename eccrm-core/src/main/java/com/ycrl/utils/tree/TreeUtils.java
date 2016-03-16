package com.ycrl.utils.tree;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Michael
 */
public class TreeUtils {
    private Session session;
    private Class<?> clazz;

    public TreeUtils(Class<?> clazz, Session session) {
        Assert.notNull(clazz);
        Assert.notNull(session);
        this.clazz = clazz;
        this.session = session;
    }

    /**
     * 重置当前类的当前节点相关的所有path
     */
    public void resetPath(String id) {

        // 必要条件检查
        Assert.hasText(id, "构建树的Path失败，树的当前节点的ID不能为空!");
        TreeSymbol tree = clazz.getAnnotation(TreeSymbol.class);
        Assert.notNull(tree, "构建树的Path失败，并没有在类[" + clazz.getName() + "]上面声明注解:" + TreeSymbol.class.getName());

        Object o = session.get(clazz, id);
        Assert.notNull(o, "ID为[" + id + "]的" + clazz.getName() + "的对象不存在!");


        // 获得各个必要属性的名称
        String idProperty = tree.id();
        String pIdProperty = tree.parentId();
        String pathProperty = tree.path();

        // 开启事务（如果没有开启）
        if (session.getTransaction() == null || !session.getTransaction().isActive()) {
            session.beginTransaction();
        }

        try {
            // 设置当前节点的PATH
            Field parentField = FieldUtils.getField(clazz, pIdProperty, true);
            Object parentId = parentField.get(o);
            String newPath = null;
            Field pathField = FieldUtils.getField(clazz, pathProperty, true);
            if (parentId == null) {
                newPath = "/" + id + "/";
            } else {
                String parentPath = (String) session.createCriteria(clazz) // 获得上级的path
                        .setProjection(Projections.property(pathProperty))
                        .add(Restrictions.eq(idProperty, parentId))
                        .setMaxResults(1)
                        .uniqueResult();
                newPath = parentPath + id + "/";
            }
            pathField.set(o, newPath);

            // 设置所有子节点（包括隔代节点）的PATH
            List children = session.createCriteria(clazz)
                    .add(Restrictions.like(pathProperty, "/" + id + "/", MatchMode.ANYWHERE))
                    .add(Restrictions.ne(idProperty, id))
                    .list();
            if (children != null && !children.isEmpty()) {
                for (Object child : children) {
                    String path = (String) pathField.get(child);
                    int length = ("/" + id + "/").length();
                    path = newPath + path.substring(path.indexOf("/" + id + "/") + length);
                    pathField.set(child, path);
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 重置上级的状态
     */
    public void resetParentStatus() {

    }
}
