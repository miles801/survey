package eccrm.base.auth.web;

import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.auth.domain.AccreditData;
import eccrm.base.auth.service.AccreditDataService;
import eccrm.base.auth.vo.AccreditDataVo;
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
@RequestMapping(value = {"/auth/accreditData"})
public class AccreditDataCtrl extends BaseController {
    @Resource
    private AccreditDataService accreditDataService;

    /**
     * 给岗位/部门进行授权
     */
    @RequestMapping(value = "/accreditToDept", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        AccreditData accreditData = GsonUtils.wrapDataToEntity(request, AccreditData.class);
        accreditDataService.accreditToPosition(accreditData);
        GsonUtils.printSuccess(response);
    }

    /**
     * 查询指定岗位被授权的所有资源的编号
     */
    @ResponseBody
    @RequestMapping(value = "/queryResourceCodeByDept", params = {"deptId"}, method = RequestMethod.GET)
    public void queryByDept(String deptId, HttpServletResponse response) {
        List<String> resources = accreditDataService.queryResourceCodeByPosition(new String[]{deptId});
        GsonUtils.printData(response, resources);
    }

    @ResponseBody
    @RequestMapping(value = "/queryAccreditResource", params = {"deptId", "resourceCode"}, method = RequestMethod.GET)
    public void queryAccreditResource(
            @RequestParam String deptId,
            @RequestParam String resourceCode,
            HttpServletResponse response) {
        AccreditDataVo accreditData = accreditDataService.queryAccreditResource(deptId, resourceCode);
        GsonUtils.printData(response, accreditData);
    }
}
