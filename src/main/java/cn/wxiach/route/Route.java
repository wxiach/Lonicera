package cn.wxiach.route;


import cn.wxiach.http.HttpMethod;

import java.lang.reflect.Method;

/**
 * @author wxiach 2023/10/8
 */
public record Route(String path, HttpMethod method, Object target, Method action) {}
