/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.service;

import com.evgen.domain.ConcertProps;
import java.util.List;

/**
 *
 * @author ieshua
 */
public interface ConcertService {
    public List<ConcertProps> getAllConcerts();
    public ConcertProps getConcertById(Long id);
    public void addConcert(ConcertProps concert);
    public void removeConcertById(Long id);
    public void updateConcert(ConcertProps concert);
}
