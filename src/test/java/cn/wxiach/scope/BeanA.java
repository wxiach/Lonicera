package cn.wxiach.scope;

import cn.wxiach.context.annotation.Component;
import cn.wxiach.context.annotation.Scope;
import cn.wxiach.beans.config.BeanDefinition;

/**
 * @author wxiach 2025/1/10
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class BeanA {
}
