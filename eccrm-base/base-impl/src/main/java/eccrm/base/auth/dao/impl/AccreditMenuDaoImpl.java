package eccrm.base.auth.dao.impl;

import eccrm.base.position.domain.PositionEmp;
import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.exception.Argument;
import eccrm.base.auth.bo.AccreditMenuBo;
import eccrm.base.auth.dao.AccreditMenuDao;
import eccrm.base.auth.domain.AccreditMenu;
import eccrm.base.menu.domain.Menu;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * @author:Michael
 */
@Repository("accreditMenuDao")
public class AccreditMenuDaoImpl extends HibernateDaoHelper implements AccreditMenuDao {

    @Override
    public String save(AccreditMenu accreditMenu) {
        return (String) getSession().save(accreditMenu);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AccreditMenu> query(AccreditMenuBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(AccreditMenuBo bo) {
        Criteria criteria = createRowCountsCriteria(AccreditMenu.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(AccreditMenuBo bo) {
        Criteria criteria = createCriteria(AccreditMenu.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     */
    private void initCriteria(Criteria criteria, AccreditMenuBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        if (bo == null) bo = new AccreditMenuBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
    }

    @Override
    public void deleteByDeptId(String deptId) {
        Argument.isEmpty(deptId, "删除岗位权限时，岗位ID不能为空!");
        getSession().createQuery("delete from " + AccreditMenu.class.getName() + " a where a.deptId=?")
                .setParameter(0, deptId)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> queryMenuIds(String[] deptIds) {
        if (deptIds == null || deptIds.length < 1) {
            return new ArrayList<String>();
        }
        return getSession().createQuery("select a.resourceId from " + AccreditMenu.class.getName() + " a where a.deptId in(:ids)")
                .setParameterList("ids", deptIds)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Menu> queryMenus(String[] deptIds) {
        if (deptIds == null || deptIds.length < 1) {
            return new ArrayList<Menu>();
        }
        String subHql = "select distinct a.resourceId from " + AccreditMenu.class.getName() + " a where a.deptId in(:ids)";
        List<String> amList = getSession().createQuery(subHql)
                .setParameterList("ids", deptIds)
                .list();
        if (amList == null || amList.isEmpty()) {
            return new ArrayList<Menu>();
        }
        return createCriteria(Menu.class)
                .add(Restrictions.in("id", amList))
                .addOrder(Order.asc("sequenceNo"))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Menu> queryMenus(String empId) {
        Argument.isEmpty(empId, "查询个人权限时，员工ID不能为空!");
        // 查询个人的岗位
        Session session = getSession();
        logger.debug("查询个人权限[" + empId + "]");
        String ppHql = "select distinct pe.positionId from " + PositionEmp.class.getName() + " pe where pe.empId=?";
        List<String> positionList = session.createQuery(ppHql)
                .setParameter(0, empId).list();
        /*String poHql = "select pe.orgId from " + PositionEmp.class.getName() + " pe where pe.empId=?";
        List<String> orgList = session.createQuery(poHql)
                .setParameter(0, empId)
                .list();*/

        // 查询个人的所有岗位的权限
        // 查询通用岗位的权限
        if (positionList == null || positionList.isEmpty()) {
            return null;
        }
        List<Menu> menus = getSession().createQuery("from " + Menu.class.getName() + " m where m.id in(select distinct am.resourceId from " + AccreditMenu.class.getName() + " am where am.deptId in(:deptIds)) order by m.sequenceNo asc")
                .setParameterList("deptIds", positionList)
                .list();
        return menus;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Menu> queryAccreditMenuRoot(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        // 查询个人的岗位（实际上这里登录人的ID为系统中员工的ID）
        Session session = getSession();

        String personalPositionHql = "select distinct pe.positionId from " + PositionEmp.class.getName() + " pe where pe.empId=?";
        List<String> positionList = session.createQuery(personalPositionHql)
                .setParameter(0, userId)
                .list();
        if (positionList == null || positionList.isEmpty()) {
            return null;
        }

        // 查询指定岗位的有效的权限
        List<Menu> menus = getSession().createQuery("from " + Menu.class.getName() + " m where (m.id in(select distinct am.resourceId from " + AccreditMenu.class.getName() + " am where am.deptId in(:deptIds)) or m.authorization=false ) " +
                " and m.status='" + CommonStatus.ACTIVE.getValue() + "' and m.show=true and m.type in('" + Menu.TYPE_SYSTEM + "','" + Menu.TYPE_NAV + "') order by m.sequenceNo asc")
                .setParameterList("deptIds", positionList)
                .list();
        return menus;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Menu> queryAccreditMenus(String userId, String systemMenuId) {
        if (StringUtils.isAnyEmpty(userId, systemMenuId)) {
            return null;
        }
        // 查询个人的岗位（实际上这里登录人的ID为系统中员工的ID）
        Session session = getSession();
        String personalPositionHql = "select distinct pe.positionId from " + PositionEmp.class.getName() + " pe where pe.empId=?";
        List<String> positionList = session.createQuery(personalPositionHql)
                .setParameter(0, userId)
                .list();
        if (positionList == null || positionList.isEmpty()) {
            return null;
        }

        // 查询指定岗位的有效的权限
        List<Menu> menus = getSession().createQuery("from " + Menu.class.getName() + " m where (m.id in(select distinct am.resourceId from " + AccreditMenu.class.getName() + " am where am.deptId in(:deptIds)) or m.authorization=false ) " +
                " and m.status='" + CommonStatus.ACTIVE.getValue() + "' and m.show=true and m.type ='" + Menu.TYPE_MENU + "' and m.path like '/" + systemMenuId + "%' order by m.sequenceNo asc")
                .setParameterList("deptIds", positionList)
                .list();
        return menus;
    }
}