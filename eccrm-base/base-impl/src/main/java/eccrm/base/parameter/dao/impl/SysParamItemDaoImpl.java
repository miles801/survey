package eccrm.base.parameter.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.exception.Argument;
import com.ycrl.core.pager.Pager;
import eccrm.base.parameter.bo.SysParamItemBo;
import eccrm.base.parameter.dao.SysParamItemDao;
import eccrm.base.parameter.domain.SysParamItem;
import eccrm.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: miles
 * @datetime: 2014-06-20
 */
@Repository("sysParamItemDao")
public class SysParamItemDaoImpl extends HibernateDaoHelper implements SysParamItemDao {

    @Override
    public String save(SysParamItem sysParamItem) {
        return (String) getSession().save(sysParamItem);
    }

    @Override
    public void update(SysParamItem sysParamItem) {
        getSession().update(sysParamItem);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SysParamItem> query(SysParamItemBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(SysParamItemBo bo) {
        Criteria criteria = createRowCountsCriteria(SysParamItem.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void delete(SysParamItem item) {
        if (item == null) {
            throw new IllegalArgumentException("删除参数选项时,参数不能为空!");
        }
        getSession().delete(item);
    }

    @Override
    public SysParamItem findById(String id) {
        return (SysParamItem) getSession().get(SysParamItem.class, id);
    }


    @Override
    public boolean hasValue(String typeCode, String value) {
        if (StringUtils.hasEmpty(typeCode, value)) {
            throw new IllegalArgumentException("查询参数选项的值是否重复时,参数个数不匹配!");
        }
        Criteria criteria = createRowCountsCriteria(SysParamItem.class);
        long total = (Long) criteria.add(Restrictions.eq("type", typeCode))
                .add(Restrictions.eq("value", value))
                .uniqueResult();
        return total > 0;
    }

    @Override
    public boolean hasName(String typeCode, String name) {
        if (StringUtils.hasEmpty(typeCode, name)) {
            throw new IllegalArgumentException("查询参数选项的名称是否重复时,参数个数不匹配!");
        }
        Criteria criteria = createRowCountsCriteria(SysParamItem.class);
        long total = (Long) criteria.add(Restrictions.eq("type", typeCode))
                .add(Restrictions.eq("name", name))
                .uniqueResult();
        return total > 0;
    }


    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(SysParamItemBo bo) {
        Criteria criteria = createCriteria(SysParamItem.class);
        initCriteria(criteria, bo);
        // 默认按照排序号进行排序
        if (Pager.getOrder() == null || !Pager.getOrder().hasNext()) {
            criteria.addOrder(Order.asc("sequenceNo"));
        }
        return criteria;
    }

    private void initCriteria(Criteria criteria, SysParamItemBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        if (bo == null) bo = new SysParamItemBo();
        criteria.add(Example.create(bo)
                .excludeProperty("type")
                .excludeProperty("status")
                .enableLike(MatchMode.ANYWHERE).ignoreCase());
        if (!StringUtils.isEmpty(bo.getStatus())) {
            criteria.add(Restrictions.eq("status", bo.getStatus()));
        }
        if (!StringUtils.isEmpty(bo.getType())) {
            criteria.add(Restrictions.eq("type", bo.getType()));
        }
    }

    @Override
    public String queryName(String type, String value) {
        Argument.isEmpty(type, "系统参数编号不能为空!");
        Argument.isEmpty(value, "系统参数的值不能为空!");
        return (String) createCriteria(SysParamItem.class)
                .setProjection(Projections.property("name"))
                .add(Restrictions.eq("type", type))
                .add(Restrictions.eq("value", value))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SysParamItem> fetchCascade(String typeCode, String value) {
        if (org.apache.commons.lang3.StringUtils.isAnyEmpty(typeCode, value)) {
            return null;
        }
        return getSession().createCriteria(SysParamItem.class)
                .add(Restrictions.eq("cascadeTypeCode", typeCode))
                .add(Restrictions.eq("cascadeItemValue", value))
                .addOrder(Order.asc("sequenceNo"))
                .list();
    }
}