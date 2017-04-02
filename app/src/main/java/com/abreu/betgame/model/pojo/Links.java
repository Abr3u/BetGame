package com.abreu.betgame.model.pojo;

public class Links {
    private BaseLink self;
    private BaseLink competition;
    private BaseLink homeTeam;
    private BaseLink awayTeam;

    public BaseLink getSelf() {
        return self;
    }

    public void setSelf(BaseLink selfLink) {
        this.self = selfLink;
    }

    public BaseLink getCompetition() {
        return competition;
    }

    public void setCompetition(BaseLink competition) {
        this.competition = competition;
    }

    public BaseLink getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(BaseLink homeTeamLink) {
        this.homeTeam = homeTeamLink;
    }

    public BaseLink getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(BaseLink awayTeamLink) {
        this.awayTeam = awayTeamLink;
    }

}
