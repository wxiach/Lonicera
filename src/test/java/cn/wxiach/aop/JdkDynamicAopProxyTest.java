package cn.wxiach.aop;

import cn.wxiach.aop.jdk.People;
import cn.wxiach.context.ApplicationContext;
import cn.wxiach.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/20
 */
public class JdkDynamicAopProxyTest {

    private People people;

    @BeforeEach
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        people = context.getBean("bob", People.class);
    }

    @Test
    public void testAdviceOrder() {
        System.out.println("\n=====Advice Order With Normal");
        people.doSomething("Bob");

        /**
         *
         * Console correct output
         *
         * =====Advice Order With Normal
         * Around method: cn.wxiach.aop.aspectj.MethodInvocationProceedingJoinPoint$TargetMethodSignature@47c4ecdc
         * Before method: cn.wxiach.aop.aspectj.MethodInvocationProceedingJoinPoint$TargetMethodSignature@42f33b5d
         * AfterReturning method with result [ Processed: Bob]
         * After method: cn.wxiach.aop.aspectj.MethodInvocationProceedingJoinPoint$TargetMethodSignature@5c8504fd
         * Around method: cn.wxiach.aop.aspectj.MethodInvocationProceedingJoinPoint$TargetMethodSignature@47c4ecdc
         */
    }

    @Test
    public void testAdviceOrderWithException() {
        System.out.println("\n=====Advice Order With Exception");
        try {
            people.doSomething(null);
        } catch (Exception ignored) {
        }
        /**
         * Console correct output
         *
         * =====Advice Order With Exception
         * Around method: cn.wxiach.aop.aspectj.MethodInvocationProceedingJoinPoint$TargetMethodSignature@18c5069b
         * Before method: cn.wxiach.aop.aspectj.MethodInvocationProceedingJoinPoint$TargetMethodSignature@3a0d172f
         * AfterThrowing method with ex [ java.lang.IllegalArgumentException: Input cannot be null]
         * After method: cn.wxiach.aop.aspectj.MethodInvocationProceedingJoinPoint$TargetMethodSignature@14dda234
         *
         */
    }
}
