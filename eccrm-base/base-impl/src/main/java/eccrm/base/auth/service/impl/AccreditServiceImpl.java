package eccrm.base.auth.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.pager.PageVo;
import eccrm.base.auth.bo.AccreditBo;
import eccrm.base.auth.dao.AccreditDao;
import eccrm.base.auth.domain.Accredit;
import eccrm.base.auth.service.AccreditService;
import eccrm.base.auth.vo.AccreditVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("accreditService")
public class AccreditServiceImpl implements AccreditService, BeanWrapCallback<Accredit, AccreditVo> {
    @Resource
    private AccreditDao accreditDao;

    @Override
    public String save(Accredit accredit) {
        String id = accreditDao.save(accredit);
        return id;
    }

    @Override
    public void update(Accredit accredit) {
        accreditDao.update(accredit);
    }

    @Override
    public PageVo query(AccreditBo bo) {
        PageVo vo = new PageVo();
        Long total = accreditDao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<Accredit> accreditList = accreditDao.query(bo);
        List<AccreditVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(accreditList, AccreditVo.class);
        vo.setData(vos);
        return vo;
    }

    @Override
    public AccreditVo findById(String id) {
        Accredit accredit = accreditDao.findById(id);

        return BeanWrapBuilder.newInstance()
                .wrap(accredit, AccreditVo.class);
    }


    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            accreditDao.deleteById(id);
        }
    }


    @Override
    public void doCallback(Accredit accredit, AccreditVo vo) {

    }
}
