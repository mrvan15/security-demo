package com.fan.securitydemo.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author shkstart
 * @date 2018/10/21 - 11:35
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
