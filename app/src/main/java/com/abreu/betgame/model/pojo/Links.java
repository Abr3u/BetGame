package com.abreu.betgame.model.pojo;

public class Links {
    private SelfLink self;
    private CompetitionLink competition;
    private HomeTeamLink homeTeam;
    private AwayTeamLink awayTeam;

    public SelfLink getSelf() {
        return self;
    }

    public void setSelf(SelfLink selfLink) {
        this.self = selfLink;
    }

    public CompetitionLink getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionLink competition) {
        this.competition = competition;
    }

    public HomeTeamLink getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(HomeTeamLink homeTeamLink) {
        this.homeTeam = homeTeamLink;
    }

    public AwayTeamLink getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(AwayTeamLink awayTeamLink) {
        this.awayTeam = awayTeamLink;
    }

}
