package eccrm.base.main.web;

import com.ycrl.core.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用于九宫格的常用工具
 *
 * @author Michael
 */
@Controller
public class ToolCtrl extends BaseController {
    /**
     * 快捷方式
     */
    @RequestMapping(value = {"/tools/qs"}, method = RequestMethod.GET)
    public String toQs() {
        return "tools/qs/qs";
    }
}
