package cn.wxiach;

import cn.wxiach.app.A;
import cn.wxiach.app.C;
import cn.wxiach.app.config.AppConfig;
import cn.wxiach.context.DefaultApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/8
 */
public class DefaultApplicationContextTest {

    @Test
    public void testRegisterBean() {
        DefaultApplicationContext context = new DefaultApplicationContext(AppConfig.class);
        A a = (A)context.getBean("testa");
        Assertions.assertNotNull(a);
        C c = context.getBean("c", C.class);
        Assertions.assertNotNull(c);
    }
}
