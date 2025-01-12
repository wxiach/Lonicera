package cn.wxiach.ioc.beans;

import cn.wxiach.annotations.ComponentScan;
import cn.wxiach.annotations.Configuration;
import cn.wxiach.context.DefaultApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/11
 */
@Configuration
@ComponentScan
public class DefaultBeanFactoryTest {

    private DefaultApplicationContext context;

    @BeforeEach
    public void init() throws Exception {
        context = new DefaultApplicationContext(DefaultBeanFactoryTest.class);
    }

    @Test
    public void testConstructorAutowired() {
        BeanA beanA = this.context.getBean("beanA", BeanA.class);
        Assertions.assertNotNull(beanA.getBeanB());
    }

    @Test
    public void testFieldAutowired() {
        BeanB beanB = this.context.getBean("beanB", BeanB.class);
        Assertions.assertNotNull(beanB.getBeanC());
    }

    @Test
    public void testCircularDependencyWithFieldInjection() {
        BeanD beanD = this.context.getBean("beanD", BeanD.class);
        Assertions.assertNotNull(beanD);
    }
}
