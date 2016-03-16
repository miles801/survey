package eccrm.utils.codeutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 13-12-23 下午4:03
 */
public class ListPage extends Page {
    /**
     * 是否使用时间选择器
     */
    private boolean datepicker = false;
    /**
     * 整个页面滚动：适用于有的列表页面由很多小的block组成，而每一个block的高度又无法确认，
     * 此时就需要整体进行滚动
     */
    private boolean wholePageScroll = false;

    /**
     * 是否使用分页插件
     */
    private boolean allowPager = true;
    /**
     * 是否加载jquery
     */
    private boolean jquery = true;

    /**
     * 是否启用highcharts
     */
    private boolean highcharts = false;
    /**
     * 是否加载angularjs
     */
    private boolean angular = true;
    /**
     * 是否加载moment.js
     */
    private boolean dealDate = false;

    /**
     * 查询条件
     */
    private List<Row> queryConditionRows;
    /**
     * 查询按钮栏
     */
    private List<Button> queryBarButtons;
    /**
     * 列表页面上的按钮栏
     */
    private List<Button> tableHeaderButtons;

    /**
     * 列表的文字
     */
    private String tableHeaderText;

    private List<String> items;

    /**
     * 是否允许使用默认名称
     */
    private boolean defaultName = true;

    private boolean tab;
    /**
     * 查询条件的行数，最大3行
     */
    private Integer conditionRows;
    //是否是树形页面
    private boolean tree = false;

    private boolean allowCheckbox = true;

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


    public List<Button> getQueryBarButtons() {
        return queryBarButtons;
    }

    public void setQueryBarButtons(List<Button> queryBarButtons) {
        this.queryBarButtons = queryBarButtons;
    }

    public List<Row> getQueryConditionRows() {
        return queryConditionRows;
    }

    public void setQueryConditionRows(List<Row> queryConditionRows) {
        this.queryConditionRows = queryConditionRows;
    }

    public List<Button> getTableHeaderButtons() {
        return tableHeaderButtons;
    }

    public void setTableHeaderButtons(List<Button> tableHeaderButtons) {
        this.tableHeaderButtons = tableHeaderButtons;
    }

    public void addQueryCondition(Row row) {
        if (queryConditionRows == null) {
            queryConditionRows = new ArrayList<Row>();
        }
        queryConditionRows.add(row);
    }

    /**
     * 要符合name:type:length:addOnIcon的格式
     *
     * @param rowDescriptions
     * @return
     */
    public ListPage addQueryCondition(String... rowDescriptions) {
        if (rowDescriptions == null || rowDescriptions.length < 1) {
            return this;
        }
        Row row = new Row(rowDescriptions);
        addQueryCondition(row);
        return this;
    }

    /**
     * 批量添加查询栏的按钮（不使用图标）
     * 格式为name:icon:url:click:extraClass
     *
     * @param buttonNames
     */
    public void addQueryBarButton(String... buttonNames) {
        if (buttonNames != null && buttonNames.length > 0) {
            for (String bn : buttonNames) {
                if (!"".equals(bn.trim())) {
                    addQueryBarButton(new Button(bn));
                }
            }
        }
    }

    public void addQueryBarButton(Button button) {
        if (queryBarButtons == null) {
            queryBarButtons = new ArrayList<Button>();
        }
        queryBarButtons.add(button);
    }

    /**
     * 格式为name:icon:url:click:extraClass
     *
     * @param buttonNames
     */
    public void addTableHeaderButton(String... buttonNames) {
        if (buttonNames != null && buttonNames.length > 0) {
            for (String buttonName : buttonNames) {
                if ("".equals(buttonName.trim())) continue;
                addTableHeaderButton(new Button(buttonName));
            }
        }
    }

    public void addTableHeaderButton(Button button) {
        if (tableHeaderButtons == null) {
            tableHeaderButtons = new ArrayList<Button>();
        }
        tableHeaderButtons.add(button);
    }

    /**
     * 允许使用"xx,yy,zz","aa","bb,cc"
     * 也可以使用"xx","yy","zz"的方式进行传递
     *
     * @param item
     */
    public void addItem(String... item) {
        if (items == null) {
            items = new ArrayList<String>();
        }
        if (item != null && item.length > 0) {
            for (String foo : item) {
                if (!"".equals(foo.trim())) {
                    String[] temps = foo.split(",");
                    for (String temp : temps) {
                        if ("".equals(temp.trim())) continue;
                        items.add(temp);
                    }
                }
            }
        }
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public boolean isTab() {
        return tab;
    }

    public void setTab(boolean tab) {
        this.tab = tab;
    }

    public boolean isAllowCheckbox() {
        return allowCheckbox;
    }

    public void setAllowCheckbox(boolean allowCheckbox) {
        this.allowCheckbox = allowCheckbox;
    }

    public boolean isTree() {
        return tree;
    }

    public void setTree(boolean tree) {
        this.tree = tree;
    }

    public String getTableHeaderText() {
        return tableHeaderText;
    }

    public void setTableHeaderText(String tableHeaderText) {
        this.tableHeaderText = tableHeaderText;
    }

    public boolean isAllowPager() {
        return allowPager;
    }

    public void setAllowPager(boolean allowPager) {
        this.allowPager = allowPager;
    }

    public void enableDefaultName() {
        defaultName = true;
    }

    public void disableDefaultName() {
        defaultName = false;
    }

    public boolean isWholePageScroll() {
        return wholePageScroll;
    }

    public void setWholePageScroll(boolean wholePageScroll) {
        this.wholePageScroll = wholePageScroll;
    }

    public Integer getConditionRows() {
        return conditionRows;
    }

    public void setConditionRows(Integer conditionRows) {
        this.conditionRows = conditionRows;
    }

    public boolean isDefaultName() {
        return defaultName;
    }

    public void setDefaultName(boolean defaultName) {
        this.defaultName = defaultName;
    }

    public boolean isHighcharts() {
        return highcharts;
    }

    public void setHighcharts(boolean highcharts) {
        this.highcharts = highcharts;
    }
}
