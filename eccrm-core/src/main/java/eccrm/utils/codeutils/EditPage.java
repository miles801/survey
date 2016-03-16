package eccrm.utils.codeutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 13-12-23 下午4:05
 */
public class EditPage extends Page {
    /**
     * 是否使用时间选择器
     */
    private boolean datepicker = false;
    /**
     * 是否加载jquery
     */
    private boolean jquery = true;
    /**
     * 是否加载angularjs
     */
    private boolean angular = true;
    /**
     * 是否加载moment.js
     */
    private boolean dealDate = true;

    /**
     * 是否使用树
     */
    private boolean tree = false;

    private boolean tab = false;

    /**
     * 保存并新建
     */
    private boolean saveAndNew = false;

    /**
     * 主要文本信息
     */
    private List<Row> formRows;

    public boolean isDatepicker() {
        return datepicker;
    }

    public void setDatepicker(boolean datepicker) {
        this.datepicker = datepicker;
    }

    public boolean isJquery() {
        return jquery;
    }

    public void setJquery(boolean jquery) {
        this.jquery = jquery;
    }

    public boolean isAngular() {
        return angular;
    }

    public void setAngular(boolean angular) {
        this.angular = angular;
    }

    public boolean isDealDate() {
        return dealDate;
    }

    public void setDealDate(boolean dealDate) {
        this.dealDate = dealDate;
    }

    public boolean isTree() {
        return tree;
    }

    public void setTree(boolean tree) {
        this.tree = tree;
    }

    public List<Row> getFormRows() {
        return formRows;
    }

    public void setFormRows(List<Row> formRows) {
        this.formRows = formRows;
    }

    /**
     * 添加多行到表单
     *
     * @param rows
     * @return
     */
    public EditPage addFormRow(Row... rows) {
        if (rows != null && rows.length > 0) {
            if (formRows == null) {
                formRows = new ArrayList<Row>();
            }
            for (Row row : rows) {
                formRows.add(row);
            }
        }
        return this;
    }

    /**
     * 通过每行的一组描述数据添加一行
     *
     * @param rowDescription
     * @return
     */
    public EditPage addFormRow(String... rowDescription) {
        if (rowDescription != null && rowDescription.length > 0) {
            Row row = new Row(rowDescription);
            addFormRow(row);
        }
        return this;
    }

    public boolean isTab() {
        return tab;
    }

    public void setTab(boolean tab) {
        this.tab = tab;
    }

    public boolean isSaveAndNew() {
        return saveAndNew;
    }

    public void setSaveAndNew(boolean saveAndNew) {
        this.saveAndNew = saveAndNew;
    }
}
