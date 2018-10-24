package com.fan.securitydemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author shkstart
 * @date 2018/10/21 - 23:27
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //spring security通过RedirectStrategy对象负责所有重定向事务
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 重写handle方法,方法中通过redirectStrategy对象重定向到指定的URL
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        /**
         *
         * 通过determineTargetUrl方法返回需要跳转的URL
         */
        String targetUrl = this.determineTargetUrl(authentication);

        // 重定向请求到指定的URL
        redirectStrategy.sendRedirect(request, response, targetUrl);

       super.handle(request, response, authentication);
    }

    /**
     * 从Authentication对象中提取当前登录的角色,并根据其角色返回适当的url
     * @param authentication
     * @return
     */
    protected String determineTargetUrl(Authentication authentication){
        String url = "";

        //获取 当前登录用户的角色权限集合
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<>();

        //将角色名称添加到List集合
        for (GrantedAuthority a: authorities) {
            roles.add(a.getAuthority());
        }

        //判断不同角色跳转到不同的URL
        if(isAdmin(roles)){
            url = "/admin";
        }else  if (isUser(roles)){
            url = "/home";
        }else{
            url = "/accessDenied";
        }

        System.out.println("url = " + url);

       return url;
    }

    private boolean isAdmin(List<String> roles){
        if(roles.contains("ROLE_ADMIN")){
            return  true;
        }
        return false;
    }

    private boolean isUser(List<String> roles){
        if(roles.contains("ROLE_USER")){
            return true;
        }
        return false;
    }

    @Override
    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
