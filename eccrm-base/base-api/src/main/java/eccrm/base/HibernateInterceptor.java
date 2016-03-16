package eccrm.base;

import com.ycrl.base.common.CommonDomain;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.utils.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>用于在Hibernate操作数据库，保存实体、更新实体时做一些额外的操作</p>
 * <p>需要在配置Hibernate的时候指定属性entityInterceptor</p>
 * @author Michael
 */
public class HibernateInterceptor extends EmptyInterceptor {
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        //设置额外信息
        if (entity instanceof CommonDomain) {
            CommonDomain o = (CommonDomain) entity;
            // 设置租户
            if (org.apache.commons.lang3.StringUtils.isBlank(o.getTenementId())) {
                int tenementIndex = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.TENEMENT_ID);
                if (tenementIndex != -1 && !StringUtils.isEmpty(SecurityContext.getTenementId())) {
                    o.setTenementId(SecurityContext.getTenementId());
                    state[tenementIndex] = SecurityContext.getTenementId();
                }
            }
            // 强制设置时间
            int createdDatetimeIndex = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.CREATED_DATETIME);
            if (createdDatetimeIndex != -1) {
                Date now = new Date();
                o.setCreatedDatetime(now);
                state[createdDatetimeIndex] = now;
            }
            // 强制设置创建人
            int userId = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.CREATOR_ID);
            if (userId != -1 && !StringUtils.isEmpty(SecurityContext.getUserId())) {
                state[userId] = SecurityContext.getUserId();
                o.setCreatorId(SecurityContext.getUserId());
            }

            // 强制设置创建人名称
            int userName = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.CREATOR_NAME);
            if (userName != -1 && !StringUtils.isEmpty(SecurityContext.getEmpName())) {
                state[userName] = SecurityContext.getEmpName();
                o.setCreatorName(SecurityContext.getEmpName());
            }
            return true;
        }

        if (entity instanceof CrmBaseDomain) {
            CrmBaseDomain o = (CrmBaseDomain) entity;
            // 设置租户
            int tenementIndex = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.TENEMENT_ID);
            if (tenementIndex != -1 && !StringUtils.isEmpty(SecurityContext.getTenementId())) {
                o.setTenementId(SecurityContext.getTenementId());
                state[tenementIndex] = SecurityContext.getTenementId();
            }

            // 强制设置时间
            int createdDatetimeIndex = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.CREATED_DATETIME);
            if (createdDatetimeIndex != -1) {
                Date now = new Date();
                o.setCreatedDatetime(now);
                state[createdDatetimeIndex] = now;
            }

            // 强制设置创建人
            int userId = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.CREATOR_ID);
            if (userId != -1 && !StringUtils.isEmpty(SecurityContext.getUserId())) {
                state[userId] = SecurityContext.getUserId();
                o.setCreatorId(SecurityContext.getUserId());
            }

            // 强制设置创建人名称
            int userName = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.CREATOR_NAME);
            if (userName != -1 && !StringUtils.isEmpty(SecurityContext.getEmpName())) {
                state[userName] = SecurityContext.getEmpName();
                o.setCreatorName(SecurityContext.getEmpName());
            }
            return true;
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        //设置额外信息
        if (entity instanceof CommonDomain) {
            CommonDomain o = (CommonDomain) entity;
            // 设置修改时间
            int modifiedDatetimeIndex = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.MODIFIED_DATETIME);
            if (modifiedDatetimeIndex != -1) {
                currentState[modifiedDatetimeIndex] = new Date();
            }
            // 设置修改人
            int modifierId = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.MODIFIER_ID);
            if (modifierId != -1) {
                currentState[modifierId] = SecurityContext.getUserId();
            }

            int modifierName = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.MODIFIER_NAME);
            if (modifierName != -1) {
                currentState[modifierName] = SecurityContext.getEmpName();
            }
            return super.onFlushDirty(o, id, currentState, previousState, propertyNames, types);
        }
        if (entity instanceof CrmBaseDomain) {
            CrmBaseDomain o = (CrmBaseDomain) entity;
            // 设置修改时间
            int modifiedDatetimeIndex = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.MODIFIED_DATETIME);
            if (modifiedDatetimeIndex != -1) {
                currentState[modifiedDatetimeIndex] = new Date();
            }
            // 设置修改人
            int modifierId = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.MODIFIER_ID);
            if (modifierId != -1) {
                currentState[modifierId] = SecurityContext.getUserId();
            }

            int modifierName = ArrayUtils.indexOf(propertyNames, SystemPropertyConstant.MODIFIER_NAME);
            if (modifierName != -1) {
                currentState[modifierName] = SecurityContext.getEmpName();
            }
            return super.onFlushDirty(o, id, currentState, previousState, propertyNames, types);
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
