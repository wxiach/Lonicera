package cn.wxiach.aop;

import cn.wxiach.context.ApplicationContext;
import cn.wxiach.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/20
 */
public class AspectJAutoProxyTest {

    private ApplicationContext context;

    @BeforeEach
    public void init() {
        context = new AnnotationConfigApplicationContext(BeanA.class);
    }

    @Test
    public void testEnableAspectJAutoProxy() {

    }
}
