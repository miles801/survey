package eccrm.knowledge.survey.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;

/**
 * 题目选项
 *
 * @author Michael
 */
public class SubjectItem extends CrmBaseDomain {
    private String name;
    private Integer sequenceNo;
    /**
     * 所属题目
     */
    private String subjectId;
    private String subjectName;

    // 是否为正确答案
    private Boolean isRight;


    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
