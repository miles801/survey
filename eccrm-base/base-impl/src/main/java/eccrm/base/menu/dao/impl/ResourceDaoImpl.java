package eccrm.base.menu.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.pager.Pager;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.menu.bo.ResourceBo;
import eccrm.base.menu.dao.ResourceDao;
import eccrm.base.menu.domain.Resource;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Michael
 */
@Repository("resourceDao")
public class ResourceDaoImpl extends HibernateDaoHelper implements ResourceDao {
    @Override
    public String save(Resource entity) {
        return (String) getSession().save(entity);
    }

    @Override
    public void update(Resource entity) {
        getSession().update(entity);
    }


    @Override
    public void delete(Resource menu) {
        if (menu == null || StringUtils.isEmpty(menu.getId())) {
            throw new IllegalArgumentException("删除菜单时，菜单不能为空!");
        }
        getSession().delete(menu);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Resource> queryChildren(String id) {
        Argument.isEmpty(id, "查询当前节点的所有子节点时，id不能为空!");
        Criteria criteria = getDefaultCriteria(null);
        criteria.add(Restrictions.like("path", "/" + id + "/", MatchMode.ANYWHERE));
        criteria.add(Restrictions.ne("id", id));
        return criteria.list();
    }


    @Override
    public Resource findById(String id) {
        return (Resource) getSession().get(Resource.class, id);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Resource> query(ResourceBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(ResourceBo bo) {
        Criteria criteria = createRowCountsCriteria(Resource.class);
        Pager.addOrder("sequenceNo", false);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }

    /**
     * 获得默认的菜单高级查询对象
     */
    private Criteria getDefaultCriteria(ResourceBo bo) {
        Criteria criteria = createCriteria(Resource.class);
        initCriteria(criteria, bo);
        return criteria;
    }


    private void initCriteria(Criteria criteria, ResourceBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("cirteria must not be null!");
        }
        if (bo == null) {
            bo = new ResourceBo();
        }
        criteria.add(Example.create(bo)
                .excludeProperty("type")
                .excludeProperty("status")
                .enableLike(MatchMode.ANYWHERE).ignoreCase());
        if (Pager.getOrder() == null) {
            criteria.addOrder(Order.asc("type"));
            criteria.addOrder(Order.asc("sequenceNo"));
        }
        if (StringUtils.isNotEmpty(bo.getType())) {
            criteria.add(Restrictions.eq("type", bo.getType()));
        }
        if (StringUtils.isNotEmpty(bo.getStatus())) {
            criteria.add(Restrictions.eq("status", bo.getStatus()));
        }
    }

    @Override
    public boolean hasName(String id, String name) {
        Argument.isEmpty(name, "查询菜单名称是否重复时,菜单名称不能为空!");
        Criteria criteria = createRowCountsCriteria(Resource.class)
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
        Criteria criteria = createRowCountsCriteria(Resource.class);
        criteria.add(Restrictions.eq("code", code));
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Resource> queryOther(String id) {
        Criteria criteria = getDefaultCriteria(null);
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.not(
                    Restrictions.like("path", "/" + id + "/", MatchMode.ANYWHERE)
            ));
        }
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Resource> queryValidResource() {
        Criteria criteria = getDefaultCriteria(null);
        criteria.add(Restrictions.eq("status", CommonStatus.ACTIVE.getValue()))
                .add(Restrictions.eq("show", true))
                .add(Restrictions.eq("type", Resource.TYPE_URL));
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Resource> queryByIds(Set<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return getDefaultCriteria(null)
                .add(Restrictions.in("id", ids))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> queryAllLimitDataResource() {
        return getSession()
                .createCriteria(Resource.class)
                .setProjection(Projections.distinct(Projections.property("code")))
                .add(Restrictions.eq("type", Resource.TYPE_DATA))
                .add(Restrictions.eq("status", CommonStatus.ACTIVE.getValue()))
                .add(Restrictions.eq("authorization", true))
                .list();
    }

    @Override
    public DetachedCriteria queryValidFuncResource() {
        return DetachedCriteria.forClass(Resource.class)
                .setProjection(Projections.distinct(Projections.id()))
                .add(Restrictions.eq("status", com.ycrl.base.common.CommonStatus.ACTIVE.getValue()))
                .add(Restrictions.eq("type", Resource.TYPE_ELEMENT));
    }

    @Override
    public DetachedCriteria queryValidDataResource() {
        return DetachedCriteria.forClass(Resource.class)
                .setProjection(Projections.distinct(Projections.id()))
                .add(Restrictions.eq("status", com.ycrl.base.common.CommonStatus.ACTIVE.getValue()))
                .add(Restrictions.eq("type", Resource.TYPE_DATA));
    }
}
