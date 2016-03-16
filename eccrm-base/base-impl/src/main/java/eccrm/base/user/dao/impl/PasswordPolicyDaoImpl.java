package eccrm.base.user.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.base.user.dao.PasswordPolicyDao;
import eccrm.base.user.domain.PasswordPolicy;
import org.springframework.stereotype.Repository;


/**
 * @author miles
 * @datetime 2014-03-17
 */
@Repository("passwordPolicyDao")
public class PasswordPolicyDaoImpl extends HibernateDaoHelper implements PasswordPolicyDao {
    @Override
    public String save(PasswordPolicy passwordPolicy) {
        return (String) getSession().save(passwordPolicy);
    }

    @Override
    public void update(PasswordPolicy passwordPolicy) {
        getSession().update(passwordPolicy);
    }


    public PasswordPolicy get() {
        return (PasswordPolicy) getSession().createQuery("from " + PasswordPolicy.class.getName())
                .setMaxResults(1)
                .uniqueResult();
    }
}