package eccrm.base.parameter.dao;

import eccrm.base.parameter.bo.SysParamTypeBo;
import eccrm.base.parameter.domain.SysParamType;
import eccrm.utils.tree.PathTreeDao;

/**
 * @author miles
 * @datetime 2014-06-20
 */
public interface SysParamTypeDao extends PathTreeDao<SysParamType, String>, ParameterTypeDao<SysParamType, SysParamTypeBo> {

}
