package eccrm.base.region.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.region.bo.RegionBo;
import eccrm.base.region.domain.Region;
import eccrm.base.region.vo.RegionVo;
import eccrm.core.VoWrapper;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-03-25
 */
public interface RegionService extends VoWrapper<Region, RegionVo> {

    String save(Region region);

    void update(Region region);

    PageVo query(RegionBo bo);

    RegionVo findById(String id);

    void deleteByIds(String... ids);

    List<RegionVo> tree(RegionBo bo);

    /**
     * 判断输入的区号是否正确(根据区号判断)
     *
     * @param code
     * @return 城市ID、城市名称
     */
    RegionVo queryByCode(String code);

    /**
     * 根据城市id获取所属省份信息（只返回id、名称）
     *
     * @param city 城市id
     */
    RegionVo getBelongProvence(String city);
}
