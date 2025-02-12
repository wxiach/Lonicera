package cn.wxiach.autowire.field.circular;

import cn.wxiach.context.annotation.Component;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author wxiach 2025/2/11
 */
@Aspect
@Component
public class BeanAspect {

    @Before("execution(* cn.wxiach.autowire.field.*.print(..))")
    public void beforeAdvice(JoinPoint point) {
        System.out.println("Before method: " + point.getSignature().getName());
    }

    @After("execution(* cn.wxiach.autowire.field.*.print(..))")
    public void afterAdvice(JoinPoint point) {
        System.out.println("After method: " + point.getSignature().getName());
    }
}
