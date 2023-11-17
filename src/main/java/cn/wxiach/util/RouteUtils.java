package cn.wxiach.util;

import cn.wxiach.http.HttpMethod;

/**
 * @author wxiach 2023/11/11
 */
public class RouteUtils {

    public static final String ROUTE_KEY_SEPARATOR = ":";

    public static String generateRouteKey(String path, String method) {
        return path + ROUTE_KEY_SEPARATOR + method;
    }
}
