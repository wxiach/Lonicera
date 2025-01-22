package cn.wxiach.aop.support;

import cn.wxiach.aop.Pointcut;
import cn.wxiach.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @author wxiach 2025/1/21
 */
public class DefaultPointcutAdvisor implements PointcutAdvisor {

    private Pointcut pointcut;

    private Advice advice;

    public DefaultPointcutAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
