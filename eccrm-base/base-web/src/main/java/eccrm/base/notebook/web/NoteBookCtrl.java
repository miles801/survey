package eccrm.base.notebook.web;

import com.ycrl.core.pager.PageVo;
import eccrm.base.notebook.bo.NoteBookBo;
import eccrm.base.notebook.domain.NoteBook;
import eccrm.base.notebook.service.NoteBookService;
import eccrm.base.notebook.vo.NoteBookVo;
import com.ycrl.core.web.BaseController;
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
 * 便签
 */
@Controller
@RequestMapping(value = {"/tools/note"})
public class NoteBookCtrl extends BaseController {
    @Resource
    private NoteBookService noteBookService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String toList() {
        return "tools/note/note";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        NoteBook noteBook = GsonUtils.wrapDataToEntity(request, NoteBook.class);
        String id = noteBookService.save(noteBook);
        GsonUtils.printData(response, id);
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        NoteBook noteBook = GsonUtils.wrapDataToEntity(request, NoteBook.class);
        noteBookService.update(noteBook);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        NoteBookVo vo = noteBookService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/query")
    public void query(HttpServletRequest request, HttpServletResponse response) {
        NoteBookBo bo = GsonUtils.wrapDataToEntity(request, NoteBookBo.class);
        PageVo pageVo = noteBookService.query(bo);
        GsonUtils.printJsonObject(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        noteBookService.deleteByIds(ids);
        GsonUtils.printSuccess(response);
    }

}
