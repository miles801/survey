package eccrm.base.menu.web;

/**
 * Created by miles on 13-11-26.
 * Menu对应的jsp页面的访问路径
 */
public interface MenuPage {
    /**
     * Menu编辑页面访问地址（更新和新建在同一个页面）
     */
    String EDIT = "base/resource/menu/menu_edit";
    /**
     * Menu列表页面访问地址
     */
    String LIST = "base/resource/menu/menu_list";
}
