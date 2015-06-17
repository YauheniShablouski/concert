/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.web;

import com.evgen.dao.ConcertDaoImpl;
import com.evgen.dao.VotingDaoImpl;
import com.evgen.domain.ConcertProps;
import com.evgen.domain.Vote;
import com.evgen.domain.VotingForm;
import com.evgen.service.ConcertServiceImpl;
import com.evgen.service.VotingServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ieshua
 */

@Controller
public class ConcertController {
    
    @RequestMapping(value = "/concert/{id}", method = RequestMethod.GET)
    public ModelAndView initConcert(@PathVariable("id") Long id) {
        ModelAndView model;
        List<Vote> voteList = null;
        List<String> sList = new ArrayList<>();
        List<String> vList = new ArrayList<>();
        Boolean isVoted = false;
        VotingForm form = new VotingForm();
        String login;
        ConcertProps concert;
        
        ApplicationContext context = 
    		new ClassPathXmlApplicationContext("spring-Module.xml");
        ConcertServiceImpl concertServiceImpl = (ConcertServiceImpl) context.getBean("concertService");
        VotingServiceImpl  voteServiceImpl = (VotingServiceImpl) context.getBean("votingService");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        login = auth.getName();
        concert = concertServiceImpl.getConcertById(id);
        LocalDateTime now = new LocalDateTime();
        if(now.compareTo(concert.getFinishVotingLDT()) >= 0) {
            return new ModelAndView("redirect:/concert/" + id + "/result");
        }
        
        if(login != "anonymousUser") {
            try{
                voteList = voteServiceImpl.getVotesByConcertAndUsername(id, login);
            }catch(Exception e) {
                System.out.println("voteList is empty!");
            }
            
            if(voteList != null) {
                isVoted = true;
                for (int i = 0; i < voteList.size(); i++) {
                    vList.add(voteList.get(i).getSong());
                }
                form.setSongs(vList);
            }
        }

        for (int i = 0; i < concert.getSongs().size(); i++) {
            sList.add(concert.getSongs().get(i).getSong());
        }
        
        
        if(concert == null) {
            return new ModelAndView("redirect:/index");
        } else {
            model = new ModelAndView("concert");
            model.addObject("form", form);
            model.addObject("songs", sList);
            model.addObject("concert", concert);
        }
        
        
        return model;
    }
    
    @RequestMapping(value = "/concert/{id}", method = RequestMethod.POST)
    public ModelAndView concertPost(VotingForm form, @PathVariable("id") Long id) {
        String username;
        List<Vote> list = new ArrayList<Vote>();
        ConcertProps concert;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        username = auth.getName();
        
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-Module.xml");
        VotingDaoImpl  voteDaoImpl = (VotingDaoImpl) context.getBean("votingDao");
        ConcertDaoImpl concertDaoImpl = (ConcertDaoImpl) context.getBean("concertDao");
        
        concert = concertDaoImpl.getConcertById(id);
        
        
        
        list = voteDaoImpl.getVotesByConcertAndUsername(id, username);
        
        if(list.size() > 0) {
            voteDaoImpl.removeVotesByConcertAndUsername(id, username);
        }
        
        list.clear();
        if(form.getSongs() != null) {
            for (int i = 0; i < form.getSongs().size(); i++) {
                list.add(new Vote(id, username, form.getSongs().get(i)));
            }
            voteDaoImpl.addVotes(list);
        }
        
        
        return new ModelAndView("redirect:/concert/" + id);
    }
        
    @RequestMapping(value = "/concert/{id}/result", method = RequestMethod.GET) 
    public ModelAndView getResults(@PathVariable("id") Long id) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-Module.xml");
        ConcertServiceImpl concertServiceImpl = (ConcertServiceImpl) context.getBean("concertService");
        VotingServiceImpl  voteServiceImpl = (VotingServiceImpl) context.getBean("votingService");
        
        ModelAndView model = new ModelAndView("results");
        List<Vote> resultList = voteServiceImpl.getResults(id);
        ConcertProps concert = concertServiceImpl.getConcertById(id);
        
        model.addObject("resultList", resultList);
        model.addObject("concert", concert);
        
        return model;
    }
    
    @RequestMapping(value = "/concert/{id}/remove", method = RequestMethod.GET) 
    public ModelAndView removeConcert(@PathVariable("id") Long id) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-Module.xml");
        ConcertServiceImpl concertServiceImpl = (ConcertServiceImpl) context.getBean("concertService");
        VotingServiceImpl  voteServiceImpl = (VotingServiceImpl) context.getBean("votingService");
        
        voteServiceImpl.removeVotesByConcert(id);
        concertServiceImpl.removeConcertById(id);
        
        return new ModelAndView("redirect:/index");
    }
}
