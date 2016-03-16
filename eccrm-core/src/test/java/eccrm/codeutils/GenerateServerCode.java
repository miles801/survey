package eccrm.codeutils;

import com.ycrl.GenerateDemo;
import org.junit.Test;

/**
 * 生成后端代码
 *
 * @author Michael
 */
public class GenerateServerCode {

    @Test
    public void testGenerateServerCode() throws Exception {
        // 参数说明（注意：这个文件修改后不允许提交）
        // 第一个参数：类名称
        // 第二个参数：模块名称
        // 第三个参数：包名称
        // 第四个参数：项目根路径
        // 第五个参数：作者
        new GenerateDemo("Volunteer", "spec", "com.michael.spec", "D:\\workspace\\lr\\pkqn", "Michael")
                .generate();
    }
}
