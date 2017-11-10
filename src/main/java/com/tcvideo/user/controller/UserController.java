package com.tcvideo.user.controller;

import com.tcvideo.core.exception.TCExcption;
import com.tcvideo.user.entity.User;
import com.tcvideo.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017-07-25.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //service类
    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @RequiresRoles("管理员")
    @RequiresPermissions("/admin/index")
    public ModelAndView findUser(String id){
        Subject subject = SecurityUtils.getSubject();
//        if(subject.hasRole("管理员")) {
//            //有权限
//            System.out.println(111);
//        } else {
//            //无权限
//            System.out.println(222);
//        }
//        SecurityUtils.setSecurityManager(securityManager); // 注入SecurityManager
        ModelAndView modelAndView = new ModelAndView();
        //调用service方法得到用户列表
        User user =null;
        try {
            user = userService.get(Integer.valueOf(id));
        }catch (TCExcption d){
            System.out.println(d.getMessage());
        }
        //将得到的用户列表内容添加到ModelAndView中
        modelAndView.addObject("users",user);
        //设置响应的jsp视图
        modelAndView.setViewName("userList");
        return modelAndView;
    }


    @RequestMapping("/validate")
    @RequiresRoles("admin")
    public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name=request.getParameter("name");
        //List<User> users = userService.get(name);
        PrintWriter out = response.getWriter();
        out.print(name);
    }


}
