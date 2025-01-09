package cn.wxiach;

import cn.wxiach.annotations.ComponentScan;
import cn.wxiach.annotations.Configuration;
import cn.wxiach.context.DefaultApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2025/1/8
 */
@Configuration
@ComponentScan
public class DefaultApplicationContextTest {

    @Test
    public void testRegisterBean() {
        DefaultApplicationContext context = new DefaultApplicationContext(this.getClass());
        User user = (User)context.getBean("user");
        String username = user.getUserName();
        Assertions.assertEquals("wxiach", username);
    }
}
