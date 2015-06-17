
package com.evgen.domain;


public class Song {
    private Long id;
    private Long concertId;
    
    private String song;

    public Song() {
    }

    public Song(String song) {
        this.song = song;
    }

    public Song(Long concertId, String song) {
        this.concertId = concertId;
        this.song = song;
    }

    public Song(Long id, Long concertId, String song) {
        this.id = id;
        this.concertId = concertId;
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
        final Song other = (Song) obj;
        
        if(!this.song.equals(other.song)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Song{" + "id=" + id + ", concertId=" + concertId + ", song=" + song + '}';
    }
    

    
    
    
}
