package cn.wxiach.route;

import cn.wxiach.http.HttpMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.wxiach.util.RouteUtils.generateRouteKey;

/**
 * @author wxiach 2023/10/8
 */
public class Router {

    private static Router Instance;
    private final Map<String, Route> routes = new ConcurrentHashMap<>(256);

    private Router() {}

    public static Router getInstance() {
        if (Instance == null) {
            Instance = new Router();
        }
        return Instance;
    }

    public void addRoute(Route route) {
        String routeKey = generateRouteKey(route.path(), route.method().name());
        if (routes.containsKey(routeKey)) {
            throw new RouteException("routeKey: " + routeKey + " already exists");
        }
        routes.put(routeKey, route);
    }

    public Route getRoute(String routeKey) {
        return routes.get(routeKey);
    }

}
