/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.domain;

import java.util.Objects;

/**
 *
 * @author ieshua
 */
public class Vote {
    private Long id;
    private Long concertId;
    private String username;
    private String song;

    public Vote() {
    }

    
    public Vote(Long concertId, String username, String song) {
        this.concertId = concertId;
        this.username = username;
        this.song = song;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vote other = (Vote) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.concertId, other.concertId)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.song, other.song)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "Vote{" + "id=" + id + ", concertId=" + concertId + ", username=" + username + ", song=" + song + '}';
    }
    
    
    
    
}
