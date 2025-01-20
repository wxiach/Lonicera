package cn.wxiach.context;

import cn.wxiach.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/8
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContextWithConfiguration() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Assertions.assertNotNull(context);
    }

    @Test
    public void testApplicationContextWithPackageName() {
        ApplicationContext context = new AnnotationConfigApplicationContext("cn.wxiach.context");
        Assertions.assertNotNull(context);
    }

}
