/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.dao;

import com.evgen.domain.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

/**
 *
 * @author ieshua
 */
public class ConcertSongDaoImpl implements ConcertSongDao{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    
    
    public class SongMapper implements RowMapper<Song> {

        @Override
        public Song mapRow(ResultSet resultSet, int i) throws SQLException {
            
            Song song = new Song();
            song.setId(resultSet.getLong("song_id"));
            song.setConcertId(resultSet.getLong("concert_id"));
            song.setSong(resultSet.getString("song"));
            
            return song;
        }
    }

    @Override
    public List<Song> getSongsByConcertId(Long concertId) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("concert_id", concertId);
        
        return namedParameterJdbcTemplate.query(
                "select * from concert_songs where concert_id = :concert_id",
                parameters, 
                new SongMapper());
    }

    @Override
    public void addSong(Long concertId, Song song) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("song", song.getSong());
        parameters.put("concert_id", song.getConcertId());
        
        namedParameterJdbcTemplate.update(
                "insert into concert_songs(concert_id, song) values(:concert_id, :song)",
                parameters);
        
    }

    @Override
    public void addSongsList(Long concertId, List<Song> songs) {
        for(int i = 0; i < songs.size(); i++) {
            songs.get(i).setConcertId(concertId);
        }
        
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(songs.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(
                "insert into concert_songs(concert_id, song) values(:concertId, :song)",
                batch);
    }

    @Override
    public void removeSongsByConcertId(Long concertId) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("concert_id", concertId);
        namedParameterJdbcTemplate.update(
                "delete from concert_songs where concert_id = :concert_id", 
                parameters); 
    }
    
}
