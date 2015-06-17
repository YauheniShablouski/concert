/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.web;


import com.evgen.dao.ConcertDaoImpl;
import com.evgen.domain.ConcertProps;
import com.evgen.domain.ListOfSongsForm;
import com.evgen.domain.Song;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ieshua
 */
@Controller
public class AddConcertController {
    
   ConcertProps props;

    @RequestMapping( value = "/addConcert", method = RequestMethod.GET)
    public ModelAndView launchAddForm(Model m) {
        return new ModelAndView("addConcert", "props", new ConcertProps());
    }
    
    @RequestMapping(value = "/addConcert", method = RequestMethod.POST)
    public String setProps(@Valid @ModelAttribute("props") ConcertProps props, BindingResult result) {
        
        if(result.hasErrors()) {
            return "addConcert";
        }
        
        LocalDateTime ldt = new LocalDateTime();
        props.setStartVotingLDT(ldt);
        
        props.setFinishVotingLDT(ldt
                                .plusHours(props.getHours().intValue())
                                .plusMinutes(props.getMinutes().intValue())
                            );
        
        ArrayList<Song> songs = new ArrayList<>();
        
        for (int i = 0; i < props.getNumberOfSongs(); i++) {
            songs.add(new Song(""));
        }
        
        props.setSongs(songs);
        
        this.props = props;
        return "redirect:/addSongs";
    }
    
    @RequestMapping(value = "/addSongs", method = RequestMethod.GET)
    public ModelAndView addSongs(@RequestParam(value = "error", required = false) String error) {    
        ListOfSongsForm form = new ListOfSongsForm();
        ModelAndView model = new ModelAndView("listOfSongs");
        
        if(error != null) {
            model.addObject("error", error);
        }
        form.setListOfSongs(props.getSongs());
        
        model.addObject("form", form);
        return model;  
    }
    
    @RequestMapping(value = "/addSongs", method = RequestMethod.POST)
    public String addSongs(@ModelAttribute("form") ListOfSongsForm form, Model m) {
        
        
        for (int i = 0; i < form.getListOfSongs().size(); i++) {
            if(form.getListOfSongs().get(i).getSong().length()>100) {
                m.addAttribute("error", "length of song must be less then 100");
                return "listOfSongs";
                
            }
            if(form.getListOfSongs().get(i).getSong().equals("")) {
                form.getListOfSongs().remove(i);
                i--;
            }
        }
        
        props.setSongs(form.getListOfSongs());
        
        ApplicationContext context = 
    		new ClassPathXmlApplicationContext("spring-Module.xml");
        ConcertDaoImpl concertDaoImpl = (ConcertDaoImpl) context.getBean("concertDao");
        concertDaoImpl.addConcert(props);
        

        
        return "redirect:/index";
    }
      
}
