package com.abreu.betgame.ui.presenters;

import com.abreu.betgame.MyApplicationContext;
import com.abreu.betgame.events.ErrorEvent;
import com.abreu.betgame.events.NewCompetitionFixturesEvent;
import com.abreu.betgame.model.CompetitionFixturesAPI;
import com.abreu.betgame.model.MyBetsDatabaseReference;
import com.abreu.betgame.model.pojo.Bet;
import com.abreu.betgame.model.pojo.Fixture;
import com.abreu.betgame.model.pojo.FixtureResponse;
import com.abreu.betgame.ui.activities.MyBetsActivity;
import com.abreu.betgame.utility.CollectionsUtility;
import com.abreu.betgame.utility.IPredicate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CompetitionFixturesPresenter {

    private MyBetsDatabaseReference myBetsDatabaseRef;
    private CompetitionFixturesAPI competitionFixturesAPI;

    @Inject
    public CompetitionFixturesPresenter(CompetitionFixturesAPI competitionFixturesAPI,MyBetsDatabaseReference myBetsDatabaseRef) {
        this.competitionFixturesAPI = competitionFixturesAPI;
        this.myBetsDatabaseRef = myBetsDatabaseRef;
    }

    public void loadCompetitonFixturesFromAPI(int competitionId) {
        competitionFixturesAPI.getCompetitionFixturesObservable(competitionId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                                                                                     .mainThread())
                .subscribe(new Subscriber<FixtureResponse>() {
                    @Override
                    public void onNext(FixtureResponse newFixtures) {

                        IPredicate<Fixture> active = new IPredicate<Fixture>() {
                            public boolean apply(Fixture fixture) {
                                return fixture.getStatus().equals("SCHEDULED") ||
                                        fixture.getStatus().equals("TIMED");
                            }
                        };
                        List<Fixture> filteredFixtures = new ArrayList<Fixture>(
                                CollectionsUtility.filter(newFixtures.getFixtureList(),active));
                        EventBus.getDefault().post(new NewCompetitionFixturesEvent(filteredFixtures));
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent());
                    }

                });
    }


    public void betOnTeam(Fixture fixture,String bet){
        String betOdd = "";
        switch(bet){
            case "home":
                betOdd = fixture.getOdds().getHomeWin().toString();
                break;
            case "draw":
                betOdd = fixture.getOdds().getDraw().toString();
                break;
            case "away":
                betOdd = fixture.getOdds().getAwayWin().toString();
                break;
        }
        Bet newBet = new Bet(fixture.getHomeTeamName(),fixture.getAwayTeamName(),fixture.getDate(),bet,betOdd,null);
        String[] tokenArray = fixture.get_links().getSelf().getHref().split("/");
        String ref = tokenArray[tokenArray.length-1];
        myBetsDatabaseRef.getReference().child(ref).setValue(newBet.toMap());
    }
}
