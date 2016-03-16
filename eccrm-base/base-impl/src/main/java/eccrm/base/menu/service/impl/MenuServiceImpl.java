package eccrm.base.menu.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.tree.PathTreeBuilder;
import com.ycrl.utils.tree.TreeFactory;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.constant.ParameterConstant;
import eccrm.base.menu.bo.MenuBo;
import eccrm.base.menu.dao.MenuDao;
import eccrm.base.menu.domain.Menu;
import eccrm.base.menu.service.MenuService;
import eccrm.base.menu.vo.MenuVo;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.core.exception.NullParamException;
import eccrm.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service("menuService")
public class MenuServiceImpl implements MenuService, BeanWrapCallback<Menu, MenuVo> {

    private Logger logger = Logger.getLogger(MenuServiceImpl.class);

    @Resource
    private MenuDao menuDao;


    @Override
    public String save(Menu entity) {
        entity.setPath("/");//设置一个默认的path
        if (entity.getParent() != null && StringUtils.isEmpty(entity.getParent().getId())) {
            entity.setParent(null);
        }
        if (StringUtils.isEmpty(entity.getStatus())) {
            entity.setStatus(CommonStatus.INACTIVE.getValue());
        }
        String id = menuDao.save(entity);
        PathTreeBuilder.buildAfterSave(menuDao, entity);
        return id;
    }

    @Override
    public MenuVo findById(String entityId) {
        if (entityId == null) {
            throw new NullParamException("id");
        }
        Menu menu = menuDao.findById(entityId);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrap(menu, MenuVo.class);
    }


    @Override
    public void update(Menu entity) {
        PathTreeBuilder.buildBeforeUpdate(menuDao, entity);
        if (entity.getParent() != null && StringUtils.isEmpty(entity.getParent().getId())) {
            entity.setParent(null);
        }
        //更新
        menuDao.update(entity);
    }

    @Override
    public void delete(String... ids) {
        if (ids == null || ids.length < 1) return;
        for (String id : ids) {
            Menu menu = menuDao.findById(id);
            if (menu == null) {
                throw new EntityNotFoundException(id);
            }
            if (CommonStatus.INACTIVE.getValue().equals(menu.getStatus())) {
                //如果状态为未启用，则直接删除（同时删除子节点）；否则修改状态
                List<Menu> menus = menuDao.queryChildren(id);
                for (Menu foo : menus) {
                    menuDao.delete(foo);
                }
                menuDao.delete(menu);
                logger.debug(SecurityContext.getUsername() + "删除菜单[" + menu.getName() + "]以及其所有子菜单!");
            } else {
                // 如果注销当前节点，则同时注销所有子节点
                menu.setStatus(CommonStatus.CANCELED.getValue());
                menuDao.batchUpdateChildrenStatus(id, CommonStatus.CANCELED.getValue());
            }
        }
    }


    @Override
    public List<MenuVo> tree(MenuBo bo) {
        List<Menu> menus = menuDao.query(bo);
        List<Menu> tree = TreeFactory.buildTree(menus, Menu.class);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(tree, MenuVo.class);
    }


    @Override
    public PageVo query(MenuBo bo) {
        PageVo vo = new PageVo();
        long total = menuDao.getTotal(bo);
        vo.setTotal(total);
        if (total < 1) return vo;
        List<Menu> menus = menuDao.query(bo);
        vo.setData(BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(menus, MenuVo.class));
        return vo;
    }

    @Override
    public List<MenuVo> queryOtherTree(String id) {
        List<Menu> menus = menuDao.queryOther(id);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "parent"})
                .setCallback(this)
                .wrapList(menus, MenuVo.class);
    }

    @Override
    public boolean hasName(String id, String name) {
        return menuDao.hasName(id, name);
    }

    @Override
    public boolean hasCode(String code) {
        return menuDao.hasCode(code);
    }

    @Override
    public PageVo queryAllChildren(String id) {
        PageVo vo = new PageVo();
        MenuBo bo = new MenuBo();
        bo.setPath("/" + id + "/");
        long total = menuDao.getTotal(bo);
        vo.setTotal(total);
        if (total < 1) {
            return vo;
        }
        List<Menu> children = menuDao.query(bo);
        List<MenuVo> data = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(children, MenuVo.class);
        vo.setData(data);
        return vo;
    }

    @Override
    public List<MenuVo> queryValid() {
        MenuBo bo = new MenuBo();
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        bo.setShow(true);
        List<Menu> menus = menuDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "parentId"})
                .wrapList(menus, MenuVo.class);
    }

    @Override
    public List<MenuVo> queryValidMenu() {
        List<Menu> menus = menuDao.queryValidMenu();
        List<Menu> tree = TreeFactory.buildTree(menus, Menu.class);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name"})
                .setCallback(new BeanWrapCallback<Menu, MenuVo>() {
                    @Override
                    public void doCallback(Menu o, MenuVo vo) {
                        if (o.getParent() != null) {
                            vo.setParentId(o.getParent().getId());
                        }
                    }
                })
                .wrapList(tree, MenuVo.class);
    }

    @Override
    public List<String> queryPermissionMenus() {
        return null;
    }

    @Override
    public void doCallback(Menu menu, MenuVo vo) {
        if (menu.getParent() != null) {
            vo.setParentId(menu.getParent().getId());
            vo.setParentName(menu.getParent().getName());
        }
        ParameterContainer container = ParameterContainer.getInstance();
        vo.setTypeName(container.getSystemName(Menu.PARAM_TYPE, menu.getType()));
        vo.setStatusName(container.getSystemName(ParameterConstant.COMMON_STATE, menu.getStatus()));
        eccrm.base.menu.domain.Resource resource = menu.getResource();
        if (resource != null) {
            vo.setUrl(resource.getUrl());
            vo.setResourceId(resource.getId());
            vo.setResourceName(resource.getName());
        }
        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            vo.setChildren(
                    BeanWrapBuilder.newInstance()
                            .setCallback(this)
                            .wrapList(menu.getChildren(), MenuVo.class)
            );
        }
    }
}
