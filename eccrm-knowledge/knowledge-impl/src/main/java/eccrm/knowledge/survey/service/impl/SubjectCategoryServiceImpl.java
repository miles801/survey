package eccrm.knowledge.survey.service.impl;

import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.pager.PageVo;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.knowledge.survey.bo.SubjectCategoryBo;
import eccrm.knowledge.survey.dao.SubjectCategoryDao;
import eccrm.knowledge.survey.domain.SubjectCategory;
import eccrm.knowledge.survey.service.SubjectCategoryService;
import eccrm.knowledge.survey.vo.SubjectCategoryVo;
import eccrm.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service("subjectCategoryService")
public class SubjectCategoryServiceImpl implements SubjectCategoryService {
    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    @Override
    public void save(SubjectCategory libraryCategory) {
        subjectCategoryDao.save(libraryCategory);
    }

    @Override
    public void update(SubjectCategory libraryCategory) {
        subjectCategoryDao.update(libraryCategory);
    }

    @Override
    public PageVo pageQuery(SubjectCategoryBo bo) {
        PageVo vo = new PageVo();
        Long total = subjectCategoryDao.getTotal(bo);
        if (total == null || total == 0) return vo;
        vo.setTotal(total);
        List<SubjectCategory> data = subjectCategoryDao.query(bo);
        vo.setData(BeanWrapBuilder.newInstance()
                .wrapList(data, SubjectCategoryVo.class));
        return vo;
    }

    @Override
    public SubjectCategoryVo findById(String id) {
        SubjectCategory libraryCategory = subjectCategoryDao.findById(id);
        return BeanWrapBuilder.newInstance().wrap(libraryCategory, SubjectCategoryVo.class);
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            if (StringUtils.isNotEmpty(id)) {
                SubjectCategory lc = subjectCategoryDao.findById(id);
                if (lc == null) {
                    continue;
                }
                // 如果是启用的，则注销
                // 如果是未启用的，则删除
                // 其他状态，跳过
                String status = lc.getStatus();
                if (CommonStatus.ACTIVE.getValue().equals(status)) {
                    lc.setStatus(CommonStatus.INACTIVE.getValue());
                } else if (CommonStatus.INACTIVE.getValue().equals(status)) {
                    subjectCategoryDao.deleteById(id);
                }
            }
        }
    }


    @Override
    public List<SubjectCategoryVo> validTree() {
        SubjectCategoryBo bo = new SubjectCategoryBo();
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<SubjectCategory> data = subjectCategoryDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .addProperties(new String[]{"id", "parentId", "name"})
                .wrapList(data, SubjectCategoryVo.class);
    }

    @Override
    public List<SubjectCategoryVo> tree() {
        List<SubjectCategory> data = subjectCategoryDao.query(null);
        return BeanWrapBuilder.newInstance()
                .addProperties("id,parentId,parentName,sequenceNo,name,status".split(","))
                .setCallback(new BeanWrapCallback<SubjectCategory, SubjectCategoryVo>() {
                    @Override
                    public void doCallback(SubjectCategory o, SubjectCategoryVo vo) {
                        vo.setStatusName(ParameterContainer.getInstance().getSystemName("SP_COMMON_STATE", o.getStatus()));
                    }
                })
                .wrapList(data, SubjectCategoryVo.class);
    }

    @Override
    public PageVo pageQueryChildren(String id, SubjectCategoryBo bo) {
        if (bo == null) {
            bo = new SubjectCategoryBo();
        }
        bo.setPath("/" + id + "/");
        // 按照PATH和排序号进行排序
        bo.setId(null);// 防止叠加条件
        PageVo vo = new PageVo();
        Long total = subjectCategoryDao.getTotal(bo);
        if (total == null || total == 0) {
            return vo;
        }
        vo.setTotal(total);
        List<SubjectCategory> data = subjectCategoryDao.query(bo);
        // 重排序：按照path和sequenceNo来进行重排
        Collections.sort(data, new Comparator<SubjectCategory>() {
            @Override
            public int compare(SubjectCategory o1, SubjectCategory o2) {
                String path1 = o1.getPath().substring(0, o1.getPath().indexOf(o1.getId()));
                String path2 = o2.getPath().substring(0, o2.getPath().indexOf(o2.getId()));
                int length = path1.length() - path2.length();
                if (length != 0) {
                    return length;
                }
                return o1.getSequenceNo() - o2.getSequenceNo();
            }
        });
        vo.setData(BeanWrapBuilder.newInstance()
                .addProperties("id,parentId,parentName,sequenceNo,name,status".split(","))
                .setCallback(new BeanWrapCallback<SubjectCategory, SubjectCategoryVo>() {
                    @Override
                    public void doCallback(SubjectCategory o, SubjectCategoryVo vo) {
                        vo.setStatusName(ParameterContainer.getInstance().getSystemName("SP_COMMON_STATE", o.getStatus()));
                    }
                })
                .wrapList(data, SubjectCategoryVo.class));
        return vo;
    }
}
