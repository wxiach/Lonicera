package cn.wxiach.aop.framework;

import cn.wxiach.aop.Advisor;

import java.util.List;

/**
 * @author wxiach 2025/1/22
 */
public interface Advised {

    Object getTarget();

    List<Advisor> getAdvisors();

    Class<?>[] getProxiedInterfaces();
}
