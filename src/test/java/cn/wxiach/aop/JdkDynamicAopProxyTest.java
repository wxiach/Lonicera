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
    public void test() {
        people.doSomething();

        System.out.println("=======================");

        try {
            people.doSomethingElse(null);
        } catch (Exception e) {
        }
    }
}
