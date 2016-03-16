package eccrm.base.menu.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.exception.Argument;
import eccrm.base.menu.bo.MenuNavBo;
import eccrm.base.menu.dao.MenuNavDao;
import eccrm.base.menu.domain.MenuNav;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Michael
 */
@Repository("menuNavDao")
public class MenuNavDaoImpl extends HibernateDaoHelper implements MenuNavDao {

    @Override
    public String save(MenuNav menuNav) {
        return (String) getSession().save(menuNav);
    }

    @Override
    public void update(MenuNav menuNav) {
        getSession().update(menuNav);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MenuNav> query(MenuNavBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        criteria.addOrder(Order.asc("sequenceNo"));
        return criteria.list();
    }

    @Override
    public long getTotal(MenuNavBo bo) {
        Criteria criteria = createRowCountsCriteria(MenuNav.class);
        initCriteria(criteria, bo);
        Long result = (Long) criteria.uniqueResult();
        return result == null ? 0l : result;
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + MenuNav.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public MenuNav findById(String id) {
        Argument.isEmpty(id, "查询时ID不能为空!");
        return (MenuNav) getSession().get(MenuNav.class, id);
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(MenuNavBo bo) {
        Criteria criteria = createCriteria(MenuNav.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     * 不能够在该方法里添加排序操作，否则在聚合查询时将会报错
     */
    private void initCriteria(Criteria criteria, MenuNavBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        if (bo == null) bo = new MenuNavBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase()
                .excludeProperty("status"));
        String status = bo.getStatus();
        if (StringUtils.isNotEmpty(status)) {
            criteria.add(Restrictions.eq("status", status));
        }
    }

}