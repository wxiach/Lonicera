package cn.wxiach.autowire.construct;

import cn.wxiach.context.annotation.AnnotationConfigApplicationContext;
import cn.wxiach.context.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/15
 */
public class ConstructorBasedAutowireTest {

    private ApplicationContext context;

    @BeforeEach
    public void init() {
        context = new AnnotationConfigApplicationContext("cn.wxiach.autowire.construct");
    }

    @Test
    public void testConstructorAutowired() {
        BeanA beanA = this.context.getBean("beanA", BeanA.class);
        Assertions.assertNotNull(beanA.getBeanB());
    }


}
