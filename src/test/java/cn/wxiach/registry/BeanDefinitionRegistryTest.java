package cn.wxiach.registry;

import cn.wxiach.context.AnnotationConfigApplicationContext;
import cn.wxiach.context.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/15
 */
public class BeanDefinitionRegistryTest {

    private ApplicationContext context;

    @BeforeEach
    public void init() {
        context = new AnnotationConfigApplicationContext("cn.wxiach.registry");
    }

    @Test
    public void testRegistryWithBeanAnnotation() {
        Object beanA1 = context.getBean("beanA");
        Assertions.assertNotNull(beanA1);
    }
}
