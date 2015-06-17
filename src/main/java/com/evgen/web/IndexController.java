/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.web;
import com.evgen.dao.ConcertDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import com.evgen.dao.UserDaoImpl;
import com.evgen.dao.VotingDaoImpl;
import com.evgen.domain.ConcertProps;
import com.evgen.domain.User;
import com.evgen.domain.Vote;
import com.evgen.domain.VotingForm;
import com.evgen.service.ConcertServiceImpl;
import com.evgen.service.UserServiceImpl;
import com.evgen.service.VotingServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.ws.BindingProvider;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author ieshua
 */
@Controller
public class IndexController {
    
    //@Autowired(required=true)
    //private UserServiceImpl userService;
    
    @RequestMapping("/")
    public String toIndex() {
        return "redirect:/index";
    }
    
    @RequestMapping("/index")
    public ModelAndView initIndex() {
        ApplicationContext context = 
    		new ClassPathXmlApplicationContext("spring-Module.xml");
        ConcertServiceImpl concertServiceImpl = (ConcertServiceImpl) context.getBean("concertService");
        
        ModelAndView model = new ModelAndView("index");
        
        List<ConcertProps> concerts = concertServiceImpl.getAllConcerts();
        Collections.reverse(concerts);
       
        model.addObject("concerts", concerts);
        return model;
    }
    
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
                
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("signIn");

		return model;

	}
    
    
      
    @RequestMapping(value="/loginError", method = RequestMethod.GET)
    public ModelAndView loginError() {
        
        ModelAndView model = new ModelAndView();
        model.addObject("error", "Invalid username and password!");
        model.setViewName("signIn");
        return model;
    }
    
    @RequestMapping("/logout")
    public String logoutAction() {
        SecurityContextHolder.clearContext();
        return "redirect:/index";
    }
}
