package eccrm.knowledge.survey.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.michael.poi.exp.ExportEngine;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.DateStringConverter;
import com.ycrl.utils.gson.GsonUtils;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
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

    @RequestMapping(value = "/exportInfo", method = RequestMethod.GET)
    public void exportInfo(HttpServletRequest request, HttpServletResponse response) {
        SubjectBo bo = GsonUtils.wrapDataToEntity(request, SubjectBo.class);
        List<SubjectVo> data = subjectService.exportData(bo);
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateStringConverter("yyyy年MM月dd日"))
                .create();
        String json = gson.toJson(data);
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject o = new JsonObject();
        o.add("c", element);
        String disposition = null;//
        try {
            disposition = "attachment;filename=" + URLEncoder.encode("题目导出数据.xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", disposition);
        try {
            new ExportEngine().export(response.getOutputStream(), this.getClass().getClassLoader().getResourceAsStream("survey_export" + bo.getSubjectType() + ".xlsx"), o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
