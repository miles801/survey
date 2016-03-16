package eccrm.utils.codeutils.server;

import eccrm.utils.codeutils.CodeTemplateUtils;
import eccrm.utils.codeutils.FileType;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author miles
 * @datetime 14-3-12 下午1:20
 * 约定：模块以-api，-impl，-web命名
 */
public class ServerCodeBuildEngine {
    public static final String PRE_PATH = "freemarker/server/";
    private Logger logger = Logger.getLogger(CodeTemplateUtils.class);
    private Configuration config;
    private ServerCodeBuildConfig serverConfig;

    public ServerCodeBuildEngine(ServerCodeBuildConfig serverConfig) {
        config = new Configuration();
        config.setTemplateLoader(new ClassTemplateLoader(ServerCodeBuildEngine.class, "/"));
        this.serverConfig = serverConfig;
    }

    public void generateAll() {
        generateEntity();
        generateDao();
        generateService();
        generateWeb();
        generateJunit();
    }

    public void generateAllWithModule() {
        generateModule();
        generateAll();
    }

    public void generateJunit() {
        //module
        generate(PRE_PATH + "junit_service.ftl", FileType.JUNIT_SERVICE);
        generate(PRE_PATH + "junit_dao.ftl", FileType.JUNIT_DAO);
    }

    public void generateModule() {
        //module
        generate(PRE_PATH + "pom.module.ftl", FileType.POM_ROOT);
        //api
        generate(PRE_PATH + "pom.api.ftl", FileType.POM_API);
        //impl
        generate(PRE_PATH + "pom.impl.ftl", FileType.POM_IMPL);
        //web
        generate(PRE_PATH + "pom.web.ftl", FileType.POM_WEB);

    }

    public void generate(String template, FileType type) {
        try {
            freemarker.template.Template foo = config.getTemplate(template);
            String path = getPath(type);
            createFile(path);
            foo.process(serverConfig, new PrintWriter(new FileOutputStream(path)));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateService() {
        //service
        generate(PRE_PATH + "service.ftl", FileType.SERVICE);

        //service impl
        generate(PRE_PATH + "service.impl.ftl", FileType.SERVICE_IMPL);
    }


    public void generateDao() {
        //service
        generate(PRE_PATH + "dao.ftl", FileType.DAO);

        //service impl
        generate(PRE_PATH + "dao.impl.ftl", FileType.DAO_IMPL);

        //mapping
        generate(PRE_PATH + "mapping.ftl", FileType.MAPPING);
    }


    public void generateEntity() {
        //entity
        generate(PRE_PATH + "domain.ftl", FileType.ENTITY);
        //vo
        generate(PRE_PATH + "vo.ftl", FileType.VO);
        //bo
        generate(PRE_PATH + "bo.ftl", FileType.BO);

    }

    public void generateWeb() {
        //controller
        generate(PRE_PATH + "controller.ftl", FileType.CONTROLLER);
    }

    public void createFile(String path) {
        File file = new File(path);
//        if (!file.exists()) {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            logger.info("创建文件:" + file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } else {
//            logger.error("文件[" + path + "]已存在，跳过！");
//        }
    }

    public String getPath(FileType type) {
        StringBuilder builder = new StringBuilder();
        int foo = type.getType();
        builder.append(serverConfig.getWorkspace())
                .append("/eccrm-")
                .append(serverConfig.getModule())
                .append("/");
        //根root
        if (foo == FileType.POM_ROOT.getType()) {
            return builder.append("pom.xml").toString();
        }
        builder.append(serverConfig.getModule());
        if (foo < 200) {
            builder.append("-api");
            if (foo == FileType.POM_API.getType()) {
                return builder.append("/pom.xml").toString();
            }
        } else if (foo < 300) {
            builder.append("-impl");
            if (foo == FileType.POM_IMPL.getType()) {
                return builder.append("/pom.xml").toString();
            }
        } else if (foo < 400) {
            builder.append("-web");
            if (foo == FileType.POM_WEB.getType()) {
                return builder.append("/pom.xml").toString();
            }
        }
        //映射文件
        if (type.getType().equals(FileType.MAPPING.getType())) {
            builder.append("/src/main/resources/mapping/")
                    .append(serverConfig.getEntity())
                    .append(".hbm.xml");
            return builder.toString();
        }
        //单元测试文件
        if (foo == FileType.JUNIT_SERVICE.getType()) {
            return builder.append("/src/test/java/")
                    .append(serverConfig.getPackagePath().replaceAll("\\.", "/"))
                    .append("/service/impl/")
                    .append(serverConfig.getEntity()).append("ServiceImplTest.java")
                    .toString();
        }
        if (foo == FileType.JUNIT_DAO.getType()) {
            return builder.append("/src/test/java/")
                    .append(serverConfig.getPackagePath().replaceAll("\\.", "/"))
                    .append("/dao/impl/")
                    .append(serverConfig.getEntity()).append("DaoImplTest.java")
                    .toString();
        }
        //java文件
        builder.append("/src/main/java/")
                .append(serverConfig.getPackagePath().replaceAll("\\.", "/"));
        switch (foo) {
            case 101:
                builder.append("/domain/")
                        .append(serverConfig.getEntity());
                break;
            case 102:
                builder.append("/vo/")
                        .append(serverConfig.getEntity()).append("Vo");
                break;
            case 103:
                builder.append("/bo/")
                        .append(serverConfig.getEntity()).append("Bo");
                break;
            case 104:
                builder.append("/dao/")
                        .append(serverConfig.getEntity()).append("Dao");
                break;
            case 105:
                builder.append("/service/")
                        .append(serverConfig.getEntity()).append("Service");
                break;
            case 106:
                /*builder.append("/service/")
                        .append(serverConfig.getEntity()).append("Service");
                        break;
                */
            case 201:
//                builder.append("/dao/impl/")
//                        .append(serverConfig.getEntity()).append("DaoImpl");
                break;
            case 202:
                builder.append("/dao/impl/")
                        .append(serverConfig.getEntity()).append("DaoImpl");
                break;
            case 203:
                builder.append("/service/impl/")
                        .append(serverConfig.getEntity()).append("ServiceImpl");
                break;
            case 204:
//                builder.append("/service/impl/")
//                        .append(serverConfig.getEntity()).append("ServiceImpl");
                break;
            case 301:
                builder.append("/web/")
                        .append(serverConfig.getEntity()).append("Ctrl");
                break;
            default:
                throw new RuntimeException("错误的代码[" + foo + "]");
        }
        builder.append(".java");
        return builder.toString();
    }

    public void generateMapping() {
        //mapping
        generate(PRE_PATH + "mapping.ftl", FileType.MAPPING);
    }
}
