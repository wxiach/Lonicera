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

    /**
     * Correct advice order: @Around -> @Before ->  @AfterReturning -> @After -> @Around
     */
    @Test
    public void testAdviceOrder() {
        people.doSomething("Bob");
    }

    /**
     * Correct advice order: @Around -> @Before -> @AfterThrowing -> @After
     */
    @Test
    public void testAdviceOrderWithException() {
        try {
            people.doSomething(null);
        } catch (Exception ignored) {}
    }
}
