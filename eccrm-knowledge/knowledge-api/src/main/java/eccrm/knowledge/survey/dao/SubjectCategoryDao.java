package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.bo.SubjectCategoryBo;
import eccrm.knowledge.survey.domain.SubjectCategory;

import java.util.List;

public interface SubjectCategoryDao {

    String save(SubjectCategory libraryCategory);

    void update(SubjectCategory libraryCategory);

    List<SubjectCategory> query(SubjectCategoryBo bo);

    Long getTotal(SubjectCategoryBo bo);

    SubjectCategory findById(String id);

    int deleteById(String id);

    /**
     * 根据分类的名称获取分类的ID
     *
     * @param categoryName 分类名称
     * @return ID
     */
    String findByName(String categoryName);
}
