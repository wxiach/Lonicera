package cn.wxiach.autowire.field;

import cn.wxiach.context.annotation.AnnotationConfigApplicationContext;
import cn.wxiach.context.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/15
 */
public class FieldBasedAutowireTest {

    private ApplicationContext context;

    @BeforeEach
    public void init() {
        context = new AnnotationConfigApplicationContext("cn.wxiach.autowire.field");
    }

    @Test
    public void testFieldAutowired() {
        BeanA beanA = context.getBean("beanA", BeanA.class);
        Assertions.assertNotNull(beanA.getBeanB());
    }

    @Test
    public void testCircularDependencyWithFieldInjection() {
        BeanC beanC = context.getBean("beanC", BeanC.class);
        Assertions.assertNotNull(beanC.getBeanD());
    }
}
