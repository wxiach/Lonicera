package cn.wxiach.registry;

import cn.wxiach.context.annotation.Bean;
import cn.wxiach.context.annotation.Configuration;

/**
 * @author wxiach 2025/1/15
 */
@Configuration
public class AppConfig {

    @Bean
    public BeanA beanA() {
        return new BeanA();
    }
}
