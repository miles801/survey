package eccrm.base.log.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.pager.PageVo;
import eccrm.base.log.bo.OperateLogBo;
import eccrm.base.log.dao.OperateLogDao;
import eccrm.base.log.domain.OperateLog;
import eccrm.base.log.service.OperateLogService;
import eccrm.base.log.vo.OperateLogVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("operateLogService")
public class OperateLogServiceImpl implements OperateLogService {
    @Resource
    private OperateLogDao operateLogDao;


    @Override
    public PageVo pageQuery(OperateLogBo bo) {
        PageVo vo = new PageVo();
        Long total = operateLogDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<OperateLog> operateLogList = operateLogDao.query(bo);
        List<OperateLogVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(operateLogList, OperateLogVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public OperateLogVo findById(String id) {
        OperateLog operateLog = operateLogDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(operateLog, OperateLogVo.class);
    }


}
