package com.abreu.betgame.model.pojo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Bet {

    public Boolean wonBet;
    public String homeTeam;
    public String awayTeam;
    public String date;
    public String bet;
    public String betOdd;

    public Bet(String homeTeam, String awayTeam, String date, String bet, String betOdd,Boolean wonBet) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
        this.bet = bet;
        this.betOdd = betOdd;
        this.wonBet = wonBet;
    }

    public Bet() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Bet))
            return false;
        Bet other = (Bet) obj;
        return this.homeTeam.equals(other.homeTeam) &&
                this.awayTeam.equals(other.awayTeam) &&
                this.date.equals(other.date);
    }

    @Override
    public int hashCode() {
        return homeTeam.hashCode()+awayTeam.hashCode()+date.hashCode();
    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("homeTeam", this.homeTeam);
        result.put("awayTeam", this.awayTeam);
        result.put("date", this.date);
        result.put("bet", this.bet);
        result.put("betOdd", this.betOdd);
        result.put("wonBet",this.wonBet);
        return result;
    }
}
