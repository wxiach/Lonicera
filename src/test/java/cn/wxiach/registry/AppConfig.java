package cn.wxiach.registry;

import cn.wxiach.annotations.Bean;
import cn.wxiach.annotations.Configuration;

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
