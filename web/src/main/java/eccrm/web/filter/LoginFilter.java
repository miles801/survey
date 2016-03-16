package eccrm.web.filter;

import com.ycrl.core.context.SecurityContext;
import eccrm.base.auth.domain.AccreditData;
import eccrm.base.auth.service.PersonalPermissionContext;
import eccrm.core.security.LoginInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 用户登录过滤器，如果没有登录，则强制重定向到登录页面（默认的登录页面可以通过参数loginHtml进行设置）
 * 如果已经登录，则从session中取出登录的账户以及对应的权限放到登录上下文中
 *
 * @author miles
 * @datetime 13-12-15 下午11:43
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*", asyncSupported = true)
public class LoginFilter implements Filter {

    private String defaultLoginHtml = "/index.html";

    private Logger logger = Logger.getLogger(LoginFilter.class);
    //静态资源
    private String[] static_suffix = new String[]{".js", ".less", ".css", ".jpg", ".png", ".gif", ".html", ".htm", ".ttf", ".svg", ".woff", ".map", ".ico", ".mp3", ".class"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("加载过滤器:登录验证过滤器....");
        String configLoginHtml = filterConfig.getInitParameter("loginHtml");
        if (configLoginHtml != null && !"".equals(configLoginHtml.trim())) {
            defaultLoginHtml = configLoginHtml;
        }
        if (!defaultLoginHtml.startsWith("/")) {
            defaultLoginHtml += "/";
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.addHeader("X-UA-Compatible", "chrome=1,IE=edge,IE=11,IE=10,IE=9,IE=8");
        // 是否为静态文件
        String requestUri = request.getRequestURI();
        for (String suffix : static_suffix) {
            if (requestUri.endsWith(suffix)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String contextPath = request.getContextPath();
        if (requestUri.startsWith(contextPath + "/login") || requestUri.startsWith(contextPath + "/index.html")
                || requestUri.startsWith(contextPath + "/base/user/register") || requestUri.startsWith(contextPath + "/attachment/upload2")) {
            logger.info("without login request uri:" + requestUri);
            filterChain.doFilter(request, response);
            return;
        }
        // 记录当前系统的线程数
//        logger.info("当前线程数:" + Thread.getAllStackTraces().size() + ",激活线程数:" + Thread.activeCount());


        HttpSession session = request.getSession();
        Boolean login = (Boolean) session.getAttribute(LoginInfo.HAS_LOGIN);
        String userId = (String) session.getAttribute(LoginInfo.USER);
        String username = (String) session.getAttribute(LoginInfo.USERNAME);
        String empId = (String) session.getAttribute(LoginInfo.EMPLOYEE);
        String empName = (String) session.getAttribute(LoginInfo.EMPLOYEE_NAME);
        String tenementId = (String) session.getAttribute(LoginInfo.TENEMENT);
        if (login != null && login && StringUtils.isNotEmpty(userId)) {
            // 设置上下文信息
            SecurityContext.set(userId, username, tenementId);
            SecurityContext.setEmpId(empId);
            SecurityContext.setEmpName(empName);
            setPermissionContext(session);

            // 打印访问信息
            logger.info(String.format("+++++++++++++ ( %s ) --%s(%s)-->%s", Thread.currentThread().getName(), userId, empName, requestUri));
            long start = System.currentTimeMillis();
            filterChain.doFilter(request, response);
            long end = System.currentTimeMillis();
            long period = end - start;
            logger.info(String.format("------------- ( %s ) --%s(%s)-->%s,用时:%d ms", Thread.currentThread().getName(), userId, empName, requestUri, period));
            if (period > 500) {
                logger.warn(String.format("请求时间过长:%d ms --> %s", period, requestUri));
            }
            SecurityContext.remove();
            PersonalPermissionContext.remove();
            return;
        }
        response.sendRedirect(contextPath + defaultLoginHtml);
    }

    private Cookie addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(-1);// 在关闭浏览器时失效
        return cookie;
    }

    @SuppressWarnings("unchecked")
    /**
     * 从session中权限上下文信息保存到ThreadLocal中
     */
    private void setPermissionContext(HttpSession session) {
        PersonalPermissionContext.setDataResource((Map<String, List<AccreditData>>) session.getAttribute(PersonalPermissionContext.DATA_RESOURCE));
    }

    @Override
    public void destroy() {

    }
}
