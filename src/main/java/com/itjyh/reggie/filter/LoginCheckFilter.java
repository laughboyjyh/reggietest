package com.itjyh.reggie.filter;

import com.alibaba.fastjson.JSON;

import com.itjyh.reggie.common.BaseContext;
import com.itjyh.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//过滤器实现
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//1.获取本次请求URI
        String requstURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
               "/backend/**",
                "/front/**",


        };
        //2.判断本次请求是否需要处理
        boolean check = check(urls, requstURI);
        //3.不需要处理,直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requstURI);
            filterChain.doFilter(request, response);
            return;
        }
        //4.判断是否登录,如果已登录,则放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录,id为:{}", request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }
//5.如果未登录,返回未登录结果,通过输出流响应JSON数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
//        log.info("拦截到请求:{}", request.getRequestURI());
//        filterChain.doFilter(request, response);

    }

    /*
     * 路径匹配,检查本次请求是否放行
     * */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
