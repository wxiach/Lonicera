package cn.wxiach.beans;

/**
 * @author wxiach 2025/1/9
 */
public class BeansCreateException extends RuntimeException{
    public BeansCreateException(String message) {
        super(message);
    }

    public BeansCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
