/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.dao;

import com.evgen.domain.Vote;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

/**
 *
 * @author ieshua
 */
public class VotingDaoImpl implements VotingDao{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    class VoteMapper implements RowMapper<Vote> {

        @Override
        public Vote mapRow(ResultSet rs, int i) throws SQLException {
            Vote vote = new Vote();
            vote.setId(rs.getLong("id"));
            vote.setConcertId(rs.getLong("concert_id"));
            vote.setUsername(rs.getString("username"));
            vote.setSong(rs.getString("song"));
            return vote;
        }
        
    }

    @Override
    public void addVotes(List<Vote> votes) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(votes.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(
                "insert into votes(concert_id, username, song) values(:concertId, :username, :song)",
                batch);
    }

    @Override
    public List<Vote> getVotesByConcertAndUsername(Long concertId, String username) {
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("concert_id", concertId);
        parameters.put("username", username);        
        return namedParameterJdbcTemplate.query(
                "select * from votes where concert_id = :concert_id and username = :username",
                parameters, 
                new VoteMapper());
    }

    @Override
    public List<Vote> getVotesByConcert(Long concertId) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("concert_id", concertId);
        
        return namedParameterJdbcTemplate.query(
                "select * from votes where concert_id = :concert_id",
                parameters, 
                new VoteMapper());
    }

    @Override
    public void removeVotesByConcert(Long concertId) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("concert_id", concertId);
        namedParameterJdbcTemplate.update(
                "delete from votes where concert_id = :concert_id", 
                parameters); 
    }

    @Override
    public void removeVotesByConcertAndUsername(Long concertId, String username) {
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("concert_id", concertId);
        parameters.put("username", username);
        namedParameterJdbcTemplate.update(
                "delete from votes where concert_id = :concert_id and username = :username", 
                parameters); 
    }

    
    
    
}
