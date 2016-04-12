package eccrm.knowledge.survey.service;

import com.ycrl.core.pager.PageVo;
import eccrm.knowledge.survey.bo.SurveyReportBo;
import eccrm.knowledge.survey.domain.SurveyReport;
import eccrm.knowledge.survey.vo.SubjectVo;
import eccrm.knowledge.survey.vo.SurveyReportVo;

import java.util.List;

/**
 * @author Michael
 */
public interface SurveyReportService {

    /**
     * 保存
     */
    String save(SurveyReport surveyReport);

    /**
     * 更新
     */
    void update(SurveyReport surveyReport);

    /**
     * 分页查询
     */
    PageVo pageQuery(SurveyReportBo bo);

    /**
     * 根据ID查询对象的信息
     */
    SurveyReportVo findById(String id);

    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);


    /**
     * 开始考试
     */
    void start(SurveyReport surveyReport);

    /**
     * 查询个人在有效期内还未完成的试卷
     */
    List<SurveyReportVo> queryUnfinish();

    /**
     * 查询个人已完成试卷
     */
    PageVo queryFinish(SurveyReportBo bo);

    /**
     * 获取下一题
     *
     * @param id
     * @return
     */
    SubjectVo getNextSubject(String id);

    /**
     * 给指定题型导入题目
     *
     * @param attachmentIds 题目数据来源（附件ID）
     * @param type          题型编号
     */
    void importData(String[] attachmentIds, String type);

    /**
     * 查询所有在线考试的IP
     *
     * @return
     */
    List<SurveyReportVo> queryAllOnlineIP();

    /**
     * 获得上一题
     *
     * @param id 试卷ID
     * @return
     */
    SubjectVo getPrevSubject(String id, Integer index);

    /**
     * 查询指定问卷下的所有题目以及题目对应的选项
     *
     * @param surveyReportId 个人试卷ID
     * @return
     */
    List<SubjectVo> querySubjectWithItems(String surveyReportId);
}
