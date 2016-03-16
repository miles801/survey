package eccrm.base.menu.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.pager.Pager;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.menu.bo.MenuBo;
import eccrm.base.menu.dao.MenuDao;
import eccrm.base.menu.domain.Menu;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by miles on 13-11-21.
 */
@SuppressWarnings("ALL")
@Repository("menuDao")
public class MenuDaoImpl extends HibernateDaoHelper implements MenuDao {

    @Override
    public String save(Menu entity) {
        return (String) getSession().save(entity);
    }

    @Override
    public void update(Menu entity) {
        getSession().update(entity);
    }


    @Override
    public void delete(Menu menu) {
        if (menu == null || StringUtils.isEmpty(menu.getId())) {
            throw new IllegalArgumentException("删除菜单时，菜单不能为空!");
        }
        getSession().delete(menu);
    }


    @Override
    public List<Menu> queryChildren(String id) {
        Argument.isEmpty(id, "查询当前节点的所有子节点时，id不能为空!");
        Criteria criteria = getDefaultCriteria(null);
        criteria.add(Restrictions.like("path", "/" + id + "/", MatchMode.ANYWHERE));
        criteria.add(Restrictions.ne("id", id));
        return criteria.list();
    }


    @Override
    public Menu findById(String id) {
        Menu menu = (Menu) getSession().get(Menu.class, id);
        return menu;
    }


    @Override
    public List<Menu> query(MenuBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(MenuBo bo) {
        Criteria criteria = createRowCountsCriteria(Menu.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }

    /**
     * 获得默认的菜单高级查询对象
     *
     * @param bo
     * @return
     */
    private Criteria getDefaultCriteria(MenuBo bo) {
        Criteria criteria = createCriteria(Menu.class);
        initCriteria(criteria, bo);
        if (Pager.getOrder() == null) {
            criteria.addOrder(Order.asc("sequenceNo"));
        }
        return criteria;
    }


    private void initCriteria(Criteria criteria, MenuBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("cirteria must not be null!");
        }
        if (bo == null) {
            bo = new MenuBo();
        }
        criteria.add(Example.create(bo)
                .excludeProperty("type")
                .excludeProperty("status")
                .enableLike(MatchMode.ANYWHERE).ignoreCase());
        if (StringUtils.isNotEmpty(bo.getType())) {
            criteria.add(Restrictions.eq("type", bo.getType()));
        }
        if (StringUtils.isNotEmpty(bo.getStatus())) {
            criteria.add(Restrictions.eq("status", bo.getStatus()));
        }

        // 添加过滤器，只能查询授权给自己的
        /*Set<String> positionIds = PersonalPermissionContext.getPositions();
        AccreditMenuDao accreditMenuDao = SystemContainer.getInstance().getBean(AccreditMenuDao.class);
        if (accreditMenuDao != null && positionIds != null && !positionIds.isEmpty()) {
            String[] arr = positionIds.toArray(new String[0]);
            List<String> ids = accreditMenuDao.queryMenuIds(arr);
            if (ids == null || ids.isEmpty()) {
                criteria.add(Restrictions.isEmpty("id"));
            } else {
                criteria.add(Restrictions.in("id", ids));
            }
        }*/
    }

    @Override
    public boolean hasName(String id, String name) {
        Argument.isEmpty(name, "查询菜单名称是否重复时,菜单名称不能为空!");
        Criteria criteria = createRowCountsCriteria(Menu.class)
                .add(Restrictions.eq("name", name));
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.eq("parent.id", id));
        } else {
            criteria.add(Restrictions.isNull("parent.id"));
        }
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public boolean hasCode(String code) {
        Argument.isEmpty(code, "查询菜单编号是否重复时,编号不能为空");
        Criteria criteria = createRowCountsCriteria(Menu.class);
        criteria.add(Restrictions.eq("code", code));
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public List<Menu> queryOther(String id) {
        Criteria criteria = getDefaultCriteria(null);
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.not(
                    Restrictions.like("path", "/" + id + "/", MatchMode.ANYWHERE)
            ));
        }
        return criteria.list();
    }

    @Override
    public List<Menu> queryValidMenu() {
        Criteria criteria = getDefaultCriteria(null);
        criteria.add(Restrictions.eq("status", CommonStatus.ACTIVE.getValue()))
                .add(Restrictions.eq("show", true));
        return criteria.list();
    }

    @Override
    public void batchUpdateChildrenStatus(String parentId, String status) {
        Assert.hasText(parentId, "批量更新菜单状态:需要更新的节点ID不能为空!");
        Assert.hasText(parentId, "批量更新菜单状态:需要指定状态!");
        getSession().createQuery("update " + Menu.class.getName() + " m set m.status=? where m.path like :path")
                .setParameter(0, status)
                .setParameter("path", "%/" + parentId + "/%")
                .executeUpdate();
    }

    @Override
    public void batchDelete(String id) {
        Assert.hasText(id, "批量删除菜单:需要指定当前节点ID!");
        getSession().createQuery("delete " + Menu.class.getName() + " m where path like :path")
                .setParameter("path", "%/" + id + "/%")
                .executeUpdate();
    }
}
