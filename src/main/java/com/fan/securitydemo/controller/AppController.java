package com.fan.securitydemo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shkstart
 * @date 2018/10/21 - 23:58
 */

@Controller
public class AppController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/home")
    public String homePage(Model model){
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());

        return "home";
    }

    @RequestMapping(value = "/login")
    public String login(){

        return "login";
    }

    @RequestMapping(value = "/admin")
    public String adminPage(Model model){
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());
        return "admin";
    }

    @RequestMapping(value = "/dba")
    public String adbPage(Model model){
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());
        return "adb";
    }

    @RequestMapping(value = "/accessDenied")
    public String accessDeniedPage(Model model){
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());
        return "accessDenied";
    }

    @RequestMapping(value = "logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse respons){
        //获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //如果用户信息不为空,注销
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout
                    (request, respons, authentication);
        }

        // 重定向到loin页面
       return "redirect:/login?logout";
    }


    /**
     * 获取当前用户名称
     * @return
     */
    private String getUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username = " + username);

        return username;
    }

    /**
     * 获得当前用户权限
     * @return
     */
    private String getAuthority(){
        //获得Authentication对象,表示用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //将角色名称添加到List集合
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority a: authentication.getAuthorities()) {
            roles.add(a.getAuthority());
        }

        System.out.println("role = " +roles);

        return roles.toString();
    }
}
