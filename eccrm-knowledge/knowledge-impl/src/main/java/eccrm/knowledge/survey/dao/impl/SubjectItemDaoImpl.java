package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.knowledge.survey.dao.SubjectItemDao;
import eccrm.knowledge.survey.domain.SubjectItem;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Michael
 */
@Repository("subjectItemDao")
public class SubjectItemDaoImpl extends HibernateDaoHelper implements SubjectItemDao {
    @Override
    public void save(SubjectItem subjectItem) {
        getSession().save(subjectItem);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SubjectItem> queryBySubjectId(String subjectId) {
        if (StringUtils.isBlank(subjectId)) {
            return null;
        }
        return createCriteria(SubjectItem.class)
                .add(Restrictions.eq("subjectId", subjectId))
                .list();
    }

    @Override
    public void deleteBySubjectId(String subjectId) {
        if (StringUtils.isBlank(subjectId)) {
            return;
        }
        getSession().createQuery("delete from " + SubjectItem.class.getName() + " si where si.subjectId=?")
                .setParameter(0, subjectId)
                .executeUpdate();
    }
}
