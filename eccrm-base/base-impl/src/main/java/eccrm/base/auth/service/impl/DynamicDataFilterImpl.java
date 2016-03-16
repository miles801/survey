package eccrm.base.auth.service.impl;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.hibernate.filter.DynamicDataFilter;
import com.ycrl.core.hibernate.filter.FilterFieldType;
import eccrm.base.auth.domain.AccreditData;
import eccrm.base.auth.domain.AccreditDataType;
import eccrm.base.auth.service.PersonalPermissionContext;
import eccrm.base.menu.service.DataResourceContext;
import eccrm.base.position.dao.PositionEmpDao;
import eccrm.base.position.domain.PositionEmp;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * <p>动态数据过滤接口的实现</p>
 * <p>权限分为：</p>
 * <p>1.个人权限（默认，如果需要授权却没有进行授权），只查看自己的（自己创建或分配给自己的）</p>
 * <p>2.岗位权限</p>
 * <p>3.机构权限</p>
 * <p>4.系统权限</p>
 * <p>5.组合权限：上面4种任意出现0或1个，加上指定的系统或者机构</p>
 * <p>6.所有权限：</p>
 *
 * @author Michael
 */
@Service("dynamicDataFilter")
public class DynamicDataFilterImpl implements DynamicDataFilter {
    private Logger logger = Logger.getLogger(DynamicDataFilterImpl.class);
    private Set<String> notExistsFilter = new HashSet<String>();

    @Override
    public void addFilterCondition(Criteria criteria, final String filterName, String fieldName, FilterFieldType filterFieldType) {

        if (criteria == null) {
            return;
        }
        Assert.hasText(filterName, "启用数据过滤器时，没有指定过滤器的名称!");
        Assert.notNull(filterFieldType, "启用数据过滤器时，没有指定[字段类型]!");

        boolean hasGrant = false;// 用于判断是否设置了过滤器，如果没有设置则默认启用个人权限过滤器


        // 获得个人被授予的所有的数据权限
        Map<String, List<AccreditData>> accreditDataMap = PersonalPermissionContext.getDataResource();
        if (accreditDataMap != null && !accreditDataMap.isEmpty()) {
            // 根据过滤器名称获取指定的数据授权列表
            List<AccreditData> accreditDataList = accreditDataMap.get(filterName);
            if (accreditDataList != null && accreditDataList.size() > 0) {
                // 设置标志位，表示已经设置了权限
                hasGrant = true;
                boolean hasPosition = false;
                boolean hasOrg = false;
                boolean hasParam = false;
                boolean hasOrgAndChildren = false;
                Set<String> otherOrgIds = new HashSet<String>();        // 指定的机构
                Set<String> otherParams = new HashSet<String>();        // 指定的系统
                // 遍历所有被授权的数据权限集合
                for (AccreditData accreditData : accreditDataList) {
                    String accreditType = accreditData.getAccreditType();
                    // 如果是全部权限，则直接结束且不启用过滤器
                    if (AccreditDataType.ALL.equals(accreditType)) {                    // 全部权限
                        return;
                    } else if (AccreditDataType.POSITION.equals(accreditType)) {        // 岗位权限
                        hasPosition = true;
                    } else if (AccreditDataType.ORG.equals(accreditType)) {             // 组织机构权限
                        hasOrg = true;
                    } else if (AccreditDataType.ORG_AND_CHILDREN.equals(accreditType)) {// 组织机构及下级机构权限
                        hasOrgAndChildren = true;
                    } else if (AccreditDataType.PARAM.equals(accreditType)) {           // 系统权限
                        hasParam = true;
                    }
                    if (StringUtils.isNotBlank(accreditData.getOtherOrgIds())) {       // 指定机构
                        Collections.addAll(otherOrgIds, accreditData.getOtherOrgIds().split(","));
                    }
                    if (StringUtils.isNotBlank(accreditData.getOtherParam())) {         // 指定系统
                        Collections.addAll(otherParams, accreditData.getOtherParam().split(","));
                    }
                }

                // 初始化DetachedCriteria
                setCriteriaCondition(criteria, fieldName, filterFieldType, hasPosition, hasOrg, hasParam, hasOrgAndChildren, otherOrgIds, otherParams);
            }
        }

        // 如果当前数据权限没有被授予，判断该权限是否是需要授权的（从需要授权的集合中判断）
        // 如果需要授权而又没有授权，则默认开启个人权限
        if (!hasGrant) {
            Set<String> needPermissionResource = DataResourceContext.getInstance().getLimitDataResourceCodeList();
            if (needPermissionResource != null && needPermissionResource.contains(filterName)) {
                setCriteriaCondition(criteria, fieldName, filterFieldType, false, false, false, true, null, null);
            }
        }

    }

    /**
     * 设置Criteria的查询条件
     *
     * @param criteria          标准查询对象
     * @param hasPosition       是否有岗位权限
     * @param hasOrg            是否有组织机构权限
     * @param hasParam          是否有系统权限
     * @param hasOrgAndChildren 是否有组织机构及下级机构权限
     * @param otherOrgIds       指定的组织机构
     * @param otherParams       指定的系统
     */
    private void setCriteriaCondition(Criteria criteria, String fieldName, FilterFieldType filterFieldType, boolean hasPosition, boolean hasOrg, boolean hasParam, boolean hasOrgAndChildren, Set<String> otherOrgIds, Set<String> otherParams) {

        Disjunction disjunction = Restrictions.disjunction();
        PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);

