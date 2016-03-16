package eccrm.base.tenement.domain;

import java.util.Date;

/**
 * ECCRM系统的公共类，包含几乎所有的类都需要的属性
 * 实体类必须继承该类
 *
 * @author miles
 * @datetime 2014/3/20 10:27
 */
public abstract class CrmBaseDomain implements TenementSymbol {
    protected String id;
    protected String creatorId;
    protected String creatorName;
    protected String modifierId;
    protected String modifierName;
    protected Date createdDatetime;
    protected Date modifiedDatetime;
    protected String description;
    private String tenementId;

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(Date modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    @Override
    public String getTenementId() {
        return tenementId;
    }

    @Override
    public void setTenementId(String tenementId) {
        this.tenementId = tenementId;
    }
}
