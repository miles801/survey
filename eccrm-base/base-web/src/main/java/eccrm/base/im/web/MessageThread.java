package eccrm.base.im.web;

import com.michael.cache.redis.RedisServer;
import com.ycrl.core.SystemContainer;
import com.ycrl.utils.gson.GsonUtils;
import redis.clients.jedis.ShardedJedis;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Michael
 */
public class MessageThread implements Runnable {

    private AsyncContext context;
    private String userId;

    public MessageThread(AsyncContext context, String userId) {
        this.context = context;
        this.userId = userId;
    }

    @Override
    public void run() {
        RedisServer redisServer = SystemContainer.getInstance().getBean(RedisServer.class);
        if (redisServer == null) {
            return;
        }
        String messageKey = "MSG:" + userId;    // 消息
        ShardedJedis redisClient = redisServer.getRedisClient();
        int i = 0;
        while (i++ < 1000) {
            List<String> messages = redisClient.lrange(messageKey, 0, -1);
            if (messages != null && !messages.isEmpty()) {
                GsonUtils.printData((HttpServletResponse) context.getResponse(), messages);
                context.complete();
                return;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 自然结束，没有读取到消息
        GsonUtils.printData((HttpServletResponse) context.getResponse(), new String[]{});
        context.complete();
        redisClient.close();
    }

}
