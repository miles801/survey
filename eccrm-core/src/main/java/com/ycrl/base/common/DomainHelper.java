package com.ycrl.base.common;

import com.ycrl.core.context.SecurityContext;
import com.ycrl.utils.uuid.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author Michael
 */
public class DomainHelper {
    /**
     * 将当前上下文信息加入到新建操作中
     * 初始化创建人、租户、创建时间，如果id为空，同时生成id
     */
    public static void initAddInfo(CommonDomain commonDomain) {
        if (StringUtils.isEmpty(commonDomain.getId())) {
            commonDomain.setId(UUIDGenerator.generate());
        }
        commonDomain.setCreatedDatetime(new Date());
        commonDomain.setCreatorId(SecurityContext.getUserId());
        commonDomain.setCreatorName(SecurityContext.getEmpName());
        commonDomain.setTenementId(SecurityContext.getTenementId());
    }

    /**
     * 将当前上下文信息加入到更新操作中
     * 初始化更新人、更新时间
     */
    public static void initModifyInfo(CommonDomain commonDomain) {
        commonDomain.setModifiedDatetime(new Date());
        commonDomain.setModifierId(SecurityContext.getUserId());
        commonDomain.setModifierName(SecurityContext.getEmpName());
    }
}
