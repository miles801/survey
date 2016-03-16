package eccrm.base.log;

import com.ycrl.utils.gson.GsonUtils;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.log.dao.OperateLogDao;
import eccrm.base.log.domain.OperateLog;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author Michael
 */
@Aspect
@Component
public class OperateLogAop {
    private Logger logger = Logger.getLogger(OperateLogAop.class);

    @Before(value = "execution(* *..dao.*.*(..))")
    public void before(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        LogInfo log = method.getAnnotation(LogInfo.class);
        if (log != null) {
            String type = log.type().toString();
            String describe = log.describe();
            OperateLog ol = new OperateLog();
            ol.setOperateType(type);
            ol.setDescription(describe);
            if (joinPoint.getArgs() != null) {
                ol.setContent(GsonUtils.toJson(joinPoint.getArgs()));
            }
            operateLogDao.save(ol);
        }
    }


    @Resource
    private OperateLogDao operateLogDao;

}
