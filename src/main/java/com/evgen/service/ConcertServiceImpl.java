/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.service;

import com.evgen.dao.ConcertDao;
import com.evgen.domain.ConcertProps;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

/**
 *
 * @author ieshua
 */
public class ConcertServiceImpl implements ConcertService{

    private ConcertDao concertDao;
    private static final Logger LOGGER= LogManager.getLogger();

    public void setConcertDao(ConcertDao concertDao) {
        this.concertDao = concertDao;
    }
    
    
    @Override
    public List<ConcertProps> getAllConcerts() {
        List<ConcertProps> concerts = concertDao.getAllConcerts();
        return concerts;
    }

    @Override
    public ConcertProps getConcertById(Long id) {
        Assert.notNull(id);
        ConcertProps concert = null;
        
        try {
            concert = concertDao.getConcertById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("concert with id = '{}' doesn't exist", id);
        }
        return concert;
    }

    @Override
    public void addConcert(ConcertProps concert) {
        Assert.notNull(concert, "concert can't be null");
        Assert.notNull(concert.getName(),"name of concert can't be null");
        Assert.notNull(concert.getNumberOfSongs(),"number of songs can't be null");
        Assert.notNull(concert.getNumberOfSongsToChoose(), "number of songs to choose can't be null");
        Assert.notNull(concert.getSongs(),"list of songs can't be null");
        Assert.notNull(concert.getFinishVoting(),"field finishVoting can't be null");
        Assert.notNull(concert.getStartVoting(),"field startVoting can't be null");
        
        concertDao.addConcert(concert);
    }

    @Override
    public void removeConcertById(Long id) {
        Assert.notNull(id, "id can't be null");
        
        try {
            ConcertProps existingConcert = getConcertById(id);
        } catch(EmptyResultDataAccessException ex) {
            LOGGER.error("concert with id = '{}' doesn't exist", id);
            throw new IllegalArgumentException("concert with this id doesn't exist");
        }
        
        concertDao.removeConcertById(id);
    }

    @Override
    public void updateConcert(ConcertProps concert) {
        Assert.notNull(concert, "concert can't be null");
        Assert.notNull(concert.getConcertId(),"id of concert can't be null");
        Assert.notNull(concert.getName(),"name of concert can't be null");
        Assert.notNull(concert.getNumberOfSongs(),"number of songs can't be null");
        Assert.notNull(concert.getNumberOfSongsToChoose(), "number of songs to choose can't be null");
        Assert.notNull(concert.getSongs(),"list of songs can't be null");
        Assert.notNull(concert.getFinishVoting(),"field finishVoting can't be null");
        Assert.notNull(concert.getStartVoting(),"field startVoting can't be null");
        
        ConcertProps existingConcert = getConcertById(concert.getConcertId());
        if(existingConcert == null) {
            LOGGER.error("concert with id = '{}' doesn't exist", concert.getConcertId());
            throw new IllegalArgumentException("concert with this id doesn't exist");
        }   
            
        concertDao.updateConcert(concert);
        
    }
    
}
