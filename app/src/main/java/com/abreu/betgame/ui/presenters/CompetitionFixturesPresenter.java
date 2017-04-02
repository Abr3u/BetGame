package com.abreu.betgame.ui.presenters;

import com.abreu.betgame.MyApplicationContext;
import com.abreu.betgame.events.ErrorEvent;
import com.abreu.betgame.events.NewCompetitionFixturesEvent;
import com.abreu.betgame.model.CompetitionFixturesAPI;
import com.abreu.betgame.model.pojo.Bet;
import com.abreu.betgame.model.pojo.Fixture;
import com.abreu.betgame.model.pojo.FixtureResponse;
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

    private static final String BETS_REF = "/bets/";
    private final DatabaseReference myBetsDatabaseRef;

    CompetitionFixturesAPI competitionFixturesAPI;

    @Inject
    public CompetitionFixturesPresenter(CompetitionFixturesAPI competitionFixturesAPI) {
        this.competitionFixturesAPI = competitionFixturesAPI;
        String myId = MyApplicationContext.getInstance().getFirebaseUser().getUid();
        this.myBetsDatabaseRef = FirebaseDatabase.getInstance().getReference(BETS_REF).child(myId);
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
                                        fixture.getStatus().equals("TIMED") ? true : false;
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
        String betTeam = "";
        switch(bet){
            case "home":
                betOdd = fixture.getOdds().getHomeWin().toString();
                betTeam = fixture.getHomeTeamName();
                break;
            case "draw":
                betOdd = fixture.getOdds().getDraw().toString();
                betTeam = "draw";
                break;
            case "away":
                betOdd = fixture.getOdds().getAwayWin().toString();
                betTeam = fixture.getAwayTeamName();
                break;
        }
        Bet newBet = new Bet(fixture.getHomeTeamName(),fixture.getAwayTeamName(),fixture.getDate(),betTeam,betOdd,null);
        String[] tokenArray = fixture.get_links().getSelf().getHref().split("/");
        String ref = tokenArray[tokenArray.length-1];
        myBetsDatabaseRef.child(ref).setValue(newBet.toMap());
    }
}
