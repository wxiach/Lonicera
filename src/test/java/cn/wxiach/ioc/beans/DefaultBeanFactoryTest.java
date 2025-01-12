package cn.wxiach.ioc.beans;

import cn.wxiach.beans.BeanDefinition;
import cn.wxiach.beans.DefaultBeanFactory;
import cn.wxiach.context.ApplicationContext;
import cn.wxiach.context.DefaultApplicationContext;
import cn.wxiach.ioc.context.BeanC;
import cn.wxiach.ioc.context.DefaultApplicationContextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/11
 */
public class DefaultBeanFactoryTest {

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void init() throws Exception {
        this.beanFactory = new DefaultBeanFactory();
        BeanDefinition beanDefinitionA = new BeanDefinition(BeanA.class);
        this.beanFactory.registerBeanDefinition("beanA", beanDefinitionA);
        BeanDefinition beanDefinitionB = new BeanDefinition(BeanB.class);
        this.beanFactory.registerBeanDefinition("beanB", beanDefinitionB);
        BeanDefinition beanDefinitionC = new BeanDefinition(BeanC.class);
        this.beanFactory.registerBeanDefinition("beanC", beanDefinitionC);
    }

    @Test
    public void testConstructorAutowired() {
        BeanA beanA = this.beanFactory.getBean("beanA", BeanA.class);
        Assertions.assertNotNull(beanA.getBeanB());
    }
}
