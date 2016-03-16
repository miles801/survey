package eccrm.base.menu.service.impl;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.pager.PageVo;
import eccrm.base.menu.bo.MenuNavBo;
import eccrm.base.menu.dao.MenuNavDao;
import eccrm.base.menu.dao.ResourceDao;
import eccrm.base.menu.domain.MenuNav;
import eccrm.base.menu.service.MenuNavService;
import eccrm.base.menu.vo.MenuNavVo;
import eccrm.base.parameter.service.ParameterContainer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("menuNavService")
public class MenuNavServiceImpl implements MenuNavService, BeanWrapCallback<MenuNav, MenuNavVo> {
    @Resource
    private MenuNavDao menuNavDao;

    @Override
    public String save(MenuNav menuNav) {
        String id = menuNavDao.save(menuNav);
        return id;
    }

    @Override
    public void update(MenuNav menuNav) {
        menuNavDao.update(menuNav);
    }

    @Override
    public PageVo query(MenuNavBo bo) {
        PageVo vo = new PageVo();
        Long total = menuNavDao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<MenuNav> menuNavList = menuNavDao.query(bo);
        List<MenuNavVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(menuNavList, MenuNavVo.class);
        vo.setData(vos);
        return vo;
    }

    @Override
    public MenuNavVo findById(String id) {
        MenuNav menuNav = menuNavDao.findById(id);

        return BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<MenuNav, MenuNavVo>() {
                    @Override
                    public void doCallback(MenuNav o, MenuNavVo vo) {
                        // 设置上级
                        setParentInfo(o, vo);
                        // 设置所属资源
                        setResourceInfo(o, vo);
                    }
                })
                .wrap(menuNav, MenuNavVo.class);
    }


    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            menuNavDao.deleteById(id);
        }
    }

    @Override
    public List<MenuNavVo> queryValid() {
        MenuNavBo bo = new MenuNavBo();
        bo.setStatus("ACTIVE");

        List<MenuNav> vos = menuNavDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "name", "showName", "sequenceNo", "type", "cols", "rows", "showMore"})
                .setCallback(new BeanWrapCallback<MenuNav, MenuNavVo>() {
                    @Override
                    public void doCallback(MenuNav o, MenuNavVo vo) {
                        // 设置资源URL
                        setResourceInfo(o, vo);
                        // 设置上级
                        setParentInfo(o, vo);
                    }
                })
                .wrapList(vos, MenuNavVo.class);
    }

    /**
     * 从实体中取出资源id，并查询出资源信息设置给vo
     */
    private void setResourceInfo(MenuNav o, MenuNavVo vo) {
        if (StringUtils.isNotEmpty(o.getResourceId())) {
            ResourceDao resourceDao = SystemContainer.getInstance().getBean(ResourceDao.class);
            eccrm.base.menu.domain.Resource resource = resourceDao.findById(o.getResourceId());
            if (resource != null) {
                vo.setResourceName(resource.getName());
                vo.setResourceUrl(resource.getUrl());
            }
        }
    }

    /**
     * 从实体中取出上级信息，赋值给vo
     */
    private void setParentInfo(MenuNav o, MenuNavVo vo) {
        if (o.getParent() != null) {
            vo.setParentId(o.getParent().getId());
            vo.setParentName(o.getParent().getName());
        }
    }

    @Override
    public void doCallback(MenuNav menuNav, MenuNavVo vo) {
        // 设置上级
        setParentInfo(menuNav, vo);
        // 设置资源信息
        setResourceInfo(menuNav, vo);
        // 设置类型
        ParameterContainer parameterContainer = ParameterContainer.getInstance();
        vo.setTypeName(parameterContainer.getSystemName(MenuNav.TYPE, menuNav.getType()));
        // 设置状态
        vo.setStatusName(parameterContainer.getSystemName(MenuNav.STATUS, menuNav.getStatus()));
    }
}
