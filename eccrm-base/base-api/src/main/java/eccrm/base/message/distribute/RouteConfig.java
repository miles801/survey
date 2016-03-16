package eccrm.base.message.distribute;

import eccrm.utils.NetUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael
 */
public class RouteConfig {
    // 需要路由的ip集合

    private static RouteConfig routeConfig = new RouteConfig();
    private Set<String> routeIpSet;

    private RouteConfig() {
        // 读取路由配置文件
        InputStream inputStream = RouteConfig.class.getClassLoader().getResourceAsStream("distribute.properties");
        Logger logger = Logger.getLogger(RouteConfig.class);
        if (inputStream == null) {
            logger.warn("没有获得路由配置文件...系统的通知将不会分发到其他服务器!");
            return;
        }

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String routeIp = properties.getProperty("route.ip");
        if (routeIp == null || "".equals(routeIp.trim())) {
            logger.warn("路由配置文件中没有配置要分发到的服务器ip（route.ip），系统的通知将不会分发到其他服务器!");
            return;
        }
        String[] ips = routeIp.trim().split(",");

        // 获得当前IP
        routeIpSet = new HashSet<String>();
        String currentIp = NetUtils.getLocalIP();
        for (String ip : ips) {
            if (ip.equals(currentIp)) {
                continue;
            }
            routeIpSet.add(ip);
        }
    }

    public static RouteConfig getInstance() {
        return routeConfig;
    }


    /**
     * 获得需要路由的ip集合
     *
     * @return 集合或者null
     */
    public Set<String> getRouteIps() {
        return routeIpSet;
    }
}
