package eccrm.knowledge.survey.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.michael.poi.exp.ExportEngine;
import com.ycrl.base.common.JspAccessType;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.DateStringConverter;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.utils.gson.JsonObjectUtils;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.bo.SurveyBo;
import eccrm.knowledge.survey.bo.SurveyReportBo;
import eccrm.knowledge.survey.domain.Survey;
import eccrm.knowledge.survey.domain.SurveyReportDetail;
import eccrm.knowledge.survey.service.*;
import eccrm.knowledge.survey.vo.SubjectVo;
import eccrm.knowledge.survey.vo.SurveyReportVo;
import eccrm.knowledge.survey.vo.SurveySubjectVo;
import eccrm.knowledge.survey.vo.SurveyVo;
import eccrm.utils.NetUtils;
import eccrm.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
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

    @Resource
    private SurveyReportService surveyReportService;

    @Resource
    private SurveyReportDetailService surveyReportDetailService;

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
        String answer = request.getParameter("answer");
        request.setAttribute("pageType", "PREVIEW");
        if (StringUtils.isNotEmpty(answer) && "true".equals(answer)) {
            request.setAttribute("surveyReportId", request.getParameter("surveyReportId"));
            request.setAttribute("pageType", "ANSWER");
        }
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
        Assert.hasText(surveyId, "没有获得试卷ID");
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
    @RequestMapping(value = "/querySubjectWithItems", params = {"surveyReportId"}, method = RequestMethod.GET)
    public void querySubjectWithItems(@RequestParam String surveyReportId, HttpServletResponse response) {
        List<SubjectVo> subjectVos = surveyReportService.querySubjectWithItems(surveyReportId);
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


    // =================================        SurveyReport       ===========================

    // 查询个人可注册试卷
    @ResponseBody
    @RequestMapping(value = "/canregister", method = RequestMethod.GET)
    public void queryAllPersonalCanRegisterSurvey(HttpServletRequest request, HttpServletResponse response) {
        List<SurveyVo> data = surveyService.queryAllCanRegister();
        GsonUtils.printData(response, data);
    }

    // 试卷注册
    @ResponseBody
    @RequestMapping(value = "/register", params = "id", method = RequestMethod.POST)
    public void register(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
        surveyService.register(id, NetUtils.getClientIpAddress(request));
        GsonUtils.printSuccess(response);
    }

    // 查询个人未完成（可考试）试卷
    @ResponseBody
    @RequestMapping(value = "/unfinished", method = RequestMethod.GET)
    public void queryPersonalUnfinishedSurvey(HttpServletResponse response) {
        List<SurveyReportVo> data = surveyReportService.queryUnfinish();
        GsonUtils.printData(response, data);
    }

    // 获取指定试卷的上一题
    // ID:已注册的试卷的ID
    @ResponseBody
    @RequestMapping(value = "/prevsubject", params = {"id", "index"}, method = RequestMethod.GET)
    public void queryPrevSubject(@RequestParam String id,
                                 @RequestParam Integer index, HttpServletResponse response) {
        SubjectVo data = surveyReportService.getPrevSubject(id, index);
        GsonUtils.printData(response, data);
    }

    // 获取指定试卷的下一题
    // ID:已注册的试卷的ID
    @ResponseBody
    @RequestMapping(value = "/nextsubject", params = "id", method = RequestMethod.GET)
    public void queryNextSubject(@RequestParam String id, HttpServletResponse response) {
        SubjectVo data = surveyReportService.getNextSubject(id);
        GsonUtils.printData(response, data);
    }

    // 答题
    @ResponseBody
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public void answer(HttpServletRequest request, HttpServletResponse response) {
        SurveyReportDetail[] detail = GsonUtils.wrapDataToEntity(request, SurveyReportDetail[].class);
        SurveyResult data = surveyReportDetailService.answer(detail);
        GsonUtils.printData(response, data);
    }

    @ResponseBody
    @RequestMapping(value = "/answer", params = {"surveyReportId", "subjectId"}, method = RequestMethod.GET)
    public void answer(@RequestParam String surveyReportId,
                       @RequestParam String subjectId, HttpServletResponse response) {
        GsonUtils.printData(response, surveyReportDetailService.getAnswer(surveyReportId, subjectId));
    }

    // 查询个人已完成试卷
    @ResponseBody
    @RequestMapping(value = "/finished", method = RequestMethod.POST)
    public void queryPersonalFinishedSurvey(HttpServletRequest request, HttpServletResponse response) {
        SurveyReportBo bo = GsonUtils.wrapDataToEntity(request, SurveyReportBo.class);
        PageVo data = surveyReportService.queryFinish(bo);
        GsonUtils.printData(response, data);
    }

    // 查询指定试卷的成绩（答题情况）
    @ResponseBody
    @RequestMapping(value = "/score", params = "id", method = RequestMethod.GET)
    public void querySurveyScore(@RequestParam String id, HttpServletResponse response) {
        SurveyReportVo data = surveyReportService.findById(id);
        GsonUtils.printData(response, data);
    }

    @ResponseBody
    @RequestMapping(value = "/template", params = "type", method = RequestMethod.GET)
    public void downloadTemplate(@RequestParam String type, HttpServletResponse response) {
        InputStream input = SurveyCtrl.class.getClassLoader().getResourceAsStream("survey_import" + type + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        String disposition = null;//
        try {
            String fileName = "导入模板.xlsx";
            if ("1".equals(type)) {
                fileName = "单选题" + fileName;
            } else if ("2".equals(type)) {
                fileName = "多选题" + fileName;
            } else if ("3".equals(type)) {
                fileName = "判断题" + fileName;
            } else if ("4".equals(type)) {
                fileName = "填空题" + fileName;
            } else {
                Assert.isTrue(false, "错误的模板类型!");
            }
            disposition = "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", disposition);
        try {
            IOUtils.copy(input, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/import", params = {"attachmentIds", "type"}, method = RequestMethod.POST)
    public void importData(@RequestParam String attachmentIds, @RequestParam String type, HttpServletResponse response) {
        surveyReportService.importData(attachmentIds.split(","), type);
        GsonUtils.printSuccess(response);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        SurveyReportBo bo = GsonUtils.wrapDataToEntity(request, SurveyReportBo.class);
        PageVo vo = surveyReportService.pageQuery(bo);
        List data = vo.getData();
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateStringConverter("yyyy-MM-dd HH:mm:ss"))
                .create();
        String json = gson.toJson(data);
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject o = new JsonObject();
        o.add("c", element);
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("考试成绩.xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", disposition);
        try {
            new ExportEngine().export(response.getOutputStream(), this.getClass().getClassLoader().getResourceAsStream("survey_report.xlsx"), o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public void queryAllOnlineIP(HttpServletResponse response) {
        List<SurveyReportVo> data = surveyReportService.queryAllOnlineIP();
        GsonUtils.printData(response, data);
    }

}
