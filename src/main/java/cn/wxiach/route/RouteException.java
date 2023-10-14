package cn.wxiach.route;

/**
 * @author wxiach 2023/10/13
 */
public class RouteException extends RuntimeException {

    public RouteException(String message) {
        super(message);
    }

    public RouteException(String message, Throwable cause) {
        super(message, cause);
    }
}
