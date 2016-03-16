package eccrm.base.message.distribute;

import com.google.gson.Gson;
import eccrm.base.message.Message;
import eccrm.utils.NetUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;

/**
 * @author Michael
 */
public class DistributeHelper {
    private static DistributeHelper ourInstance = new DistributeHelper();

    public static DistributeHelper getInstance() {
        return ourInstance;
    }

    private Logger logger = Logger.getLogger(DistributeHelper.class);

    private DistributeHelper() {
    }

    /**
     * 分发消息
     *
     * @param messageType 消息类型
     * @param message     消息主体
     * @return 分发结果
     */
    public boolean distribute(String messageType, Message message) {
        boolean flag = true;
        if (messageType == null || "".equals(messageType.trim()) || message == null) {
            return false;
        }
        Set<String> ips = RouteConfig.getInstance().getRouteIps();
        if (ips == null || ips.isEmpty()) {
            return false;
        }
        String localIp = NetUtils.getLocalIP();
        // 分发消息
        for (String ip : ips) {
            // 本地ip不发起
            if (ip.contains(localIp)) {
                continue;
            }
            DistributeResult result = sendRequest(ip, messageType, message);
            if (!result.isSuccess()) {
                logger.error("消息分发失败:" + ip + ",错误码:" + result.getCode() + ",可能原因:" + result.getMessage());
                flag = false;
            }
        }
        return flag;

    }

    private DistributeResult sendRequest(String ip, String messageType, Message message) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://" + ip + "/core/message/push?messageType=" + messageType);
        DistributeResult result = new DistributeResult();
        try {
            post.setEntity(new StringEntity(new Gson().toJson(message), "utf-8"));
            CloseableHttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
            int code = statusLine.getStatusCode();
            result.setCode(code + "");
            if (code == 200) {
                result.setSuccess(true);
            } else {
                result.setMessage(statusLine.getReasonPhrase());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
