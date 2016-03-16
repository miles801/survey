package eccrm.utils.codeutils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author miles
 * @datetime 13-12-24 下午6:27
 */
public class CodeTemplateUtils {
    private Logger logger = Logger.getLogger(CodeTemplateUtils.class);
    private Configuration config;
    private String root;

    public CodeTemplateUtils() {
    }

    public CodeTemplateUtils(Configuration config, String root) {
        this.config = config;
        this.root = root;
    }

    public void generateAll(Module module) throws Exception {
        generateList(module);
        generateEdit(module);
    }

    public void generateEdit(Module module) throws Exception {
        generateEditPage(module);
        generateEditPageJs(module);
    }

    /**
     * 生成所有的列表页面以及js
     *
     * @param module
     * @throws Exception
     */
    public void generateList(Module module) throws Exception {
        generateModuleJs(module);
        generateListPage(module);
        generateListPageJs(module);
    }

    /**
     * 生成模块的js
     *
     * @param module
     * @throws Exception
     */
    public void generateModuleJs(Module module) throws Exception {
        String templateName = "freemarker/module_js.ftl";
        Template template = config.getTemplate(templateName);
        String path = getPagePath(module) + "/" + module.getEntity() + ".js";
        createFile(path);
        template.process(module, new PrintWriter(new FileOutputStream(path)));
    }

    /**
     * 生成列表页面
     *
     * @param module
     * @throws Exception
     */
    public void generateListPage(Module module) throws Exception {
        String templateName = "freemarker/page_list.ftl";
        ListPage listPage = module.getListPage();
        if (listPage.isTab()) {
            templateName = "freemarker/page_list_tab.ftl";
        }
        if (listPage.isTree()) {
            templateName = "freemarker/page_list_tree.ftl";
        }
        if (listPage.getType() == PageType.MARKETING_EXECUTE_ORDER.getType()) {
            templateName = "freemarker/page_list_tree_marketing.ftl";
        }
        Template template = config.getTemplate(templateName);
        String path = getPagePath(module) + "/list/" + module.getEntity() + "_list.jsp";
        createFile(path);
        template.process(module, new PrintWriter(new FileOutputStream(path)));
        return;
    }

    /**
     * 生成编辑页面
     *
     * @param module
     * @throws Exception
     */
    public void generateEditPage(Module module) throws Exception {
        String templateName = "freemarker/page_edit.ftl";
        EditPage page = module.getEditPage();
        if (page.isTab()) {
            templateName = "freemarker/page_edit_tab.ftl";
        }
        Template template = config.getTemplate(templateName);
        String path = getPagePath(module) + "/edit/" + module.getEntity() + "_edit.jsp";
        createFile(path);
        template.process(module, new PrintWriter(new FileOutputStream(path)));
        return;
    }

    /**
     * 生成编辑页面的tab页面
     *
     * @param module
     * @throws Exception
     */
    public void generateTabEditPage(Module module) throws Exception {
        String templateName = "freemarker/page_edit_tab.ftl";
        Template template = config.getTemplate(templateName);
        String path = getPagePath(module) + "/edit/" + module.getEntity() + "_edit.jsp";
        createFile(path);
        template.process(module, new PrintWriter(new FileOutputStream(path)));
        return;
    }

    /**
     * 生成列表页面对应的js
     *
     * @param module
     * @throws Exception
     */
    public void generateListPageJs(Module module) throws Exception {
        String templateName = "freemarker/page_list_js.ftl";
        ListPage page=module.getListPage();
        if (page.getType() == PageType.MARKETING_EXECUTE_ORDER.getType()) {
            templateName = "freemarker/page_list_marketing_js.ftl";
        }
        Template template = config.getTemplate(templateName);
        String path = getPagePath(module) + "/list/" + module.getEntity() + "_list.js";
        createFile(path);
        template.process(module, new PrintWriter(new FileOutputStream(path)));
        return;
    }

    /**
     * 生成编辑页面js
     *
     * @param module
     * @throws Exception
     */
    public void generateEditPageJs(Module module) throws Exception {
        String templateName = "freemarker/page_edit_js.ftl";
        Template template = config.getTemplate(templateName);
        String path = getPagePath(module) + "/edit/" + module.getEntity() + "_edit.js";
        createFile(path);
        template.process(module, new PrintWriter(new FileOutputStream(path)));
        return;
    }

    private String getPagePath(Module module) {
        String extraPath = module.getExtraPath();
        if (extraPath == null || "".equals(extraPath.trim())) {
            return root + module.getName() + "/" + module.getEntity();
        } else {
            return (root + module.getName() + "/" + extraPath + "/" + module.getEntity()).replaceAll("//", "/");
        }
    }

    /**
     * 根据path判断文件是否存在，如果不存在则创建
     *
     * @param path
     */
    public void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                logger.info("创建文件:" + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Module module = ModuleThreadLocal.getModule();
            if (module != null && !module.isPermitOverride()) {
                throw new RuntimeException("文件[" + file.getPath() + "]已经存在，不执行生成操作!");
            } else {
                logger.info("覆盖文件:" + file.getPath());
            }
        }
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }
}
