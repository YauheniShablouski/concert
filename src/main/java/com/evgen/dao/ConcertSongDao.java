/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.dao;

import com.evgen.domain.Song;
import java.util.List;

/**
 *
 * @author ieshua
 */
public interface ConcertSongDao {
    List<Song> getSongsByConcertId(Long concertId);
    void addSong(Long concertId, Song song);
    void addSongsList(Long concertId, List<Song> songs);
    void removeSongsByConcertId(Long concertId);
}
