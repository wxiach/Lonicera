package cn.wxiach.aop;



/**
 * 
 * @author wxiach 2025/1/21
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
