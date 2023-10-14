package cn.wxiach.bean;

/**
 * @author wxiach 2023/10/12
 */
public class BeansException extends RuntimeException {

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
