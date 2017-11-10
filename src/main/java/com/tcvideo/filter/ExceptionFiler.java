package com.tcvideo.filter;



import com.tcvideo.core.exception.TCExcption;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 业务异常过滤器
 */
public class ExceptionFiler implements Filter {

    private String errorPage;//跳转的错误信息页面


    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        //捕获抛出的业务异常
        try {
            chain.doFilter(req, res);
        } catch (RuntimeException e) {
            if(e instanceof TCExcption){//业务异常
                request.setAttribute("TCExcption", e);//存储业务异常信息类
                request.getRequestDispatcher(errorPage).forward(request, response);//跳转到信息提示页面！！
            }
            e.printStackTrace();
        }
    }
    //初始化读取你配置的提示页面路径
    public void init(FilterConfig config) throws ServletException {
        //读取错误信息提示页面路径
        errorPage = config.getInitParameter("errorPage");
        if(null==errorPage || "".equals(errorPage)){
            throw new RuntimeException("没有配置错误信息跳转页面,请再web.xml中进行配置\n<init-param>\n<param-name>errorPage</param-name>\n<param-value>/error.jsp</param-value>\n </init-param>\n路径可以是你自己设定的任何有效路径页面！！");
            //System.out.println("没有配置错误信息跳转页面");
        }
    }

}
