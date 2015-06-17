
package com.evgen.domain;

import java.util.List;
import java.util.Objects;

public class VotingForm {
    private List<String> songs;

    public VotingForm() {
    }
    

    public VotingForm(List<String> songs) {
        this.songs = songs;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "VotingForm{" + "songs=" + songs + '}';
    }

        
}
