package cn.wxiach.ioc.context;

import cn.wxiach.annotations.Bean;
import cn.wxiach.annotations.ComponentScan;
import cn.wxiach.annotations.Configuration;

/**
 * @author wxiach 2025/1/10
 */
@Configuration
@ComponentScan("cn.wxiach.ioc.context")
public class AppConfig {

    @Bean
    public BeanD beanD() {
        return new BeanD();
    }

}
