package cn.wxiach.aop.framework;

import cn.wxiach.aop.Advisor;

import java.util.List;

/**
 * @author wxiach 2025/1/22
 */
public class ProxyFactory extends AdvisedSupport {

    public ProxyFactory(Object target, List<Advisor> advisors, Class<?>[] interfaces) {
        super(target, advisors, interfaces);
    }

    public Object getProxy() {
        return createAopProxy(this).getProxy();
    }

    private AopProxy createAopProxy(AdvisedSupport config) {
        if (getProxiedInterfaces().length != 0) {
            return new JdkDynamicAopProxy(config);
        }
        return new CglibAopProxy();
    }

}
