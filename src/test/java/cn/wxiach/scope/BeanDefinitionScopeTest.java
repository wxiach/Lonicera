package cn.wxiach.scope;

import cn.wxiach.context.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/15
 */
public class BeanDefinitionScopeTest {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    public void init() {
        context = new AnnotationConfigApplicationContext("cn.wxiach.scope");
    }

    @Test
    public void testSingletonScope() {
        Object beanA1 = context.getBean("beanA");
        Object beanA2 = context.getBean("beanA");
        Assertions.assertSame(beanA1, beanA2);
    }

    @Test
    public void testPrototypeScope() {
        Object beanB1 = context.getBean("beanB");
        Object beanB2 =context.getBean("beanB");
        Assertions.assertNotSame(beanB1, beanB2);
    }
}
