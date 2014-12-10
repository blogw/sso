package com.github.blogw.adshiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class LoginWithAD {
	public static final String userName = "wyg@redlotus.com.cn";
    public static final String password = "Redlotus01";

    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = (SecurityManager) factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        
        try {
            currentUser.login(token);
            System.out.println("We've authenticated! :)");
        } catch (AuthenticationException e) {
            System.out.println("We did not authenticate :(");
            e.printStackTrace();
        }
        if (currentUser.hasRole("adviser")) {
            System.out.println("We're authorized! :)");
        } else {
            System.out.println("We are not authorized :(");
        }
        if (currentUser.hasRole("business")) {
            System.out.println("We're authorized! :)");
        } else {
            System.out.println("We are not authorized :(");
        }
    }
}
