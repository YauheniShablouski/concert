/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.dao;

import com.evgen.domain.Vote;
import java.util.List;

/**
 *
 * @author ieshua
 */
public interface VotingDao {
    void addVotes(List<Vote> votes);
    List<Vote> getVotesByConcertAndUsername(Long concertId, String username);
    List<Vote> getVotesByConcert(Long concertId);
    void removeVotesByConcert(Long concertId);
    void removeVotesByConcertAndUsername(Long concertId, String username);
    
}
