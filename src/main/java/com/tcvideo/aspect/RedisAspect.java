package com.tcvideo.aspect;

import com.tcvideo.redis.RedisCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by caiteng on 2017-08-14.
 */
@Component
@Aspect
public class RedisAspect {

    @Autowired
    @Qualifier("redisCache")
    private RedisCache redisCache;

    /**
     * 配置get切点，对所有service的get方法的结果进行缓存
     */
    @Pointcut("execution(* com.tcvideo.*.service.*.get(..))")
    public void getPointCut(){
    }
    /**
     * 配置update切点，对所有service的get方法的结果进行缓存
     */
    @Pointcut("execution(* com.tcvideo.*.service.*.get(..))")
    public void updatePointCut(){
    }

    @Around("getPointCut()")
    public Object aroundGetPointCut(ProceedingJoinPoint joinPoint){

        //获取key key="tc_video"+类名+方法名+参数
        StringBuffer redisKey=new StringBuffer();
        redisKey.append("tc_video");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        redisKey.append("_").append(className);
        String methodName = joinPoint.getSignature().getName();
        redisKey.append("_").append(methodName);
        //先获取目标方法参数
        Object[] args = joinPoint.getArgs();
        for(Object obj:args){
            redisKey.append("_").append(String.valueOf(obj));
        }
        System.out.println("redisKey为"+redisKey.toString());

        //获取从redis中查询到的对象
        Object objectFromRedis = redisCache.getDataFromRedis(redisKey.toString());

        //如果查询到了
        if(null != objectFromRedis){
            System.out.println("从redis中查询到了数据...不需要查询数据库");
            return objectFromRedis;
        }

        System.out.println("没有从redis中查到数据...");

        //没有查到，那么查询数据库
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable e) {

            e.printStackTrace();
        }

        System.out.println("从数据库中查询的数据...");

        //后置：将数据库中查询的数据放到redis中
        System.out.println("把数据库查询的数据存储到redis中...");

        redisCache.setDataToRedis(redisKey.toString(), object);

        //将查询到的数据返回
        return object;
   }

    @Around("updatePointCut()")
    public Object aroundUpdatePointCut(ProceedingJoinPoint joinPoint){
        //获取key key="tc_video"+类名+get+参数
        StringBuffer redisKey=new StringBuffer();
        redisKey.append("tc_video");

        String className = joinPoint.getTarget().getClass().getSimpleName();
        redisKey.append("_").append(className);
        String methodName = joinPoint.getSignature().getName();
        redisKey.append("_").append("get");
        //先获取目标方法参数
        Object[] args = joinPoint.getArgs();
        for(Object obj:args){
            redisKey.append("_").append(String.valueOf(obj));
        }
        System.out.println("redisKey为"+redisKey.toString());
        //执行方法，获得更新后新数据
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable e) {

            e.printStackTrace();
        }
        //将数据库中查询的数据放到redis中
        System.out.println("把更新后的数据存储到redis中...");

        redisCache.setDataToRedis(redisKey.toString(), object);

        //将查询到的数据返回
        return object;

    }

}
