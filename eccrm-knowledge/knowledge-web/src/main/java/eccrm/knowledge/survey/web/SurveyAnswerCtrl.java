package eccrm.knowledge.survey.web;

import com.ycrl.core.pager.PageVo;
import com.ycrl.core.web.BaseController;
import com.ycrl.utils.gson.GsonUtils;
import com.ycrl.utils.string.StringUtils;
import eccrm.knowledge.survey.bo.SurveyAnswerBo;
import eccrm.knowledge.survey.domain.SurveyAnswer;
import eccrm.knowledge.survey.service.SurveyAnswerService;
import eccrm.knowledge.survey.service.SurveyService;
import eccrm.knowledge.survey.vo.SurveyAnswerVo;
import eccrm.knowledge.survey.vo.SurveyVo;
import eccrm.utils.ArrayUtils;
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
 * @author Michael
 */
@Controller
@RequestMapping(value = {"/survey/answer"})
public class SurveyAnswerCtrl extends BaseController {
    @Resource
    private SurveyAnswerService surveyAnswerService;

    @Resource
    private SurveyService surveyService;

    /**
     * 回答试卷
     * 如果已经答过题了，则进入浏览界面
     * 如果还未答题，则进入答题界面
     * @param id         试卷ID
     * @param businessId 需要绑定的业务ID
     * @param userId     答题人ID
     * @param userName   答题人名称
     * @param userType   答题人类型
     * @param batchId   批次ID
     */
    @RequestMapping(value = "", params = {"id", "businessId", "userId", "userName", "userType","batchId"}, method = RequestMethod.GET)
    public String answer(@RequestParam String id,
                         @RequestParam String businessId,
                         @RequestParam String userId,
                         @RequestParam String userName,
                         @RequestParam String userType,
                         @RequestParam String batchId,
                         HttpServletRequest request, HttpServletResponse response) {
        boolean answered = surveyAnswerService.hasAnswered(id, businessId, userId);
        request.setAttribute("pageType", answered ? "VIEW" : "ANSWER");
        SurveyVo survey = surveyService.findById(id);
        request.setAttribute("beans", survey);
        request.setAttribute("businessId", businessId);
        request.setAttribute("userId", userId);
        request.setAttribute("userName", StringUtils.decodeByUTF8(userName));
        request.setAttribute("userType", userType);
        request.setAttribute("batchId", batchId);
        return "knowledge/survey/preview";
    }

    /**
     * 查询具体某一个人的答题结果
     * @param surveyId   试卷ID
     * @param businessId 业务ID
     * @param userId     答题人ID
     */
    @RequestMapping(value = "/result", params = {"surveyId", "businessId", "userId"}, method = RequestMethod.GET)
    @ResponseBody
    public void answer(@RequestParam String surveyId,
                       @RequestParam String businessId,
                       @RequestParam String userId,
                       HttpServletResponse response) {
        List<SurveyAnswerVo> vos = surveyAnswerService.queryAnswer(surveyId, businessId, userId);
        GsonUtils.printData(response, vos);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response) {
        SurveyAnswer[] surveyAnswer = GsonUtils.wrapDataToEntity(request, SurveyAnswer[].class);
        surveyAnswerService.batchSave(ArrayUtils.arrayToList(surveyAnswer));
        GsonUtils.printSuccess(response);
    }


    @ResponseBody
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST)
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        SurveyAnswerBo bo = GsonUtils.wrapDataToEntity(request, SurveyAnswerBo.class);
        PageVo pageVo = surveyAnswerService.pageQuery(bo);
        GsonUtils.printData(response, pageVo);
    }


}
