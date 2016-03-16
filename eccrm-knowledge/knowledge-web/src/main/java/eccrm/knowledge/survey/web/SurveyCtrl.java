package eccrm.knowledge.survey.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.utils.gson.JsonObjectUtils;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.bo.SurveyBo;
import eccrm.knowledge.survey.domain.Survey;
import eccrm.knowledge.survey.service.SurveyService;
import eccrm.knowledge.survey.service.SurveySubjectService;
import eccrm.knowledge.survey.vo.SubjectVo;
import eccrm.knowledge.survey.vo.SurveySubjectVo;
import eccrm.knowledge.survey.vo.SurveyVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/survey"})
public class SurveyCtrl extends BaseController {
    @Resource
    private SurveyService surveyService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList() {
        return "knowledge/survey/list/survey_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd(HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.ADD);
        return "knowledge/survey/edit/survey_edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        Survey survey = GsonUtils.wrapDataToEntity(request, Survey.class);
        String id = surveyService.save(survey);
        GsonUtils.printData(response, id);
    }

    @RequestMapping(value = "/modify", params = {"id"}, method = RequestMethod.GET)
    public String toModify(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.MODIFY);
        request.setAttribute("id", id);
        return "knowledge/survey/edit/survey_edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Survey survey = GsonUtils.wrapDataToEntity(request, Survey.class);
        surveyService.update(survey);
        GsonUtils.printSuccess(response);
    }

    /**
     * 发布
     */
    @RequestMapping(value = "/publish", params = {"id"}, method = RequestMethod.POST)
    @ResponseBody
    public void publish(@RequestParam String id, HttpServletResponse response) {
        surveyService.publish(id);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = {"/detail"}, params = {"id"}, method = RequestMethod.GET)
    public String toDetail(@RequestParam String id, HttpServletRequest request) {
        request.setAttribute(JspAccessType.PAGE_TYPE, JspAccessType.DETAIL);
        request.setAttribute("id", id);
        return "knowledge/survey/edit/survey_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public void findById(@RequestParam String id, HttpServletResponse response) {
        SurveyVo vo = surveyService.findById(id);
        GsonUtils.printData(response, vo);
    }

    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        SurveyBo bo = GsonUtils.wrapDataToEntity(request, SurveyBo.class);
        PageVo pageVo = surveyService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", params = {"ids"}, method = RequestMethod.DELETE)
    public void deleteByIds(@RequestParam String ids, HttpServletResponse response) {
        String[] idArr = ids.split(",");
        surveyService.deleteByIds(idArr);
        GsonUtils.printSuccess(response);
    }


    @RequestMapping(value = "/preview", params = {"id"}, method = RequestMethod.GET)
    public String preview(@RequestParam String id,
                          HttpServletRequest request, HttpServletResponse response) {
        SurveyVo survey = surveyService.findById(id);
        request.setAttribute("beans", survey);
        request.setAttribute("pageType", "PREVIEW");
        return "knowledge/survey/preview";
    }


    @RequestMapping(value = "/analysis", params = {"id"}, method = RequestMethod.GET)
    public String analysis(@RequestParam String id,
                           HttpServletRequest request, HttpServletResponse response) {
        SurveyVo survey = surveyService.findById(id);
        request.setAttribute("beans", survey);
        request.setAttribute("pageType", "ANALYSIS");
        return "knowledge/survey/preview";
    }

    // -------------------------- SurveySubject -------------------------

    @Resource
    private SurveySubjectService surveySubjectService;

    @RequestMapping(value = "/addSubjects", method = RequestMethod.POST)
    @ResponseBody
    public void addSubjects(HttpServletRequest request, HttpServletResponse response) {
        JsonObject jsonObject = com.ycrl.utils.gson.GsonUtils.wrapDataToEntity(request, JsonObject.class);
        String surveyId = JsonObjectUtils.getStringProperty(jsonObject, "surveyId");
        Assert.hasText(surveyId, "没有获得调查问卷ID");
        String subjectIds = JsonObjectUtils.getStringProperty(jsonObject, "subjectIds");
        Assert.hasText(subjectIds, "没有获得题目ID");
        surveySubjectService.saveBatch(surveyId, subjectIds.split(","));
        com.ycrl.utils.gson.GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/querySubjects", params = {"surveyId"}, method = RequestMethod.POST)
    public void pageQuery(@RequestParam String surveyId, HttpServletRequest request, HttpServletResponse response) {
        SubjectBo bo = com.ycrl.utils.gson.GsonUtils.wrapDataToEntity(request, SubjectBo.class);
        List<SurveySubjectVo> subjectVos = surveySubjectService.querySubject(surveyId, bo);
        GsonUtils.printData(response, subjectVos);
    }

    @ResponseBody
    @RequestMapping(value = "/querySubjectWithItems", params = {"surveyId"}, method = RequestMethod.GET)
    public void querySubjectWithItems(@RequestParam String surveyId, HttpServletResponse response) {
        List<SubjectVo> subjectVos = surveySubjectService.querySubjectWithItems(surveyId);
        GsonUtils.printData(response, subjectVos);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSubjects", params = {"surveyId", "subjectIds"}, method = RequestMethod.DELETE)
    public void deleteSubjectByIds(@RequestParam String surveyId,
                                   @RequestParam String subjectIds, HttpServletResponse response) {
        surveySubjectService.delete(surveyId, subjectIds.split(","));
        GsonUtils.printSuccess(response);
    }

    @ResponseBody
    @RequestMapping(value = "/updateSubjectSequence", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public void updateSubjectSequence(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        JsonObject jsonObject = GsonUtils.wrapDataToEntity(request, JsonObject.class);
        if (jsonObject != null) {
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue().getAsInt();
                map.put(key, value);
            }
        }
        surveySubjectService.updateSequenceNo(map);
        GsonUtils.printSuccess(response);
    }
}
