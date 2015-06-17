package com.evgen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.evgen.domain.User;
import com.evgen.service.UserServiceImpl;

import javax.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/registration")
public class RegistrationController {
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String registration(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("user", new User());
        if(error != null) {
            model.addAttribute("error", error);
        }
        return "registration";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String getForm(@Valid User user, BindingResult result, Model model) {

        
        if(result.hasErrors()) {
            return "registration";
        }
        
        ApplicationContext context = 
    		new ClassPathXmlApplicationContext("spring-Module.xml");
        UserServiceImpl userService = (UserServiceImpl) context.getBean("userService");
        
        User existingUser = userService.getUserByUserName(user.getUsername());
        
        if(existingUser != null) {
            model.addAttribute("error", "user with this login already exist");
            return "registration";
        }
        
        user.setEnabled(1);
        userService.addUser(user);
        return "redirect:/index";
    }

}