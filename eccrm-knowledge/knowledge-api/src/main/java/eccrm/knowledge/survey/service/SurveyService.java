package eccrm.knowledge.survey.service;

import com.ycrl.core.pager.PageVo;
import eccrm.knowledge.survey.bo.SurveyBo;
import eccrm.knowledge.survey.domain.Survey;
import eccrm.knowledge.survey.vo.SurveyVo;

import java.util.List;

/**
 * @author Michael
 */
public interface SurveyService {

    /**
     * 保存
     */
    String save(Survey survey);

    /**
     * 更新
     */
    void update(Survey survey);

    /**
     * 分页查询
     */
    PageVo pageQuery(SurveyBo bo);

    /**
     * 查询状态为有效的数据，不进行分页，常用于对外提供的查询接口
     */
    List<SurveyVo> queryValid(SurveyBo bo);

    /**
     * 根据ID查询对象的信息
     */
    SurveyVo findById(String id);

    void deleteByIds(String[] ids);

    /**
     * 发布问卷
     *
     * @param id 问卷ID
     * @throws RuntimeException 问卷状态不是“启用状态”
     * @throws RuntimeException 问卷已过期
     * @throws RuntimeException 问卷未设置题目
     */
    void publish(String id);
}
