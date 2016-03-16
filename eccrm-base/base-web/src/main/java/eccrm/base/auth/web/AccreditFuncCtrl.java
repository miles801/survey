package eccrm.base.auth.web;

import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.auth.domain.AccreditFunc;
import eccrm.base.auth.service.AccreditFuncService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/auth/accreditFunc"})
public class AccreditFuncCtrl extends BaseController {
    @Resource
    private AccreditFuncService accreditFuncService;


    /**
     * 给岗位/部门进行授权
     *
     * @param deptId 岗位id
     */
    @RequestMapping(value = "/accreditToDept", params = {"deptId"}, method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public void save(@RequestParam String deptId,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        AccreditFunTemp temp = GsonUtils.wrapDataToEntity(request, AccreditFunTemp.class);
        accreditFuncService.accreditToPosition(deptId, temp.getResources());
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/queryResourceCodeByDept", params = {"deptId"}, method = RequestMethod.GET)
    public void queryByDept(String deptId, HttpServletResponse response) {
        List<String> resourceIds = accreditFuncService.queryResourceCodeByPosition(new String[]{deptId});
        GsonUtils.printData(response, resourceIds);
    }

    /**
     * 从session中取出当前用户缓存的权限集合，判断给定的权限编号是否存在，如果存在则表示有权限，不存在则没有权限
     * 响应结果：
     * true:有权限 false:无权限
     *
     * @param code 资源编号
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/hasPermission", params = {"code"}, method = RequestMethod.GET)
    public void hasPermission(@RequestParam final String code,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        Map<String, Boolean> resources = (Map<String, Boolean>) request.getSession().getAttribute(AccreditFunc.ACCREDIT_FUNCTION_CODE);
        if (resources != null && resources.containsKey(code)) {
            GsonUtils.printData(response, true);
        } else {
            GsonUtils.printData(response, false);
        }
    }
}
