package eccrm.base.auth.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.SystemContainer;
import eccrm.base.auth.dao.AccreditDataDao;
import eccrm.base.auth.domain.AccreditData;
import eccrm.base.menu.dao.ResourceDao;
import eccrm.base.position.dao.PositionEmpDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("accreditDataDao")
public class AccreditDataDaoImpl extends HibernateDaoHelper implements AccreditDataDao {
    /**
     * 根据指定员工id过滤岗位的过滤器
     */
    public static final String ORG_FILTER = "AD_POSITION_FILTER";
    public static final String ORG_FILTER_PARAM = "empId";

    @Override
    public String save(AccreditData accreditData) {
        return (String) getSession().save(accreditData);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> queryResourceCodeByPerson(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        // 利用Hibernate过滤器来实现根据员工进行过滤的功能
        Session session = getSession();
        session.enableFilter(ORG_FILTER)
                .setParameter(ORG_FILTER_PARAM, userId);
        return session.createCriteria(AccreditData.class)
                .setProjection(Projections.property("resourceCode"))
                .list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> queryResourceCodeByDept(String[] deptIds) {
        if (deptIds == null || deptIds.length < 1) {
            return null;
        }
        return getSession().createCriteria(AccreditData.class)
                .setProjection(Projections.property("resourceCode"))
                .add(Restrictions.in("deptId", deptIds))
                .list();
    }

    @Override
    public AccreditData queryAccreditResource(String deptId, String resourceCode) {
        if (StringUtils.isAnyEmpty(deptId, resourceCode)) {
            return null;
        }
        return (AccreditData) createCriteria(AccreditData.class)
                .add(Restrictions.eq("deptId", deptId))
                .add(Restrictions.eq("resourceCode", resourceCode))
                .addOrder(Order.asc("resourceCode"))
                .uniqueResult();
    }

    @Override
    public void deleteAccreditByDept(String deptId, String dataResourceId) {
        if (StringUtils.isAnyBlank(deptId, dataResourceId)) {
            return;
        }
        getSession().createQuery("delete from " + AccreditData.class.getName() + " ad where ad.deptId=? and ad.resourceId=?")
                .setParameter(0, deptId)
                .setParameter(1, dataResourceId)
                .executeUpdate();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AccreditData> queryPersonalAllDataResource(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);
        Assert.notNull(positionEmpDao, "查询员工被授权的数据资源时：" + PositionEmpDao.class.getName() + "没有被实例化!");
        ResourceDao resourceDao = SystemContainer.getInstance().getBean(ResourceDao.class);
        Assert.notNull(resourceDao, "查询员工被授权的数据资源时：" + ResourceDao.class.getName() + "没有被实例化!");
        return createCriteria(AccreditData.class)
                .add(Property.forName("id").in(
                        DetachedCriteria.forClass(AccreditData.class)
                                .setProjection(Projections.distinct(Projections.id()))
                                .add(Property.forName("deptId").in(positionEmpDao.findEmpPositionIds(userId)))
                                .add(Property.forName("resourceId").in(resourceDao.queryValidDataResource()))
                )).list();
    }
}