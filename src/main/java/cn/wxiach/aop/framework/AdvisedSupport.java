package cn.wxiach.aop.framework;

import cn.wxiach.aop.Advisor;

import java.util.List;

/**
 * @author wxiach 2025/1/22
 */
public class AdvisedSupport implements Advised {

    private final Object target;

    private final List<Advisor> advisors;

    private final Class<?>[] interfaces;

    public AdvisedSupport(Object target, List<Advisor> advisors, Class<?>[] interfaces) {
        this.target = target;
        this.interfaces = interfaces;
        this.advisors = advisors;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public List<Advisor> getAdvisors() {
        return advisors;
    }

    @Override
    public Class<?>[] getProxiedInterfaces() {
        return interfaces;
    }
}
