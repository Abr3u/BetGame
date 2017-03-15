package com.abreu.betgame.events;

import com.abreu.betgame.model.pojo.Competition;

import java.util.List;

public class NewCompetitionsEvent {
    List<Competition> competitions;

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public NewCompetitionsEvent(List<Competition> competitions) {
        this.competitions = competitions;
    }
}
