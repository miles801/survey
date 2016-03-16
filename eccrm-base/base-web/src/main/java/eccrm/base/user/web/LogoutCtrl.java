package eccrm.base.user.web;

import com.michael.cache.redis.RedisServer;
import com.ycrl.core.SystemContainer;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.utils.string.StringUtils;
import eccrm.base.user.service.LoginLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登出，提供退出系统、强制踢出用户下线的功能
 *
 * @author miles
 * @datetime 2014/6/20 11:31
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/logout")
public class LogoutCtrl {
    @Resource
    private LoginLogService loginLogService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        RedisServer redisServer = SystemContainer.getInstance().getBean(RedisServer.class);
        ShardedJedis redisClient = redisServer.getRedisClient();
        // 移除数据权限
        redisClient.del("PD:" + SecurityContext.getEmpId());
        // 移除登录信息
        redisClient.hdel("PF:", SecurityContext.getEmpId());
        redisClient.hdel("user:" + SecurityContext.getEmpId(), SecurityContext.getEmpId());
        String token = request.getParameter("token");
        token = token == null ? (String) request.getSession().getAttribute("token") : token;
        if (StringUtils.isNotEmpty(token)) {
            redisClient.hdel("login:", token);
        }
        redisClient.close();

        request.getSession().invalidate();
        return "redirect:/";
    }

}
