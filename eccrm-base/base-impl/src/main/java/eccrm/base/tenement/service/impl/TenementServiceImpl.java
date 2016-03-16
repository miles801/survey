package eccrm.base.tenement.service.impl;

import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import eccrm.base.region.dao.RegionDao;
import eccrm.base.tenement.bo.TenementBo;
import eccrm.base.tenement.dao.TenementDao;
import eccrm.base.tenement.domain.Tenement;
import eccrm.base.tenement.domain.TenementStatus;
import eccrm.base.tenement.service.TenementService;
import eccrm.base.tenement.vo.TenementVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: miles
 * @datetime: 2014-03-14
 */
@Service("tenementService")
public class TenementServiceImpl implements TenementService {
    @Resource
    private TenementDao tenementDao;
    @Resource
    private RegionDao regionDao;

    @Override
    public String save(Tenement tenement) {
        if (tenement.getStatus() == null) {
            tenement.setStatus(TenementStatus.COLLECTING.getValue());
        }
        tenement.setCreatorId(SecurityContext.getUserId());
        tenement.setCreatorName(SecurityContext.getUsername());
        tenement.setCreatedDatetime(new Date());
        return tenementDao.save(tenement);
    }

    @Override
    public void update(Tenement tenement) {
        tenementDao.update(tenement);
    }

    @Override
    public PageVo query(TenementBo bo) {
        PageVo vo = new PageVo();
        Long total = tenementDao.getTotal(bo);
        if (total == null || total == 0) return vo;
        vo.setTotal(total);
        List<Tenement> tenements = tenementDao.query(bo);
        vo.setData(wrapVos(tenements));
        return vo;
    }

    @Override
    public TenementVo findById(String id) {
        return wrapVo(tenementDao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            if ("".equals(id.trim())) continue;
            Tenement tenement = tenementDao.findById(id);
            if (tenement == null) {
                throw new EntityNotFoundException("ID为[" + id + "]的租户不存在或者已经被删除，请刷新后重试!");
            }
            //无状态、采集中的租户是可以被删掉的
            Integer status = tenement.getStatus();
            if (status == null || status.equals(TenementStatus.COLLECTING.getValue())) {
                tenementDao.deleteById(id);
                continue;
            }
            //正常状态-->已注销
            if (status.equals(TenementStatus.NORMAL.getValue())) {
                tenement.setStatus(TenementStatus.CANCELED.getValue());
            }
        }
    }

    @Override
    public void close(String id) {
        if (id == null) {
            throw new IllegalArgumentException("For closing tenement,id must not be null!");
        }
        Tenement tenement = tenementDao.findById(id);
        if (tenement == null) {
            throw new EntityNotFoundException("ID为[" + id + "]的租户不存在或者已经被删除，请刷新后重试!");
        }
        tenement.setStatus(TenementStatus.CLOSED.getValue());
    }

    @Override
    public void pause(String id) {
        if (id == null) {
            throw new IllegalArgumentException("For pausing tenement,id must not be null!");
        }
        Tenement tenement = tenementDao.findById(id);
        if (tenement == null) {
            throw new EntityNotFoundException("ID为[" + id + "]的租户不存在或者已经被删除，请刷新后重试!");
        }
        tenement.setStatus(TenementStatus.PAUSE.getValue());
    }

    private TenementVo wrapVo(Tenement tenement) {
        if (tenement == null) return null;
        TenementVo vo = new TenementVo();
        BeanUtils.copyProperties(tenement, vo);
        //设置区域名称
        String province = tenement.getProvince();
        if (province != null) {
            vo.setProvinceName(regionDao.getName(province));
        }
        String city = tenement.getCity();
        if (city != null) {
            vo.setCityName(regionDao.getName(city));
        }
        String county = tenement.getCounty();
        if (county != null) {
            vo.setCountyName(regionDao.getName(county));
        }
        return vo;
    }

    private List<TenementVo> wrapVos(List<Tenement> tenements) {
        List<TenementVo> vos = new ArrayList<TenementVo>();
        if (tenements == null) return vos;
        for (Tenement tenement : tenements) {
            TenementVo foo = wrapVo(tenement);
            if (foo == null) continue;
            vos.add(foo);
        }
        return vos;
    }
}
