package eccrm.base.log.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.log.bo.OperateLogBo;
import eccrm.base.log.vo.OperateLogVo;

/**
 * @author Michael
 * 
 */
public interface OperateLogService {



    /**
    * 分页查询
    */
    PageVo pageQuery(OperateLogBo bo);

    /**
    * 根据ID查询对象的信息
    */
    OperateLogVo findById(String id);


}
