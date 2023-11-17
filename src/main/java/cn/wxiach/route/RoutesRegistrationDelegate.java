package cn.wxiach.route;

import cn.wxiach.annotation.Get;
import cn.wxiach.annotation.Path;
import cn.wxiach.bean.BeanContext;
import cn.wxiach.bean.BeanDefinition;
import cn.wxiach.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wxiach 2023/10/14
 */
public class RoutesRegistrationDelegate {

    public static void registerRoutes(BeanContext context) {
        List<BeanDefinition> beans = context.scanPackage();
        beans.stream().filter(bean -> bean.getType().isAnnotationPresent(Path.class)).forEach(bean -> {
            Method[] methods = bean.getType().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Get.class)) {
                    String clazzPathValue = bean.getType().getAnnotation(Path.class).value();
                    String[] methodPathValues = method.getAnnotation(Get.class).path();
                    for (String methodPathValue : methodPathValues) {
                        String path = resolveAnnotationPathValue(clazzPathValue, methodPathValue);
                        Route route = new Route(path, HttpMethod.GET, bean.getBean(), method);
                        Router.getInstance().addRoute(route);
                    }
                }
            }
        });
    }

    private static String resolveAnnotationPathValue(String clazzPathValue, String methodPathValue) {
        if (clazzPathValue.endsWith("/") && methodPathValue.startsWith("/")) {
            return clazzPathValue + methodPathValue.substring(1);
        }
        if (!clazzPathValue.endsWith("/") && !methodPathValue.startsWith("/")) {
            return clazzPathValue + "/" + methodPathValue;
        }
        return clazzPathValue + methodPathValue;
    }
}
