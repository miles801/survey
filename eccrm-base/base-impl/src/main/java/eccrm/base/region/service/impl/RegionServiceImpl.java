package eccrm.base.region.service.impl;

import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.exception.NullParamException;
import com.ycrl.core.pager.PageVo;
import eccrm.base.region.bo.RegionBo;
import eccrm.base.region.dao.RegionDao;
import eccrm.base.region.domain.Region;
import eccrm.base.region.service.RegionService;
import eccrm.base.region.vo.RegionVo;
import eccrm.core.VoHelper;
import eccrm.utils.tree.DynamicTreeBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: miles
 * @datetime: 2014-03-25
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService, BeanWrapCallback<Region, RegionVo> {
    @Resource
    private RegionDao regionDao;
    private DynamicTreeBuilder builder;

    private DynamicTreeBuilder getBuilder() {
        if (builder == null) {
            builder = new DynamicTreeBuilder(regionDao);
        }
        return builder;
    }

    @Override
    public String save(Region region) {
        if (region.getStatus() == null) {
            region.setStatus(CommonStatus.ACTIVE.getValue());
        }
        String id = regionDao.save(region);
        getBuilder().save(region);
        return id;
    }

    @Override
    public void update(Region region) {
        if (region.getParent() != null && region.getParent().getId() == null) region.setParent(null);
        getBuilder().update(region);
        regionDao.update(region);
    }

    @Override
    public PageVo query(RegionBo bo) {
        PageVo vo = new PageVo();
        Long total = regionDao.getTotal(bo);
        if (total == null || total == 0) return vo;
        vo.setTotal(total);
        List<Region> regions = regionDao.query(bo);
        vo.setData(VoHelper.wrapVos(regions, this));
        return vo;
    }

    @Override
    public RegionVo findById(String id) {
        return wrap(regionDao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            getBuilder().delete(id);
            regionDao.deleteById(id);
        }
    }

    @Override
    public List<RegionVo> tree(RegionBo bo) {
        //查询数据
        List<Region> regions = regionDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "type"})
                .setCallback(this)
                .wrapList(regions, RegionVo.class);
    }

    @Override
    public RegionVo queryByCode(String code) {
        RegionVo vo = new RegionVo();
        if (StringUtils.isBlank(code)) {
            throw new NullParamException("区号不能为空!");
        }
        RegionBo regionBo = new RegionBo();
        regionBo.setCode(code);
        List<Region> regions = regionDao.query(regionBo);
        if (regions != null && regions.size() > 0) {
            Region region = regions.get(0);
            vo.setId(region.getId());
            vo.setName(region.getName());
        }
        return vo;
    }

    @Override
    public RegionVo wrap(Region region) {
        if (region == null) return null;
        RegionVo vo = new RegionVo();
        BeanUtils.copyProperties(region, vo);
        vo.setIsParent(!region.getLeaf());
        Region parent = region.getParent();
        if (parent != null) {
            vo.setParentId(parent.getId());
            vo.setParentName(parent.getName());
        }

        return vo;
    }

    @Override
    public void doCallback(Region region, RegionVo vo) {
        vo.setIsParent(!region.getLeaf());
        Region parent = region.getParent();
        if (parent != null) {
            vo.setParentId(parent.getId());
            vo.setParentName(parent.getName());
        }
    }

    @Override
    public RegionVo getBelongProvence(String city) {
        Region region = regionDao.getBelongProvence(city);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name"})
                .wrap(region, RegionVo.class);
    }
}
