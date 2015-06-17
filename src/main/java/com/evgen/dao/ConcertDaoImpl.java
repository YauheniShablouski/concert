/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.dao;

import com.evgen.domain.ConcertProps;
import com.evgen.domain.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.joda.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author ieshua
 */
public class ConcertDaoImpl  implements ConcertDao{
    
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private KeyHolder keyHolder = new GeneratedKeyHolder();
    private ConcertSongDaoImpl songDao;
    
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public void setSongDao(ConcertSongDaoImpl songDao) {
        this.songDao = songDao;
        this.songDao.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
    }
    
    public class ConcertMapper implements RowMapper<ConcertProps> {

        @Override
        public ConcertProps mapRow(ResultSet resultSet, int i) throws SQLException {
            
            ConcertProps concert = new ConcertProps();
            concert.setConcertId(resultSet.getLong("id"));
            concert.setName(resultSet.getString("name"));
            concert.setNumberOfSongs(resultSet.getLong("number_of_songs"));
            concert.setNumberOfSongsToChoose(resultSet.getLong("number_of_songs_to_choose"));
            concert.setNumberOfTickets(resultSet.getLong("number_of_tickets"));
            concert.setTicketPrice(resultSet.getLong("ticket_price"));
            concert.setStartVoting(resultSet.getTimestamp("start_voting"));
            concert.setFinishVoting(resultSet.getTimestamp("finish_voting"));
           
            concert.setSongs(songDao.getSongsByConcertId(concert.getConcertId()));
            
            return concert;
        }
    }
    
    

    @Override
    public List<ConcertProps> getAllConcerts() {
        return namedParameterJdbcTemplate.query(
                "select * from concert_props", 
                new ConcertMapper());
    }

    @Override
    public ConcertProps getConcertById(Long id) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                "select * from concert_props where id = :id", 
                parameters,
                new ConcertMapper());
    }

    @Override
    public void addConcert(ConcertProps concert) {
        SqlParameterSource ps = new BeanPropertySqlParameterSource(concert);
        namedParameterJdbcTemplate.update(
                "insert into concert_props(ticket_price, name, number_of_songs, number_of_songs_to_choose, "
                        + "number_of_tickets, start_voting, finish_voting) values(:ticketPrice, :name, :numberOfSongs, "
                        + ":numberOfSongsToChoose, :numberOfTickets, :startVoting, :finishVoting)", 
                ps, 
                keyHolder);
        
        
        songDao.addSongsList(keyHolder.getKey().longValue(), concert.getSongs());
        
    }

    @Override
    public void removeConcertById(Long id) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("id", id);
        namedParameterJdbcTemplate.update(
                "delete from concert_props where id = :id", 
                parameters); 
        songDao.removeSongsByConcertId(id);
    }

    @Override
    public void updateConcert(ConcertProps concert) {
        SqlParameterSource ps = new BeanPropertySqlParameterSource(concert);
        
        ConcertProps prevConcert = getConcertById(concert.getConcertId()); 
        namedParameterJdbcTemplate.update(
                "update concert_props set ticket_price = :ticketPrice, name = :name, number_of_songs = :numberOfSongs"
                        + ", number_of_songs_to_choose= :numberOfSongsToChoose, number_of_tickets = :numberOfTickets, "
                        + "start_voting= :startVoting, finish_voting = :finishVoting where id = :id", 
                ps);
         
        
        songDao.removeSongsByConcertId(concert.getConcertId());
        songDao.addSongsList(concert.getConcertId(), concert.getSongs());
    }
    
    
    
}
