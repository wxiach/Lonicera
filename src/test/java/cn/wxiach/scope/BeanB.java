package cn.wxiach.scope;

import cn.wxiach.annotations.Component;
import cn.wxiach.annotations.Scope;
import cn.wxiach.beans.BeanDefinition;

/**
 * @author wxiach 2025/1/10
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BeanB {
}
