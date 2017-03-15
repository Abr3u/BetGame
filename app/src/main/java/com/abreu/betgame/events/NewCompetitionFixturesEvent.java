package com.abreu.betgame.events;

import com.abreu.betgame.model.pojo.Fixture;

import java.util.List;

/**
 * Created by ricar on 10/03/2017.
 */

public class NewCompetitionFixturesEvent {

    List<Fixture> competitionFixtures;

    public List<Fixture> getCompetitionFixtures() {
        return competitionFixtures;
    }


    public NewCompetitionFixturesEvent(List<Fixture> fixtures) {
        this.competitionFixtures = fixtures;
    }
}
