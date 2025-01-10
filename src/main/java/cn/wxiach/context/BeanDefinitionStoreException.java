package cn.wxiach.context;

/**
 * @author wxiach 2025/1/10
 */
public class BeanDefinitionStoreException extends RuntimeException {
    public BeanDefinitionStoreException(String message) {
        super(message);
    }

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
