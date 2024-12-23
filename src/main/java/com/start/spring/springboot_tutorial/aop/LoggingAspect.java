package com.start.spring.springboot_tutorial.aop;

import com.start.spring.springboot_tutorial.entity.Department;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import java.time.*;
import java.util.Date;
import java.util.List;

@Aspect
@Component
@EnableAspectJAutoProxy
@Slf4j
//This anotation does not work as method level
@ConditionalOnProperty(
        value="enable.aop.based.conditional.debugging",
        havingValue = "true",
        matchIfMissing = false)
public class LoggingAspect {
//    Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before(value="execution(* *.*.*.*.controller.DepartmentController.*(..))")
    public void beforelogger(JoinPoint jp) {
        System.out.println("in @Before");
        System.out.println("Before method called: " + jp.getSignature());
        Object[] objs = jp.getArgs();
        if (objs != null && objs.length >0) {
            System.out.println("JP param passed: " + jp.getArgs()[0].toString());
        }
    }

    @After(value="execution(* com.start.spring.springboot_tutorial.controller.DepartmentController.*(..))")
    public void afterlogger(JoinPoint jp) {
        System.out.println("in @After");
        System.out.println("After method called: " + jp.getSignature());
        Object[] objs = jp.getArgs();
        if (objs != null && objs.length >0) {
            System.out.println("JP param passed: " + jp.getArgs()[0].toString());
        }
    }


//    TODO Make these two work
    @AfterReturning(value = "execution(java.util.List<com.start.spring.springboot_tutorial.entity.Department> com.start.spring.springboot_tutorial.controller.DepartmentController.fetchDepartments(..))",
        returning = "result")
      public void afterReturning(JoinPoint Jp, List<Department> result) {
        System.out.println("In @AfterReturning, return value:" + result);

    }


    @AfterThrowing(value="execution(java.util.List<com.start.spring.springboot_tutorial.entity.Department> com.start.spring.springboot_tutorial.controller.DepartmentController.fetchDepartments(..))",
            throwing = "e")
    public void afterThrowing(JoinPoint Jp, Exception e) {

        System.out.println("in @AfterThrowing,  exception:" + e.getMessage());

    }



//    This @Pointcut + @Around definitions also work
//    Package-based
    @Pointcut ("within(com.start.spring.springboot_tutorial.controller.*)")

//    Class-based
//    @Pointcut ("this(com.start.spring.springboot_tutorial.controller.DepartmentController)")

//    Method-based
//    Anywhere at the target methods tagged with @CustomerAnnotation, NEED CODE CHANGES
//    @Pointcut ("@annotation(com.start.spring.springboot_tutorial.annotation.CustomerAnnotation)")

//    @Pointcut ("execution(* *.*.*.*.controller.DepartmentController.*(..))")
    public void loggingPointCut() {}
    @Around("loggingPointCut()")

//    @Around(value = "execution(* *.*.*.*.controller.DepartmentController.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("In @Around method before calling jp.proceed(): " + jp.getSignature());

        Object obj = null;
        try {
            LocalDateTime start = LocalDateTime.now();
            Date startDate = new Date();
            obj = jp.proceed();

            LocalDateTime end = LocalDateTime.now();
            Date endDate = new Date();

            long duration = (end.getNano () - start.getNano()) / 1000000;

            long milli = endDate.getTime() - startDate.getTime();

            System.out.println("Time taken for the method call calculate by LocalDateTime: " + duration);
            System.out.println("Time taken for the method call calculate by old Date: " + milli);
            if (obj != null) System.out.println("In @Around method after calling jp.proceed().  Object returned " + obj);
        } catch (Exception e) {
            throw e;
        }

        return obj;
    }

}
