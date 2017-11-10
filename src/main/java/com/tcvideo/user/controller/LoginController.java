package com.tcvideo.user.controller;

import com.tcvideo.util.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-08-09.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    //跳转到登录页面
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
    //跳转到登录页面
    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView("login/login");
        return mav;
    }
    //跳转到error页面
    @RequestMapping("/error")
    public ModelAndView error()  {
        ModelAndView mav = new ModelAndView("error/error");
        return mav;
    }
    //跳转到403
    @RequestMapping("/403")
    public ModelAndView error403() {
        ModelAndView mav = new ModelAndView("error/403");
        return mav;
    }

    //跳转到登录成功页面
    @RequestMapping("/loginsuccess")
    public ModelAndView loginsuccess(){
        ModelAndView mav = new ModelAndView("loginsuccess");
        return mav;
    }

    /**
     * 验证用户名和密码
     */
    @RequestMapping(value="/checkLogin",method= RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String checkLogin(HttpServletResponse response, String username, String password) throws Exception {
        System.out.println("checkLogin");
        Map<String, Object> result = new HashMap<String, Object>();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject  = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            token.setRememberMe(true);
            try {
                subject.login(token);//验证角色和权限
                result.put("status", 200);
                result.put("message","登陆成功");
                result.put("back_url", "/backEnd/index");
            } catch (IncorrectCredentialsException ice) {
                // 捕获密码错误异常
                result.put("status", 10001);
                result.put("message", "密码错误");
            } catch (UnknownAccountException uae) {
                // 捕获未知用户名异常
                result.put("status", 10002);
                result.put("message","用户名错误");
            } catch (ExcessiveAttemptsException eae) {
                // 捕获错误登录过多的异常
                result.put("status", 10003);
                result.put("message", "登录次数过多");
            }
        }
        return JsonUtil.toJson(result);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value="/logout")
    @ResponseBody
    public String logout() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return JsonUtil.toJson(result);
    }
}

