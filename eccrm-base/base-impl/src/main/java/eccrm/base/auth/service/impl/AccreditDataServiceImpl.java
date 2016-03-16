package eccrm.base.auth.service.impl;

import com.ycrl.base.common.DomainHelper;
import com.ycrl.core.beans.BeanWrapBuilder;
import eccrm.base.auth.dao.AccreditDataDao;
import eccrm.base.auth.domain.AccreditData;
import eccrm.base.auth.service.AccreditDataService;
import eccrm.base.auth.vo.AccreditDataVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Michael
 */
@Service("accreditDataService")
public class AccreditDataServiceImpl implements AccreditDataService {
    @Resource
    private AccreditDataDao accreditDataDao;

    @Override
    public List<String> queryResourceCodeByPosition(String[] positionIds) {
        if (positionIds == null || positionIds.length < 1) {
            return null;
        }
        return accreditDataDao.queryResourceCodeByDept(positionIds);
    }

    @Override
    public void accreditToPosition(AccreditData accreditData) {
        // 有效性检查
        if (accreditData == null) {
            return;
        }
        String deptId = accreditData.getDeptId();
        if (StringUtils.isBlank(deptId)) {
            return;
        }
        // 删除原有关系
        accreditDataDao.deleteAccreditByDept(deptId, accreditData.getResourceId());

        // 保存新的关系
        DomainHelper.initAddInfo(accreditData);
        accreditDataDao.save(accreditData);
    }

    @Override
    public Set<String> queryPersonalAllDataResourceCode(String userId) {
        List<String> resourceCodes = accreditDataDao.queryResourceCodeByPerson(userId);
        if (resourceCodes == null || !resourceCodes.isEmpty()) {
            return null;
        }
        Set<String> codes = new HashSet<String>();
        codes.addAll(resourceCodes);
        return codes;
    }

    @Override
    public List<AccreditData> queryPersonalAllDataResource(String userId) {
        return accreditDataDao.queryPersonalAllDataResource(userId);
    }

    @Override
    public AccreditDataVo queryAccreditResource(String deptId, String resourceCode) {
        AccreditData accreditData = accreditDataDao.queryAccreditResource(deptId, resourceCode);
        if (accreditData == null) {
            return null;
        }
        return BeanWrapBuilder.newInstance()
                .wrap(accreditData, AccreditDataVo.class);
    }
}
