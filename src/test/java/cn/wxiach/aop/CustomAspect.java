package cn.wxiach.aop;

import cn.wxiach.context.annotation.Component;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @author wxiach 2025/1/20
 */
@Aspect
@Component
public class CustomAspect {

    @Before("execution(* cn.wxiach.aop.jdk.Bob.*(..))")
    public void beforeAdvice(JoinPoint point) {
        System.out.println("Before method: " + point.getSignature());
    }

    @After("execution(* cn.wxiach.aop.jdk.Bob.*(..))")
    public void afterAdvice(JoinPoint point) {
        System.out.println("After method: " + point.getSignature());
    }

    @Around("execution(* cn.wxiach.aop.jdk.Bob.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint point) throws Throwable {
        System.out.println("Around method: " + point.getSignature());
        Object returnValue = point.proceed();
        System.out.println("Around method: " + point.getSignature());
        return returnValue;
    }

    @AfterReturning(value = "execution(* cn.wxiach.aop.jdk.Bob.*(..)", returning = "result")
    public void afterReturningAdvice(Object result) {
        System.out.println("AfterReturning method with result [ " + result + "] ");
    }

    @AfterThrowing(value = "execution(* cn.wxiach.aop.jdk.Bob.*(..)", throwing = "ex")
    public void afterThrowingAdvice(Throwable ex) {
        System.out.println("AfterThrowing method with ex [ " + ex + "] ");
    }

}
