package com.ycrl.aop;

import com.ycrl.base.common.CommonDomain;
import com.ycrl.base.common.DomainHelper;
import org.aspectj.lang.JoinPoint;

/**
 * 对实体进行保存、更新操作时的aop
 * Created by Michael on 2014/10/19.
 */
public class DomainOperateAop {

    /**
     * 保存之前：
     * 如果没有id则使用UUIDGenerate生成一个id、设置创建时间、创建人、租户
     *
     * @throws Throwable
     */
    public void beforeSave(JoinPoint joinPoint) throws Throwable {
        Object[] objArr = joinPoint.getArgs();
        if (objArr != null && objArr.length > 0) {
            Object o = objArr[0];
            if (o instanceof CommonDomain) {
                CommonDomain domain = (CommonDomain) o;
                DomainHelper.initAddInfo(domain);
            }
        }
    }

    /**
     * 更新之前:
     * 设置更新人、更新时间
     *
     * @throws Throwable
     */
    public void beforeUpdate(JoinPoint joinPoint) throws Throwable {
        Object[] objArr = joinPoint.getArgs();
        if (objArr != null && objArr.length > 0) {
            Object o = objArr[0];
            if (o instanceof CommonDomain) {
                CommonDomain domain = (CommonDomain) o;
                DomainHelper.initModifyInfo(domain);
            }
        }
    }

}
