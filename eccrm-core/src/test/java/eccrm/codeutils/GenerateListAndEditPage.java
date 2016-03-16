package eccrm.codeutils;

import eccrm.utils.codeutils.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * 生成编辑页面和列表页面，即对应的js
 *
 * @author miles
 * @datetime 13-12-23 下午2:24
 */
public class GenerateListAndEditPage {
    private Configuration config = null;
    private String root;
    private Logger logger = Logger.getLogger(GenerateListAndEditPage.class);

    @Before
    public void setUp() throws Exception {
        config = new Configuration();
        root = "D:/workspace/lr/hh-oa/web\\src\\main\\webapp\\app\\";
        config.setTemplateLoader(new ClassTemplateLoader(EditPage.class, "/"));
    }


    @Test
    public void testGenerateListAndEditPage() throws Exception {
        Module module = new Module("oa", "knowledge", "知识");
        module.setPermitOverride(true);
        module.setAuthor("Michael");

        // 编辑页面
        setEditPage(module);

        // 列表页面
        setListPage(module);

        CodeTemplateUtils codeUtils = new CodeTemplateUtils(config, root);
        codeUtils.generateAll(module);
    }

    private void setListPage(Module module) {
        ListPage listPage = new ListPage();
        listPage.addQueryCondition(new Row(
                "标题:label", "title",
                "关键字:label", "keywords"
        ));
        listPage.addQueryBarButton("查询:search:query", "重置:repeat:reset");
        listPage.addTableHeaderButton("新建:plus:marketing/add", "删除:remove", "启用", "注销");
        listPage.addItem("标题", "内容", "状态", "操作");
        module.setListPage(listPage);
    }

    private void setEditPage(Module module) {
        EditPage editPage = new EditPage();
        editPage.setSaveAndNew(true);
        editPage.setDatepicker(true);
        //name:type:length:addOnIcon
        editPage.addFormRow(
                "标题:label", "title:text:col-2-half", "类型:label", "type:select:col-2-half"
        ).addFormRow(
                "内容:label", "content:textarea:col-6-half"
        ).addFormRow(
                "关键字:label", "keywords:text:col-6-half"
        ).addFormRow(
                "外部链接:label", "url:text:col-6-half"
        ).addFormRow(
                "状态:label", "status:select:col-6-half"
        );
        module.setEditPage(editPage);
    }

}
