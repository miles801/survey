package eccrm.knowledge.survey.service;

import com.ycrl.core.pager.PageVo;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.vo.SubjectVo;

import java.util.List;

public interface SubjectService {

    /**
     * 保存题目，并保存对应的选项
     */
    void save(Subject subject);

    /**
     * 更新题目，会先删除之前的选项，然后重建新的题目选项
     * 注意：更新后，原有题目选项的ID将会丢失
     */
    void update(Subject subject);

    PageVo pageQuery(SubjectBo bo);

    SubjectVo findById(String id);

    void deleteByIds(String[] ids);

    /**
     * 获取需要导出的数据
     *
     * @param bo
     * @return
     */
    List<SubjectVo> exportData(SubjectBo bo);

}
