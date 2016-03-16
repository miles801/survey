package eccrm.base.user.web;

import eccrm.core.security.LoginInfo;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * HttpSession的监听器：主要用于在session被销毁时进行登出
 *
 * @author miles
 * @datetime 2014/4/17 0:58
 */
public class SessionListener implements HttpSessionListener {
    private Logger logger = Logger.getLogger(SessionListener.class);


    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String username = (String) session.getAttribute(LoginInfo.USERNAME);
        if (username == null) return;
        OnlineUser onlineUser = OnlineUser.getInstance();
        onlineUser.remove(username);
        logger.info(String.format("session destroyed,[%s(%s)] logoff....当前在线用户数:%d", session.getAttribute(LoginInfo.USERNAME), session.getAttribute(LoginInfo.EMPLOYEE_NAME), onlineUser.count()));

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }
}
