package eccrm.knowledge.survey.web;

import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import eccrm.knowledge.survey.bo.SubjectCategoryBo;
import eccrm.knowledge.survey.domain.SubjectCategory;
import eccrm.knowledge.survey.service.SubjectCategoryService;
import eccrm.knowledge.survey.vo.SubjectCategoryVo;
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
 * 题库分类
 */
@Controller
@RequestMapping(value = {"/survey/category"})
public class SubjectCategoryCtrl extends BaseController {
    @Resource
    private SubjectCategoryService subjectCategoryService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "knowledge/survey/category/list/category_list";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        SubjectCategory libraryCategory = GsonUtils.wrapDataToEntity(request, SubjectCategory.class);
        subjectCategoryService.save(libraryCategory);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        SubjectCategory libraryCategory = GsonUtils.wrapDataToEntity(request, SubjectCategory.class);
        subjectCategoryService.update(libraryCategory);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        SubjectCategoryVo vo = subjectCategoryService.findById(id);
        GsonUtils.printData(response, vo);
    }


    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        SubjectCategoryBo bo = GsonUtils.wrapDataToEntity(request, SubjectCategoryBo.class);
        PageVo pageVo = subjectCategoryService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQueryChildren", params = {"id"}, method = RequestMethod.POST)
    public void pageQueryChildren(HttpServletRequest request,
                                  @RequestParam String id, HttpServletResponse response) {
        SubjectCategoryBo bo = GsonUtils.wrapDataToEntity(request, SubjectCategoryBo.class);
        PageVo pageVo = subjectCategoryService.pageQueryChildren(id, bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        subjectCategoryService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/validTree", method = RequestMethod.GET)
    public void validTree(HttpServletResponse response, HttpServletRequest request) {
        List<SubjectCategoryVo> data = subjectCategoryService.validTree();
        GsonUtils.printData(response, data);
    }

    @ResponseBody
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public void tree(HttpServletResponse response, HttpServletRequest request) {
        List<SubjectCategoryVo> data = subjectCategoryService.tree();
        GsonUtils.printData(response, data);
    }
}
