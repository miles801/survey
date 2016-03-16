package com.ycrl.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * @author Michael
 */
//@Component
//@Aspect
public class LogAop {
    private Logger logger = Logger.getLogger(LogAop.class);

    @Around(value = "execution(* *..service.impl.*.*(..)) || execution(* *..service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        // 获得类名称
        String className = joinPoint.getTarget().getClass().getName();
        // 获得方法名称
        String methodName = joinPoint.getSignature().getName();
        logger.info("++++++++\t" + className + "." + methodName);
        Object obj = null;
        obj = joinPoint.proceed();
        logger.info("--------\t" + className + "." + methodName);
        long end = System.currentTimeMillis();
        if (end - start > 1000) {
            logger.warn("处理时间过长(" + (end - start) / 1000 + "s):" + className + "." + methodName);
        }
        return obj;
    }
}
