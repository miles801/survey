package eccrm.knowledge.survey.service;

import com.ycrl.core.pager.PageVo;
import eccrm.knowledge.survey.bo.SubjectCategoryBo;
import eccrm.knowledge.survey.domain.SubjectCategory;
import eccrm.knowledge.survey.vo.SubjectCategoryVo;

import java.util.List;

/**
 * 题库分类
 */
public interface SubjectCategoryService {

    void save(SubjectCategory libraryCategory);

    void update(SubjectCategory libraryCategory);

    /**
     * 分页查询
     */
    PageVo pageQuery(SubjectCategoryBo bo);

    SubjectCategoryVo findById(String id);

    /**
     * 分页查询指定节点下的孩子节点（直接节点），包括自身
     */
    PageVo pageQueryChildren(String id, SubjectCategoryBo bo);

    void deleteByIds(String[] ids);


    /**
     * 查询所有有效的题库分类，组装成树
     * 仅返回id、名称、上级id信息
     */
    List<SubjectCategoryVo> validTree();

    /**
     * 查询所有的题库分类，组装成树
     * 仅返回id、名称、上级id信息、排序号、状态
     */
    List<SubjectCategoryVo> tree();

}
