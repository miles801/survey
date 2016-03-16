package eccrm.knowledge.survey.web;

import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.core.web.BaseController;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.service.SubjectItemService;
import eccrm.knowledge.survey.service.SubjectService;
import eccrm.knowledge.survey.vo.SubjectItemVo;
import eccrm.knowledge.survey.vo.SubjectVo;
import org.springframework.context.annotation.Scope;
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
 * 题目
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"/survey/subject"})
public class SubjectCtrl extends BaseController {
    @Resource
    private SubjectService subjectService;

    @Resource
    private SubjectItemService subjectItemService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "knowledge/survey/subject/list/subject_list";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = GsonUtils.wrapDataToEntity(request, Subject.class);
        subjectService.save(subject);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = GsonUtils.wrapDataToEntity(request, Subject.class);
        subjectService.update(subject);
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        SubjectVo vo = subjectService.findById(id);
        GsonUtils.printData(response, vo);
    }

    /**
     * 查询题目下的所有选项
     *
     * @param id 题目id
     */
    @ResponseBody
    @RequestMapping(value = "/items", params = {"id"}, method = RequestMethod.GET)
    public void queryItems(@RequestParam String id, HttpServletResponse response) {
        List<SubjectItemVo> data = subjectItemService.queryBySubjectId(id);
        GsonUtils.printData(response, data);
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(HttpServletRequest request, HttpServletResponse response) {
        SubjectBo bo = GsonUtils.wrapDataToEntity(request, SubjectBo.class);
        PageVo pageVo = subjectService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(String ids, HttpServletResponse response) {
        if (ids == null || "".equals(ids.trim())) {
            throw new IllegalArgumentException("批量删除时，没有获得对应的ID列表（使用逗号分隔的字符串）!");
        }
        String[] idArr = ids.split(",");
        subjectService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }

}
