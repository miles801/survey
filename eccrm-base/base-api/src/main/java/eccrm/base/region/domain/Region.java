package eccrm.base.region.domain;

import com.ycrl.base.common.CommonDomain;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;
import eccrm.utils.tree.DynamicTree;

/**
 * @author miles
 * @datetime 2014-03-25
 */
public class Region extends CommonDomain implements DynamicTree, EnumSymbol {

    /**
     * 区号
     */
    private String code;
    /**
     * 省、市、区...
     */
    @EnumClass(RegionType.class)
    private Integer type;
    private String zipcode;
    private Integer sequenceNo;
    private Region parent;
    private String parentId;
    private String name;
    private Boolean leaf;

    /**
     * 全拼
     */
    private String pinyin;
    /**
     * 简拼
     */
    private String jp;

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getName() {
        return name;
    }


    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    @Override
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
