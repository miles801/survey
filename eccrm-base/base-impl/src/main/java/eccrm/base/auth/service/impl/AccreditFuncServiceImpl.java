package eccrm.base.auth.service.impl;

import com.ycrl.base.common.DomainHelper;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.auth.dao.AccreditFuncDao;
import eccrm.base.auth.domain.AccreditFunc;
import eccrm.base.auth.service.AccreditFuncService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("accreditFuncService")
public class AccreditFuncServiceImpl implements AccreditFuncService {
    @Resource
    private AccreditFuncDao accreditFuncDao;

    @Override
    public void accreditToPosition(String positionId, List<AccreditFunc> accreditFuncs) {
        // 验证合法性
        if (StringUtils.isEmpty(positionId)) return;

        // 删除原有关系
        accreditFuncDao.deleteByDeptId(positionId);

        // 创建新的关系
        if (accreditFuncs != null && !accreditFuncs.isEmpty()) {
            for (AccreditFunc accreditFunc : accreditFuncs) {
                DomainHelper.initAddInfo(accreditFunc);
                accreditFunc.setDeptId(positionId);
                accreditFuncDao.save(accreditFunc);
            }
        }
    }

    @Override
    public List<String> queryResourceCodeByPosition(String[] positionIds) {
        return accreditFuncDao.queryResourceCodeByDept(positionIds);
    }

    @Override
    public List<String> queryPersonalResourceCode() {
        return accreditFuncDao.queryEmpResourceCode(SecurityContext.getUserId());
    }
}