        // 指定了员工字段
        if (FilterFieldType.EMPLOYEE.equals(filterFieldType)) {
            disjunction.add(Property.forName(fieldName).eq(SecurityContext.getUserId()));
            // 其他条件
            Disjunction newDisjunction = getDisjunction(hasPosition, hasOrg, hasParam, hasOrgAndChildren, otherOrgIds, otherParams);
            if (newDisjunction != null) {
                DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PositionEmp.class)
                        .setProjection(Projections.distinct(Projections.property("empId")));
                detachedCriteria.add(newDisjunction);
                disjunction.add(Property.forName(fieldName).in(detachedCriteria));
            }
        }

        // 指定了机构字段
        if (FilterFieldType.ORG.equals(filterFieldType)) {
            if (hasOrgAndChildren) {    // 查询组织机构及子组织机构
                disjunction.add(Property.forName(fieldName).in(positionEmpDao.findCurrentAndChildOrgIds(SecurityContext.getUserId())));
                hasOrgAndChildren = false;
            } else if (hasOrg) {
                disjunction.add(Property.forName(fieldName).in(positionEmpDao.findEmpOrgIds(SecurityContext.getUserId())));
                hasOrg = false;
            }

            if (otherOrgIds != null && !otherOrgIds.isEmpty()) {
                disjunction.add(Property.forName(fieldName).in(otherOrgIds));
                otherOrgIds = null;
            }

            // 其他条件
            Disjunction newDisjunction = getDisjunction(hasPosition, false, hasParam, false, null, otherParams);
            if (newDisjunction != null) {
                DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PositionEmp.class)
                        .setProjection(Projections.distinct(Projections.property("orgId")));
                detachedCriteria.add(newDisjunction);
                disjunction.add(Property.forName(fieldName).in(detachedCriteria));
            }
        }

        // 指定了岗位字段
        if (FilterFieldType.POSITION.equals(filterFieldType)) {
            if (hasPosition) {
                disjunction.add(Property.forName(fieldName).in(positionEmpDao.findEmpPositionIds(SecurityContext.getUserId())));
                hasPosition = false;
            }

            // 其他条件
            Disjunction newDisjunction = getDisjunction(false, hasOrg, hasParam, hasOrgAndChildren, otherOrgIds, otherParams);
            if (newDisjunction != null) {
                DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PositionEmp.class)
                        .setProjection(Projections.distinct(Projections.property("positionId")));
                detachedCriteria.add(newDisjunction);
                disjunction.add(Property.forName(fieldName).in(detachedCriteria));
            }
        }

        // 指定了业态字段
        if (FilterFieldType.PARAM.equals(filterFieldType)) {
            if (hasParam) {
                disjunction.add(Property.forName(fieldName).in(positionEmpDao.findEmpParams(SecurityContext.getUserId())));
                hasParam = false;
            }
            if (otherParams != null && !otherParams.isEmpty()) {
                disjunction.add(Property.forName(fieldName).in(otherParams));
                otherParams = null;
            }

            // 其他条件
            Disjunction newDisjunction = getDisjunction(hasPosition, hasOrg, false, hasOrgAndChildren, otherOrgIds, otherParams);
            if (newDisjunction != null) {
                DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PositionEmp.class)
                        .setProjection(Projections.distinct(Projections.property("orgId")));
                detachedCriteria.add(newDisjunction);
                disjunction.add(Property.forName(fieldName).in(detachedCriteria));
            }
        }
        criteria.add(disjunction);
    }

    /**
     * 根据参数，获得OR条件
     * 如果没有添加条件，则返回null
     *
     * @param hasPosition       是否有岗位权限
     * @param hasOrg            是否有组织机构权限
     * @param hasParam          是否有系统权限
     * @param hasOrgAndChildren 是否有组织机构及下级机构权限
     * @param otherOrgIds       指定的组织机构
     * @param otherParams       指定的系统
     */
    private Disjunction getDisjunction(boolean hasPosition, boolean hasOrg, boolean hasParam, boolean hasOrgAndChildren, Set<String> otherOrgIds, Set<String> otherParams) {

        PositionEmpDao positionEmpDao = SystemContainer.getInstance().getBean(PositionEmpDao.class);
        Disjunction disjunction = Restrictions.disjunction();
        boolean addNewCondition = false;
        if (hasOrgAndChildren) {    // 查询组织机构及子组织机构
            disjunction.add(Property.forName("orgId").in(positionEmpDao.findCurrentAndChildOrgIds(SecurityContext.getUserId())));
            addNewCondition = true;
        } else if (hasOrg) {         // 查询个人所属组织机构
            disjunction.add(Property.forName("orgId").in(positionEmpDao.findEmpOrgIds(SecurityContext.getUserId())));
            addNewCondition = true;
        }
        if (hasPosition) {          // 获得个人的岗位
            disjunction.add(Property.forName("positionId").in(positionEmpDao.findEmpPositionIds(SecurityContext.getUserId())));
            addNewCondition = true;
        }
        if (hasParam) {             // 获得个人系统
            disjunction.add(Property.forName("orgId").in(positionEmpDao.findEmpPramOrgIds(SecurityContext.getUserId())));
            addNewCondition = true;
        }
        if (otherOrgIds != null && !otherOrgIds.isEmpty()) {// 单独指定的组织机构
            addNewCondition = true;
            disjunction.add(Property.forName("orgId").in(otherOrgIds));
        }
        if (otherParams != null && !otherParams.isEmpty()) {// 单独指定的系统
            disjunction.add(Property.forName("orgId").in(positionEmpDao.findParamOrgIds(otherParams)));
            addNewCondition = true;
        }
        return addNewCondition ? disjunction : null;
    }

}
