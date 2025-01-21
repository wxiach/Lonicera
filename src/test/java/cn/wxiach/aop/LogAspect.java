package cn.wxiach.aop;

import cn.wxiach.context.annotation.Component;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author wxiach 2025/1/20
 */
@Aspect
@Component
public class LogAspect {

    @Before("execution(void cn.wxiach.aop.User.say())")
    public void beforeLog() {
        System.out.println("=================before log===============");
    }
}
