package eccrm.base.user.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.base.user.bo.GroupBo;
import eccrm.base.user.dao.GroupDao;
import eccrm.base.user.domain.Group;
import com.ycrl.core.pager.Pager;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author miles
 * @datetime 2014-07-03
 */
@Repository("groupDao")
public class GroupDaoImpl extends HibernateDaoHelper implements GroupDao {

    @Override
    public String save(Group group) {
        return (String) getSession().save(group);
    }

    @Override
    public void update(Group group) {
        getSession().update(group);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> query(GroupBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(GroupBo bo) {
        Criteria criteria = createRowCountsCriteria(Group.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void delete(Group group) {
        getSession().delete(group);
    }

    @Override
    public Group findById(String id) {
        return (Group) getSession().get(Group.class, id);
    }

    @Override
    public boolean hasName(String groupId, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("查询指定名称的组是否存在时,组的名称不能为空!");
        }
        Criteria criteria = createRowCountsCriteria(Group.class);
        criteria.add(Restrictions.eq("name", name));
        if (StringUtils.isNotEmpty(groupId)) {
            criteria.add(Restrictions.eq("parent.id", groupId));
        } else {
            criteria.add(Restrictions.isNull("parent"));
        }
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> queryChildren(String id) {
        Argument.isEmpty(id, "根据ID查询子节点时,id不能为空!");
        return createCriteria(Group.class)
                .add(Restrictions.like("path", "/" + id + "/", MatchMode.ANYWHERE))
                .add(Restrictions.not(Restrictions.idEq(id)))
                .list();
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(GroupBo bo) {
        Criteria criteria = createCriteria(Group.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     */
    private void initCriteria(Criteria criteria, GroupBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("初始化Criteria对象时,Criteria不能为空!");
        }
        if (bo == null) bo = new GroupBo();
        // 添加默认排序
        if (Pager.getOrder() == null || !Pager.getOrder().hasNext()) {
            criteria.addOrder(Order.asc("sequenceNo"));
        }
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
    }

}