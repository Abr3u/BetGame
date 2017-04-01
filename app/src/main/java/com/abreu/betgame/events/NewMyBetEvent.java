package com.abreu.betgame.events;

import com.abreu.betgame.model.pojo.Bet;


/**
 * Created by ricar on 01/04/2017.
 */

public class NewMyBetEvent {
    private Bet bet;

    public NewMyBetEvent(Bet bet) {
        this.bet = bet;
    }

    public Bet getBet() {
        return bet;
    }

}
