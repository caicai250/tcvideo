package com.tcvideo.aspect;

/**
 * Created by caiteng on 2017-09-04.
 */

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 注册切面
 * @author
 *
 */
@Aspect
@Component
public class RegisterAspect {

    private static final String ASPECT_CONTROLLER_EXECUTION="execution(* com.tcvideo.*.controller.*.*(..))";
    private static final String ASPECT_SERVICE_EXECUTION="execution(* com.tcvideo.*.service.UserService.*(..))";

    /**
     * 查询结果处理
     * @param joinPoint 接入点
     * @param result 目标方法的返回结果
     */
    @AfterReturning(value=ASPECT_SERVICE_EXECUTION, returning = "result")
    public void afterMethod(JoinPoint joinPoint, Object result){
        //logger.info(result);
    }

    private static Logger logger = Logger.getLogger(RegisterAspect.class);
    /**
     * 对项目中所有controller进行日志输出
     * @param joinPoint
     */
    @Before(value=ASPECT_CONTROLLER_EXECUTION)
    public void controllerLog(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info(className+":"+methodName);
    }

}