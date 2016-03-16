package eccrm.base.handlelog.service;

import java.util.List;

import com.ycrl.core.pager.PageVo;
import eccrm.base.handlelog.bo.HandleLogBo;
import eccrm.base.handlelog.domain.HandleLog;
import eccrm.base.handlelog.vo.HandleLogVo;
/**
* @author wangsd
* @datetime 2014-04-21
*/
public interface HandleLogService {

	String save(HandleLog handleLog);

    void update(HandleLog handleLog);

    PageVo query(HandleLogBo bo);

    HandleLogVo findById(String id);

    void deleteByIds(String... ids);
    
    /**
     * 根据报的id来查询对应的处理记录集合
     * @param reportId
     * @return
     */
    List<HandleLog> findByReportId(String reportId);

}
