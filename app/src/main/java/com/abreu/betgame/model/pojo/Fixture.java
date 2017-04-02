package com.abreu.betgame.model.pojo;


import com.google.gson.annotations.Expose;

public class Fixture {
    private Links _links;
    private String date;
    private String status;
    private Integer matchday;
    private String homeTeamName;
    private String awayTeamName;
    private Result result;
    private Odds odds;

    public String getDate() {
        //split into date-time
        String[] dateTime = date.split("T");
        //remove year and split month-day
        String aux = dateTime[0].substring(5);
        String[]monthDay = aux.split("-");
        //remove timezone and secs
        String time = dateTime[1].substring(0,dateTime[1].length()-4);
        return monthDay[1]+"-"+monthDay[0]+" at "+time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMatchday() {
        return matchday;
    }

    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Odds getOdds() {
        return odds;
    }

    public void setOdds(Odds odds) {
        this.odds = odds;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

}