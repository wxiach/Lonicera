package cn.wxiach;

import cn.wxiach.bean.BeanContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2023/10/12
 */
public class BeanContextTest {

    private BeanContext context;

    @BeforeEach
    public void setup() {
        context = new BeanContext("cn.wxiach");
    }

    @Test
    public void testScanPackage() {
        context.scanPackage();
    }
}
