package eccrm.base.user.web;

import eccrm.base.user.domain.PasswordPolicy;
import eccrm.base.user.service.PasswordPolicyService;
import eccrm.base.user.vo.PasswordPolicyVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author miles
 * @datetime 2014-03-17
 */

@Controller
@RequestMapping(value = {"/base/user/passwordpolicy"})
public class PasswordPolicyCtrl extends BaseController {
    @Resource
    private PasswordPolicyService passwordPolicyService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "base/user/passwordpolicy";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response) {
        PasswordPolicy passwordPolicy = GsonUtils.wrapDataToEntity(request, PasswordPolicy.class);
        String id = passwordPolicyService.save(passwordPolicy);
        GsonUtils.printJson(response, "id", id);
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response) {
        PasswordPolicy passwordPolicy = GsonUtils.wrapDataToEntity(request, PasswordPolicy.class);
        passwordPolicyService.update(passwordPolicy);
        GsonUtils.printSuccess(response);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(HttpServletResponse response) {
        PasswordPolicyVo vo = passwordPolicyService.get();
        if (vo == null) {
            vo = new PasswordPolicyVo();
        }
        GsonUtils.printJsonObject(response, vo);
        return null;
    }

}
