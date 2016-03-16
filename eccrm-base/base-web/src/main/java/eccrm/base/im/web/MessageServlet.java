package eccrm.base.im.web;

import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pool.ThreadPool;
import org.apache.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Michael
 */
@WebServlet(name = "MessageServlet", urlPatterns = "/servlet/message", asyncSupported = true)
public class MessageServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        Logger logger = Logger.getLogger(MessageServlet.class);
        logger.info("初始化消息处理机...");
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        // 启动异步
        final AsyncContext context = request.startAsync();
        // 设置超时时间为90秒
        context.setTimeout(90 * 1000);

        // 利用线程池启动一个线程
        MessageThread thread = new MessageThread(context, SecurityContext.getUserId());
        ThreadPool.getInstance().execute(thread);

    }

}
