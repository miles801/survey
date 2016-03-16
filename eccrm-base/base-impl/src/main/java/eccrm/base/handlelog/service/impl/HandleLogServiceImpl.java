package eccrm.base.handlelog.service.impl;

import eccrm.base.handlelog.bo.HandleLogBo;
import eccrm.base.handlelog.dao.HandleLogDao;
import eccrm.base.handlelog.domain.HandleLog;
import eccrm.base.handlelog.service.HandleLogService;
import eccrm.base.handlelog.vo.HandleLogVo;
import com.ycrl.core.pager.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangsd
 * @datetime: 2014-04-16
 */
@Service("handleLogService")
public class HandleLogServiceImpl implements HandleLogService {
    @Resource
    private HandleLogDao handleLogDao;

    @Override
    public String save(HandleLog handleLog) {
        String id = handleLogDao.save(handleLog);
        return id;
    }

    @Override
    public void update(HandleLog handleLog) {
        handleLogDao.update(handleLog);
    }

    @Override
    public PageVo query(HandleLogBo bo) {
        PageVo vo = new PageVo();
        Long total = handleLogDao.getTotal(bo);
        if (total == 0) return vo;
        vo.setTotal(total);
        List<HandleLog> handleLogs = handleLogDao.query(bo);
        vo.setData(wrapVos(handleLogs));
        return vo;
    }

    @Override
    public HandleLogVo findById(String id) {
        return wrapVo(handleLogDao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            handleLogDao.deleteById(id);
        }
    }


    private HandleLogVo wrapVo(HandleLog handleLog) {
        if (handleLog == null) return null;
        HandleLogVo vo = new HandleLogVo();
        BeanUtils.copyProperties(handleLog, vo);
        return vo;
    }

    private List<HandleLogVo> wrapVos(List<HandleLog> handleLogs) {
        List<HandleLogVo> vos = new ArrayList<HandleLogVo>();
        if (handleLogs == null) return vos;
        for (HandleLog handleLog : handleLogs) {
            HandleLogVo foo = wrapVo(handleLog);
            if (foo == null) continue;
            vos.add(foo);
        }
        return vos;
    }

    @Override
    public List<HandleLog> findByReportId(String reportId) {
        if (reportId == null) {
            throw new IllegalArgumentException("报的id不能为空");
        }
        List<HandleLog> handleLogs = handleLogDao.findByReportId(reportId);
        return handleLogs;
    }
}
