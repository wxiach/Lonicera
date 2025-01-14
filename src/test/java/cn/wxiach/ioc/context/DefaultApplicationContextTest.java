package cn.wxiach.ioc.context;

import cn.wxiach.context.DefaultApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/8
 */
public class DefaultApplicationContextTest {

    private DefaultApplicationContext context;

    @BeforeEach
    public void init() {
        context = new DefaultApplicationContext(AppConfig.class);
    }

    @Test
    public void testRegisterBean() {
        BeanA beanA = (BeanA)context.getBean("beanA");
        Assertions.assertNotNull(beanA);
        BeanB beanB = context.getBean("beanB", BeanB.class);
        Assertions.assertNotNull(beanB);
    }

    @Test
    public void testScopeOfBean() {
        BeanB beanB1 = context.getBean("beanB", BeanB.class);
        BeanB beanB2 = context.getBean("beanB", BeanB.class);
        Assertions.assertSame(beanB1, beanB2);
        BeanC beanC1 = context.getBean("beanC", BeanC.class);
        BeanC beanC2 =context.getBean("beanC", BeanC.class);
        Assertions.assertNotSame(beanC1, beanC2);
    }

    @Test
    public void testBeanRegistrationWithConfiguration() {
        BeanD beanD1 = context.getBean("beanD", BeanD.class);
        BeanD beanD2 = context.getBean("appConfig", AppConfig.class).beanD();
        Assertions.assertSame(beanD1, beanD2);
    }
}
