package eccrm.base.parameter.dao;

import eccrm.base.parameter.bo.BusinessParamTypeBo;
import eccrm.base.parameter.domain.BusinessParamType;
import eccrm.utils.tree.PathTreeDao;

/**
 * @author miles
 * @datetime 2014-07-02
 */
public interface BusinessParamTypeDao extends PathTreeDao<BusinessParamType, String>, ParameterTypeDao<BusinessParamType, BusinessParamTypeBo> {

}
