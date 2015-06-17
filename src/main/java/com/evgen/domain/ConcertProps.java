/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.domain;

import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.joda.time.LocalDateTime;

/**
 *
 * @author ieshua
 */
public class ConcertProps {
    private Long concertId;
    
    @NotNull @Max(Long.MAX_VALUE)
    private Long ticketPrice;
    
    @Size(min = 1, max = 100)
    private String name;
    
    @NotNull @Min(1) @Max(100)
    private Long numberOfSongs;
    
    @NotNull @Min(1) @Max(100)
    private Long numberOfSongsToChoose;
    
    @NotNull @Max(Long.MAX_VALUE)
    private Long numberOfTickets;
    
    @NotNull @Max(Long.MAX_VALUE)
    private Long hours;
    
    @NotNull @Max(Long.MAX_VALUE)
    private Long minutes;
    
    private Timestamp startVoting;
    private Timestamp finishVoting;
    private List<Song> songs;

    public ConcertProps() {
    }

    public ConcertProps(Long concertId, Long ticketPrice, String name, Long numberOfSongs, Long numberOfSongsToChoose, Long numberOfTickets, Timestamp startVoting, Timestamp finishVoting, List<Song> songs) {
        this.concertId = concertId;
        this.ticketPrice = ticketPrice;
        this.name = name;
        this.numberOfSongs = numberOfSongs;
        this.numberOfSongsToChoose = numberOfSongsToChoose;
        this.numberOfTickets = numberOfTickets;
        this.startVoting = startVoting;
        this.finishVoting = finishVoting;
        this.songs = songs;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Long getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(Long numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    public Long getNumberOfSongsToChoose() {
        return numberOfSongsToChoose;
    }

    public void setNumberOfSongsToChoose(Long numberOfSongsToChoose) {
        this.numberOfSongsToChoose = numberOfSongsToChoose;
    }

    public Long getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Long numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Timestamp getStartVoting() {
        return startVoting;
    }

    public void setStartVoting(Timestamp startVoting) {
        this.startVoting = startVoting;
    }

    public Timestamp getFinishVoting() {
        return finishVoting;
    }

    public void setFinishVoting(Timestamp finishVoting) {
        this.finishVoting = finishVoting;
    }
    
    public void setStartVotingLDT(LocalDateTime startVoting) {
        this.startVoting = new Timestamp(startVoting.toDateTime().getMillis());
    }
    
    public LocalDateTime getStartVotingLDT() {
        return new LocalDateTime(startVoting);
    }
    
    public void setFinishVotingLDT(LocalDateTime finishVoting) {
        this.finishVoting = new Timestamp(finishVoting.toDateTime().getMillis());
    }
    
    public LocalDateTime getFinishVotingLDT() {
        return new LocalDateTime(finishVoting);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "ConcertProps{" + "concertId=" + concertId + ", ticketPrice=" + ticketPrice 
                + ", name=" + name + ", numberOfSongs=" + numberOfSongs + ", numberOfSongsToChoose=" 
                + numberOfSongsToChoose + ", numberOfTickets=" + numberOfTickets + ", startDate=" 
                + startVoting+ ", finishDate=" + finishVoting + ", songs=" + songs + "}\n";
    }

    
}
