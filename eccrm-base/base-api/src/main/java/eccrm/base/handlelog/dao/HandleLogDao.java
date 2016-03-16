package eccrm.base.handlelog.dao;

import com.ycrl.core.pager.PageVo;
import eccrm.base.handlelog.bo.HandleLogBo;
import eccrm.base.handlelog.domain.HandleLog;
import eccrm.base.handlelog.vo.HandleLogVo;
import java.util.List;

/**
* @author wangsd
* @datetime 2014-04-21
*/
public interface HandleLogDao {

	String save(HandleLog handleLog);

    void update(HandleLog handleLog);

    List<HandleLog> query(HandleLogBo bo);

    long getTotal(HandleLogBo bo);

    HandleLog findById(String id);

    int deleteById(String id);

    /**
     * 根据报的id来查询对应的处理记录集合
     * @param reportId
     * @return
     */
	List<HandleLog> findByReportId(String reportId);
}
