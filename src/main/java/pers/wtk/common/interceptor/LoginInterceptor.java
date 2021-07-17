package pers.wtk.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.wtk.common.enums.AppAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wtk
 * @description 登录拦截器
 * @date 2021-06-19
 */
public class LoginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger("root");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取资源请求路径
        String uri = request.getRequestURI();

        HttpSession session = request.getSession();
        //如果用户已登录也放行
        if (session.getAttribute(AppAttribute.LOGGED) != null) {
            return true;
        }
        //未登录，跳转到登录页面
        logger.trace("检查用户的登录状态 未登录，拦截");
        //用户没有登录挑战到登录页面
        response.sendRedirect("index.html");
        return false;
    }
}
