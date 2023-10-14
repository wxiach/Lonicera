package cn.wxiach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author wxiach 2023/10/6
 */

public class LoniceraTest {

    private Lonicera app;

    @BeforeEach
    public void setup() {
        app = Lonicera.run(LoniceraTest.class);
    }

    @Test
    public void test() {
    }
}
