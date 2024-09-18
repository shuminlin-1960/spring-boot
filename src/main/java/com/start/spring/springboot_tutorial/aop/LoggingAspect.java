package com.start.spring.springboot_tutorial.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
@Slf4j
public class LoggingAspect {
//    Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
/*
    @Before(value="execution(* *.*.*.*.controller.DepartmentController.*(..))")
    public void beforelogger(JoinPoint jp) {
        System.out.println("Before loggers");
        System.out.println("After method called: " + jp.getSignature());
        Object[] objs = jp.getArgs();
        if (objs != null && objs.length >0) {
            System.out.println("JP param passed: " + jp.getArgs()[0].toString());
        }
    }

    @After(value="execution(* com.start.spring.springboot_tutorial.controller.DepartmentController.*(..))")
    public void afterlogger(JoinPoint jp) {
        System.out.println("Afterloggers");
        System.out.println("After method called: " + jp.getSignature());
        Object[] objs = jp.getArgs();
        if (objs != null && objs.length >0) {
            System.out.println("JP param passed: " + jp.getArgs()[0].toString());
        }
    }
*/

//    TODO Make these two work
//    @AfterReturning(value= "execution(* demo.ShoppingCart.quantity(..))",
//        returning = "retVal")
//      public void afterReturning(JoinPoint Jp, String retVal) {
//
//        System.out.println("After returning, return value:" + retVal);
//
//    }
//
//
//    @AfterThrowing(value= "execution(* demo.ShoppingCart.quantity(..))",
//            throwing = "e")
//    public void afterThrowing(JoinPoint Jp, Exception e) {
//
//        System.out.println("After throwing,  exception:" + e.getMessage());
//
//    }


//    TODO Make these two work

//    Works
//    Package-based
    @Pointcut ("within(com.start.spring.springboot_tutorial.service.*)")
//    Class-based
//    @Pointcut ("this(com.start.spring.springboot_tutorial.controller.DepartmentController)")
//    Method-based
//    @Pointcut ("execution(* *.*.*.*.controller.DepartmentController.*(..))")
//    Anywhere at the target methods tagged with @CustomerAnnotation, NEED CODE CHANGES
//    @Pointcut ("@annotation(com.start.spring.springboot_tutorial.annotation.CustomerAnnotation)")

    public void loggingPointCut() {}

    @Around("loggingPointCut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("Around method called: " + jp.getSignature());

        Object obj = null;
        try {
            obj = jp.proceed();
        } catch (Exception e) {
            throw e;
        }

        System.out.println("Object returned: " + obj);

        return obj;
    }

}
