package com.jing.system.permission;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	

	@RequestMapping(value="/login/auth",method=RequestMethod.POST)  
    public String doLogin(HttpServletRequest request){  
        String username = request.getParameter("userName");  
        String pwd = request.getParameter("password");  
        String captcha = request.getParameter("captcha");  
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd, captcha);   
        Subject currentUser = SecurityUtils.getSubject();   
        currentUser.login(token);    
        return "redirect:/static/index.html";  
    }  
}
