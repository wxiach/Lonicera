package cn.wxiach.aop.aspectj;


import cn.wxiach.aop.Pointcut;

/**
 * @author wxiach 2025/1/21
 */
public class AspectJExpressionPointcut implements Pointcut {

    private final String pointcutExpression;

    public AspectJExpressionPointcut(String pointcutExpression) {
        this.pointcutExpression = pointcutExpression;
    }
}
