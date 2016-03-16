package eccrm.base.region.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.pager.OrderNode;
import com.ycrl.core.pager.Pager;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.region.bo.RegionBo;
import eccrm.base.region.dao.RegionDao;
import eccrm.base.region.domain.Region;
import eccrm.utils.Argument;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;


/**
 * @author: miles
 * @datetime: 2014-03-25
 */
@Repository("regionDao")
public class RegionDaoImpl extends HibernateDaoHelper implements RegionDao {
    @Override
    public String save(Region region) {
        return (String) getSession().save(region);
    }

    @Override
    public void update(Region region) {
        getSession().update(region);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Region> query(RegionBo bo) {
        Criteria criteria = createCriteria(Region.class);
        initCriteria(criteria, bo);
        OrderNode order = Pager.getOrder();
        if (order == null) {
            //默认按照类型和序列号的升序进行排序
            criteria.addOrder(Order.asc("type"));
            criteria.addOrder(Order.asc("sequenceNo"));
        }
        return criteria.list();
    }

    @Override
    public Long getTotal(RegionBo bo) {
        Criteria criteria = createRowCountsCriteria(Region.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public int deleteById(String id) {
        Argument.isEmpty(id, "根据ID删除行政区域时,ID不能为空!");
        return getSession().createQuery("delete from " + Region.class.getSimpleName() + " r where r.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public Region findById(Serializable id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null!");
        }
        return (Region) getSession().get(Region.class, id);
    }

    @Override
    public synchronized int nextSequenceNo(String parentId) {
        Integer max = 0;
        if (parentId == null) {
            max = (Integer) getSession().createQuery("select max(r.sequenceNo) from " + Region.class.getName() + " r where r.parent is null").uniqueResult();
        } else {
            max = (Integer) getSession().createQuery("select max(r.sequenceNo) from  " + Region.class.getName() + " r where r.parent.id = ?")
                    .setParameter(0, parentId).uniqueResult();
        }
        return (max == null ? 1 : max + 1);
    }

    @Override
    public void evict(Region region) {
        if (region == null) return;
        getSession().evict(region);
    }

    private void initCriteria(Criteria criteria, RegionBo bo) {
        if (bo == null) bo = new RegionBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
        //只查询符合条件的根节点
        Boolean root = bo.getRoot();
        if (root != null && root) {
            criteria.add(Restrictions.isNull("parent"));
        }

        Boolean valid = bo.getValid();
        if (valid != null && valid) {
            criteria.add(Restrictions.eq("status", CommonStatus.ACTIVE.getValue()));
        }
        //只查询指定id的直接子节点
        String parentId = bo.getParentId();
        if (parentId != null && !"".equals(parentId.trim())) {
            criteria.add(Restrictions.eq("parent.id", parentId));
        }
    }

    @Override
    public long childSize(Serializable id) {
        return (Long) createRowCountsCriteria(Region.class)
                .add(Restrictions.eq("parent.id", id))
                .uniqueResult();
    }

    @Override
    public String getName(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null!");
        }
        return (String) createCriteria(Region.class)
                .setProjection(Projections.property("name"))
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public String getNameByCode(String cityCode) {
        com.ycrl.core.exception.Argument.isEmpty(cityCode, "根据区号查询城市名称时,城市区号不能为空!");
        return (String) createCriteria(Region.class)
                .setProjection(Projections.property("name"))
                .add(Restrictions.eq("code", cityCode))
                .uniqueResult();
    }

    @Override
    public Region getBelongProvence(String city) {
        if (city == null || "".equals(city.trim())) {
            return null;
        }
        // 查询上级ID的子查询
        DetachedCriteria parentIdDetached = DetachedCriteria.forClass(Region.class)
                .setProjection(Projections.property("parentId"))
                .add(Restrictions.idEq(city));
        return (Region) createCriteria(Region.class)
                .add(Property.forName("id").eq(parentIdDetached))
                .setFirstResult(0)
                .setMaxResults(1)
                .uniqueResult();
    }
}