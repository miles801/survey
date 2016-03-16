package eccrm.base.parameter.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.pager.Pager;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.parameter.bo.BusinessParamTypeBo;
import eccrm.base.parameter.dao.BusinessParamTypeDao;
import eccrm.base.parameter.domain.BusinessParamType;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: miles
 * @datetime: 2014-07-02
 */
@Repository("businessParamTypeDao")
public class BusinessParamTypeDaoImpl extends HibernateDaoHelper implements BusinessParamTypeDao {

    @Override
    public String save(BusinessParamType businessParamType) {
        return (String) getSession().save(businessParamType);
    }

    @Override
    public void update(BusinessParamType businessParamType) {
        getSession().update(businessParamType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusinessParamType> query(BusinessParamTypeBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(BusinessParamTypeBo bo) {
        Criteria criteria = createRowCountsCriteria(BusinessParamType.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public BusinessParamType findById(String id) {
        return (BusinessParamType) getSession().get(BusinessParamType.class, id);
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(BusinessParamTypeBo bo) {
        Criteria criteria = createCriteria(BusinessParamType.class);
        initCriteria(criteria, bo);
        if (Pager.getOrder() == null || !Pager.getOrder().hasNext()) {
            criteria.addOrder(Order.asc("sequenceNo"));
        }
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     */
    private void initCriteria(Criteria criteria, BusinessParamTypeBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        if (bo == null) bo = new BusinessParamTypeBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusinessParamType> queryOther(String id) {
        Criteria criteria = getDefaultCriteria(new BusinessParamTypeBo());
        criteria.add(Restrictions.eq("status", CommonStatus.ACTIVE.getValue()));
        if (StringUtils.isEmpty(id)) {
            return criteria.list();
        }
        return criteria.add(Restrictions.ne("id", id))
                .add(Restrictions.not(Restrictions.like("path", id, MatchMode.ANYWHERE)))
                .list();
    }

    @Override
    public void delete(BusinessParamType type) {
        if (type == null) {
            throw new IllegalArgumentException("删除参数类型时,参数不能为空!");
        }
        getSession().delete(type);
    }

    @Override
    public boolean hasCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("编号不能为空!");
        }
        Criteria criteria = createRowCountsCriteria(BusinessParamType.class);
        long total = (Long) criteria.add(Restrictions.eq("code", code)).uniqueResult();
        return total > 0;
    }

    @Override
    public boolean hasName(String parentId, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        Criteria criteria = createRowCountsCriteria(BusinessParamType.class);
        criteria.add(Restrictions.eq("name", name));
        if (parentId != null) {
            criteria.add(Restrictions.eq("parent.id", parentId));
        }
        long total = (Long) criteria.uniqueResult();
        return total > 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusinessParamType> queryChildren(String id) {
        Argument.isEmpty(id, "查询孩子节点时,没有当前节点获得ID!");
        return getDefaultCriteria(null)
                .add(Restrictions.like("path", "/" + id + "/", MatchMode.ANYWHERE))
                .add(Restrictions.ne("id", id))
                .list();
    }

    @Override
    public String getName(String code) {
        Argument.isEmpty(code, "编号不能为空!");
        return (String) createCriteria(BusinessParamType.class)
                .setProjection(Projections.property("name"))
                .add(Restrictions.eq("code", code))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusinessParamType> queryUsing() {
        return getDefaultCriteria(null)
                .add(Restrictions.in("status", new String[]{CommonStatus.ACTIVE.getValue(), CommonStatus.CANCELED.getValue()}))
                .list();
    }
}