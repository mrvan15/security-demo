package com.fan.securitydemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

/**
 * @author shkstart
 * @date 2018/10/21 - 11:41
 */
@Configuration
public class AppSecurityConfigurer extends WebSecurityConfigurerAdapter {

     @Autowired
     private  AuthenticationSuccessHandler authenticationSuccessHandler;


    /**
     * 用户授权操作
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("AppSecurityConfigurer configure() 调用");
        http.authorizeRequests()
                .antMatchers("/login","/css/**","/js/**","/img/*").permitAll()
                .antMatchers("/admin/*").hasAnyRole("ADMIN","DBA")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .usernameParameter("loginname")
                .passwordParameter("password")
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("AppSecurityConfigurer configure() 调用");
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder()
        ).withUser("fkit").password("123456").roles("USER");

        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("admin").password("admin")
                .roles("ADMIN","DBA");
    }


}
