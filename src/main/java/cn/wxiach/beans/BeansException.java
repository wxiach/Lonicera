package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/9
 */
public class BeansException extends RuntimeException {
    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
