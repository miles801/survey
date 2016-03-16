package eccrm.base.menu.web;

import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import eccrm.base.menu.domain.MenuNav;
import eccrm.base.menu.service.MenuNavService;
import eccrm.base.menu.vo.MenuNavVo;
import com.ycrl.utils.gson.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/base/nav"})
public class MenuNavCtrl extends BaseController {
    @Resource
    private MenuNavService menuNavService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/resource/nav/nav_list";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String toDashboard() {
        return "base/resource/nav/nav_home";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        MenuNav menuNav = GsonUtils.wrapDataToEntity(request, MenuNav.class);
        menuNavService.save(menuNav);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        MenuNav menuNav = GsonUtils.wrapDataToEntity(request, MenuNav.class);
        menuNavService.update(menuNav);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        MenuNavVo vo = menuNavService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public void query(HttpServletResponse response) {
        PageVo pageVo = menuNavService.query(null);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/queryValid", method = RequestMethod.GET)
    public void queryValid(HttpServletResponse response) {
        List<MenuNavVo> vos = menuNavService.queryValid();
        GsonUtils.printData(response, vos);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        menuNavService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

}
