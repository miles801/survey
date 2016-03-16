package eccrm.base.menu.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.menu.bo.MenuNavBo;
import eccrm.base.menu.domain.MenuNav;
import eccrm.base.menu.vo.MenuNavVo;

import java.util.List;

/**
 * @author Michael
 */
public interface MenuNavService {

    String save(MenuNav menuNav);

    void update(MenuNav menuNav);

    PageVo query(MenuNavBo bo);

    MenuNavVo findById(String id);

    void deleteByIds(String[] ids);

    /**
     * 查询所有处于启用状态的菜单导航
     *
     * @return 菜单导航集合
     */
    List<MenuNavVo> queryValid();
}
