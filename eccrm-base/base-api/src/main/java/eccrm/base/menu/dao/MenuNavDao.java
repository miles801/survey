package eccrm.base.menu.dao;

import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.menu.bo.MenuNavBo;
import eccrm.base.menu.domain.MenuNav;

import java.util.List;

/**
 * @author Michael
 */
public interface MenuNavDao {

    @LogInfo(type = OperateType.ADD, describe = "九宫格-新增")
    String save(MenuNav menuNav);

    @LogInfo(type = OperateType.MODIFY, describe = "九宫格-修改")
    void update(MenuNav menuNav);

    List<MenuNav> query(MenuNavBo bo);

    long getTotal(MenuNavBo bo);

    MenuNav findById(String id);

    @LogInfo(type = OperateType.DELETE, describe = "九宫格-删除")
    void deleteById(String id);
}
