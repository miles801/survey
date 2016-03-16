package eccrm.base.notebook.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.notebook.bo.NoteBookBo;
import eccrm.base.notebook.dao.NoteBookDao;
import eccrm.base.notebook.domain.NoteBook;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: shenbb
 * @datetime: 2014-03-22
 */
@Repository("noteBookDao")
public class NoteBookDaoImpl extends HibernateDaoHelper implements NoteBookDao {
    @Override
    public String save(NoteBook noteBook) {
        return (String) getSession().save(noteBook);
    }

    @Override
    public void update(NoteBook noteBook) {
        getSession().update(noteBook);
    }

    @Override
    public List<NoteBook> query(NoteBookBo bo) {
        Criteria criteria = createCriteria(NoteBook.class).addOrder(Order.desc("createdDatetime"));
        initCriteria(criteria, bo);
        // 只查询个人的便签
        criteria.addOrder(Order.desc("createdDatetime"));
        return criteria.list();
    }

    @Override
    public Long getTotal(NoteBookBo bo) {
        Criteria criteria = createRowCountsCriteria(NoteBook.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + NoteBook.class.getName() + " n where n.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public NoteBook findById(String id) {
        return (NoteBook) getSession().get(NoteBook.class, id);
    }

    private void initCriteria(Criteria criteria, NoteBookBo bo) {
        if (bo == null) bo = new NoteBookBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
        criteria.add(Restrictions.eq("creatorId", SecurityContext.getUserId()));
    }
}