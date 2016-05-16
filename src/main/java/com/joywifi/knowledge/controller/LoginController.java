package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.security.FormAuthenticationCaptchaFilter;
import com.joywifi.knowledge.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getPrincipal();
        if (object == null) {
            return "login";
        }
        return "redirect:/";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationCaptchaFilter.DEFAULT_USERNAME_PARAM) String userName,
                       Model model) {
        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getPrincipal();
        if (object == null) {
            model.addAttribute(FormAuthenticationCaptchaFilter.DEFAULT_USERNAME_PARAM, userName);
            model.addAttribute("msg", "用户名或密码错误！");
            return "login";
        }
        return "redirect:/";
    }

}
