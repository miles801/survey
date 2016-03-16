package eccrm.base.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 欢迎页
 * Created by Michael on 2014/9/18.
 */
@Controller
public class IndexCtrl {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String toIndex() {
        return "redirect:/index.html";
    }
}
