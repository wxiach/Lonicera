package cn.wxiach.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author wxiach 2025/1/24
 */
public interface ProxyMethodInvocation extends MethodInvocation {
    Object getProxy();
}
