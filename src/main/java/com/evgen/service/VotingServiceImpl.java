/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.service;

import com.evgen.dao.VotingDao;
import com.evgen.domain.ConcertProps;
import com.evgen.domain.User;
import com.evgen.domain.Vote;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

/**
 *
 * @author ieshua
 */
public class VotingServiceImpl implements VotingService{
    private VotingDao votingDao;
    private UserService userService;
    private ConcertService concertService;
    private static final Logger LOGGER= LogManager.getLogger();
    
    public void setVotingDao(VotingDao votingDao) {
        this.votingDao = votingDao;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setConcertService(ConcertService concertService) {
        this.concertService = concertService;
    }
    
    
    
    
    
    @Override
    public void addVotes(List<Vote> votes) {
        Assert.notNull(votes, "list of votes cant'be null");
        
        votingDao.addVotes(votes);        
    }

    @Override
    public List<Vote> getVotesByConcertAndUsername(Long concertId, String username) {
        Assert.notNull(concertId, "id of concert can't be null");
        Assert.notNull(username, "username can't be null");
        
        User user = userService.getUserByUserName(username);
        if(user == null) {
            LOGGER.error("user with login = '{}' doesn't exist", username);
            throw new IllegalArgumentException("user with this login doesn't exist");
        }
        
        ConcertProps concert = concertService.getConcertById(concertId);
        if(concert == null) {
            LOGGER.error("Concert with id = '{}' doesn't exist", concertId);
            throw new IllegalArgumentException("concert with this id doesn't exist");
        }
        
        return votingDao.getVotesByConcertAndUsername(concertId, username);
        
    }

    @Override
    public List<Vote> getVotesByConcert(Long concertId) {
        Assert.notNull(concertId, "id of concert can't be null");
        
        ConcertProps concert = concertService.getConcertById(concertId);
        if(concert == null) {
            LOGGER.error("Concert with id = '{}' doesn't exist", concertId);
            throw new IllegalArgumentException("concert with this id doesn't exist");
        }
        
        return votingDao.getVotesByConcert(concertId);
    }

    @Override
    public void removeVotesByConcert(Long concertId) {
        Assert.notNull(concertId, "id of concert can't be null");
        
        ConcertProps concert = concertService.getConcertById(concertId);
        if(concert == null) {
            LOGGER.error("Concert with id = '{}' doesn't exist", concertId);
            throw new IllegalArgumentException("concert with this id doesn't exist");
        }
        
        votingDao.removeVotesByConcert(concertId);
    }

    @Override
    public void removeVotesByConcertAndUsername(Long concertId, String username) {
        Assert.notNull(concertId, "id of concert can't be null");
        Assert.notNull(username, "username can't be null");
        
        User user = userService.getUserByUserName(username);
        if(user == null) {
            LOGGER.error("user with login = '{}' doesn't exist", username);
            throw new IllegalArgumentException("user with this login doesn't exist");
        }
        
        ConcertProps concert = concertService.getConcertById(concertId);
        if(concert == null) {
            LOGGER.error("Concert with id = '{}' doesn't exist", concertId);
            throw new IllegalArgumentException("concert with this id doesn't exist");
        }
        
        votingDao.removeVotesByConcertAndUsername(concertId, username);
    }

    @Override
    public List<Vote> getResults(Long concertId) {
        Assert.notNull(concertId, "id of concert can't be null");
        
        ConcertProps concert = concertService.getConcertById(concertId);
        if(concert == null) {
            LOGGER.error("Concert with id = '{}' doesn't exist", concertId);
            throw new IllegalArgumentException("concert with this id doesn't exist");
        }
        
        List<Vote> voteList = votingDao.getVotesByConcert(concertId);
        Map<String,Integer> map = new HashMap<String, Integer>();
        List<Vote> resultList = new ArrayList<>();
        Integer tempValue;
        if(!voteList.isEmpty()) {
            for(int i = 0; i < concert.getSongs().size(); i++) {
                map.put(concert.getSongs().get(i).getSong(), 0);
            }
            
            for(int i = 0; i < voteList.size(); i++) {
                tempValue = map.get(voteList.get(i).getSong());
                tempValue++;
                map.put(voteList.get(i).getSong(), tempValue);
            }
            
            List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(map.entrySet());
            Collections.sort(entryList, new Comparator<Entry<String, Integer>>() {
                @Override
                public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                    int v1 = e1.getValue(); 
                    int v2 = e2.getValue();
                    return (v1 < v2) ? 1 : (v1 == v2) ? 0 : -1;
                } 
            });
            
            Entry<String, Integer> e;
            for(int i = 0; i < concert.getNumberOfSongsToChoose(); i++) {
                e = entryList.get(i);
                resultList.add(new Vote(concertId, "admin", e.getKey()));
            }
        }
            
        return resultList;
    }
    
}
