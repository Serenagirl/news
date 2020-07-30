package com.zhongruan.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    // 1. 继承HandlerInterceptorAdapter类
    // 2. 重写preHandle方法
    // 3. 指定拦截器使用的位置

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 当session中没有user对象时 则重定向 到 登录页面
        if (request.getSession().getAttribute("user") == null) {
            // 重定向
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }

}
