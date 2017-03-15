package com.abreu.betgame.model.pojo;

import java.util.List;

public class FixtureResponse {
    private List<Fixture> fixtures;

    public List<Fixture> getFixtureList() {
        return fixtures;
    }

    public void setFixtureList(List<Fixture> fixtureList) {
        this.fixtures = fixtureList;
    }

}