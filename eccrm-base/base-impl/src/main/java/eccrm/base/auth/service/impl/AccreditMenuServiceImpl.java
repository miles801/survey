package eccrm.base.auth.service.impl;

import com.ycrl.base.common.DomainHelper;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.exception.Argument;
import com.ycrl.utils.tree.TreeFactory;
import eccrm.base.auth.dao.AccreditMenuDao;
import eccrm.base.auth.domain.AccreditMenu;
import eccrm.base.auth.service.AccreditMenuService;
import eccrm.base.menu.domain.Menu;
import eccrm.base.menu.vo.MenuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael
 */
@Service("accreditMenuService")
public class AccreditMenuServiceImpl implements AccreditMenuService, BeanWrapCallback<Menu, MenuVo> {
    @Resource
    private AccreditMenuDao accreditMenuDao;


    @Override
    public List<String> queryAccreditMenuIds(String[] deptIds) {
        if (deptIds == null || deptIds.length < 1) {
            return new ArrayList<String>();
        }

        return accreditMenuDao.queryMenuIds(deptIds);
    }

    @Override
    public void saveByDept(String deptId, String[] menuIds) {
        if (StringUtils.isEmpty(deptId)) {
            throw new RuntimeException("给岗位授权时，机构ID不能为空!");
        }
        // 删掉历史岗位的授权信息
        accreditMenuDao.deleteByDeptId(deptId);

        // 保存新的授权关系
        if (menuIds != null && menuIds.length > 0) {
            for (String menuId : menuIds) {
                AccreditMenu accredit = new AccreditMenu();
                DomainHelper.initAddInfo(accredit);
                accredit.setResourceId(menuId);
                accredit.setDeptId(deptId);
                accreditMenuDao.save(accredit);
            }
        }
    }


    @Override
    public void doCallback(Menu menu, MenuVo vo) {
        Menu parent = menu.getParent();
        if (parent != null) {
            vo.setParentId(parent.getId());
            vo.setParentName(parent.getName());
        }
        // 设置资源url
        vo.setUrl(menu.getResource().getUrl());
    }

    @Override
    public List<MenuVo> queryAccreditMenus(String[] deptIds) {
        if (deptIds == null || deptIds.length < 1) {
            return new ArrayList<MenuVo>();
        }
        List<Menu> menus = accreditMenuDao.queryMenus(deptIds);
        if (menus == null || menus.isEmpty()) {
            return new ArrayList<MenuVo>();
        }
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "icon", "code", "type"})
                .setCallback(this)
                .wrapList(menus, MenuVo.class);
    }

    @Override
    public List<MenuVo> queryPersonalAccreditMenus(String empId) {
        Argument.isEmpty(empId, "查询个人权限时，没有获得用户ID！");
        List<Menu> menus = accreditMenuDao.queryMenus(empId);
        if (menus == null || menus.isEmpty()) {
            return null;
        }
        List<Menu> tree = TreeFactory.buildTree(menus, Menu.class);

        return wrapToAccreditMenuVo(tree);
    }

    /**
     * 将Menu集合转换成MenuVo集合，只转换id、name、icon、code、type等属性
     *
     * @param tree
     * @return
     */
    private List<MenuVo> wrapToAccreditMenuVo(List<Menu> tree) {
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "icon", "code", "type","fullScreen"})
                .setCallback(new BeanWrapCallback<Menu, MenuVo>() {
                    @Override
                    public void doCallback(Menu menu, MenuVo vo) {
                        // FIXME 性能待优化
                        if (menu.getResource() != null) {
                            vo.setUrl(menu.getResource().getUrl());
                        }
                        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                            vo.setChildren(
                                    BeanWrapBuilder.newInstance()
                                            .setCallback(this)
                                            .wrapList(menu.getChildren(), MenuVo.class)
                            );
                        }
                    }
                })
                .wrapList(tree, MenuVo.class);
    }

    @Override
    public List<MenuVo> queryPersonalAccreditMenus(String userId, String parentId) {
        List<Menu> menus = accreditMenuDao.queryAccreditMenus(userId, parentId);
        if (menus == null || menus.isEmpty()) {
            return null;
        }
        List<Menu> tree = TreeFactory.buildTree(menus, Menu.class, parentId);
        return wrapToAccreditMenuVo(tree);
    }

    @Override
    public List<MenuVo> queryPersonalAccreditMenusRoot(String userId) {
        List<Menu> menus = accreditMenuDao.queryAccreditMenuRoot(userId);
        if (menus == null || menus.isEmpty()) {
            return null;
        }
        return wrapToAccreditMenuVo(menus);
    }
}
