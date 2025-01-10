package cn.wxiach;

import cn.wxiach.app.A;
import cn.wxiach.app.B;
import cn.wxiach.app.C;
import cn.wxiach.app.config.AppConfig;
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
        A a = (A)context.getBean("a");
        Assertions.assertNotNull(a);
        B b = context.getBean("b", B.class);
        Assertions.assertNotNull(b);
    }

    @Test
    public void testScopeOfBean() {
        B b1 = context.getBean("b", B.class);
        B b2 = context.getBean("b", B.class);
        Assertions.assertEquals(b1, b2);
        C c1 = (C)context.getBean("c");
        C c2 = (C)context.getBean("c");
        Assertions.assertNotEquals(c1, c2);
    }
}
