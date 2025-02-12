package cn.wxiach.autowire.field.circular;

import cn.wxiach.context.ApplicationContext;
import cn.wxiach.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/15
 */
public class FieldBasedAutowireCircularReferenceTest {

    private ApplicationContext context;

    @BeforeEach
    public void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testCircularDependencyWithFieldInjection() {
        BeanA beanA = context.getBean("beanA", BeanA.class);
        Assertions.assertNotNull(beanA.getBeanB());
    }

    @Test
    public void testCircularDependencyWithFieldInjectionWhileAOP() {
        BeanInterface beanC = context.getBean("beanC", BeanInterface.class);
        Assertions.assertNotNull(beanC);
    }
}
