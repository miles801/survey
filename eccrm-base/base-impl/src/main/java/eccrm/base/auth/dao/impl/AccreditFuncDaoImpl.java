package eccrm.base.auth.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.SystemContainer;
import eccrm.base.auth.dao.AccreditFuncDao;
import eccrm.base.auth.domain.AccreditFunc;
import eccrm.base.menu.dao.ResourceDao;
import eccrm.base.position.dao.PositionEmpDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("accreditFuncDao")
public class AccreditFuncDaoImpl extends HibernateDaoHelper implements AccreditFuncDao {

    @Override
    public String save(AccreditFunc accreditFunc) {
        return (String) getSession().save(accreditFunc);
    }

    @Override
    public void deleteByDeptId(String deptId) {
        if (StringUtils.isEmpty(deptId)) return;
        getSession().createQuery("delete from " + AccreditFunc.class.getName() + " af where af.deptId=?")
                .setParameter(0, deptId)
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<String> queryResourceCodeByDept(String[] deptIds) {
        if (deptIds == null || deptIds.length < 1) return null;
        return getSession().createCriteria(AccreditFunc.class)
                .setProjection(Projections.property("resourceCode"))
                .add(Restrictions.in("deptId", deptIds))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> queryEmpResourceCode(String empId) {
        PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);
        Assert.notNull(positionEmpDao, "查询员工的被授权的操作资源编号时：" + PositionEmpDao.class.getName() + "没有被实例化!");

        ResourceDao resourceDao = SystemContainer.getInstance().getBean(ResourceDao.class);
        Assert.notNull(resourceDao, "查询员工的被授权的操作资源编号时：" + ResourceDao.class.getName() + "没有被实例化!");
        return createCriteria(AccreditFunc.class)
                .setProjection(Projections.distinct(Projections.property("resourceCode")))
                .add(Property.forName("deptId").in(positionEmpDao.findEmpPositionIds(empId)))
                .add(Property.forName("resourceId").in(resourceDao.queryValidFuncResource()))
                .list();
    }
}