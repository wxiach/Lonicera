package cn.wxiach.aop;

/**
 * @author wxiach 2025/1/25
 */
public class AopInvocationException extends RuntimeException {

    public AopInvocationException(String message) {
        super(message);
    }

    public AopInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
