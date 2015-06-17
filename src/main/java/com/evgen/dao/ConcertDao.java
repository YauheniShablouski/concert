/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.dao;

import com.evgen.domain.ConcertProps;
import com.evgen.domain.Song;

import java.util.List;

/**
 *
 * @author ieshua
 */
public interface ConcertDao {
    public List<ConcertProps> getAllConcerts();
    public ConcertProps getConcertById(Long id);
    public void addConcert(ConcertProps concert);
    public void removeConcertById(Long id);
    public void updateConcert(ConcertProps concert);
}
