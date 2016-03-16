package eccrm.base.log.web;

import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import eccrm.base.log.bo.OperateLogBo;
import eccrm.base.log.service.OperateLogService;
import eccrm.base.log.vo.OperateLogVo;
import com.ycrl.utils.gson.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/base/operateLog"})
public class OperateLogCtrl extends BaseController {
    @Resource
    private OperateLogService operateLogService;
    @RequestMapping(value = {""}, method=RequestMethod.GET )
    public String toList() {
        return "base/operateLog/operateLog_list";
    }


    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        OperateLogVo vo = operateLogService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        OperateLogBo bo = GsonUtils.wrapDataToEntity(request, OperateLogBo.class);
        PageVo pageVo = operateLogService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }


}
