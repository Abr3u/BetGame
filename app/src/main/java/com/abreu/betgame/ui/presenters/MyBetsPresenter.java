package com.abreu.betgame.ui.presenters;

import android.util.Log;

import com.abreu.betgame.events.ErrorEvent;
import com.abreu.betgame.events.NewMyBetEvent;
import com.abreu.betgame.events.RemovedBetEvent;
import com.abreu.betgame.model.MyBetsDatabaseReference;
import com.abreu.betgame.model.SingleFixtureAPI;
import com.abreu.betgame.model.pojo.Bet;
import com.abreu.betgame.model.pojo.Result;
import com.abreu.betgame.model.pojo.SingleFixtureResponse;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ricar on 01/04/2017.
 */

public class MyBetsPresenter {

    private MyBetsDatabaseReference myBetsRef;
    private SingleFixtureAPI singleFixtureAPI;

    @Inject
    public MyBetsPresenter(MyBetsDatabaseReference myBetsRef, SingleFixtureAPI singleFixtureAPI) {
        this.myBetsRef = myBetsRef;
        this.singleFixtureAPI = singleFixtureAPI;
    }

    public void loadBetsFromFirebase() {
        myBetsRef.getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Bet bet = dataSnapshot.getValue(Bet.class);
                if (bet.wonBet != null) {
                    Log.d("ggg","ja sabia resultado");
                    EventBus.getDefault().post(new NewMyBetEvent(bet));
                } else {
                    //check if bet was won
                    final String matchKey = dataSnapshot.getKey();
                    Log.d("ggg", "matchKey " + matchKey);
                    singleFixtureAPI.getSingleFixtureObservable(Integer.parseInt(matchKey)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                            .mainThread())
                            .subscribe(new Subscriber<SingleFixtureResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("ggg", "ERRO");
                                    Log.e("ggg", "TS " + e.toString());
                                }

                                @Override
                                public void onNext(SingleFixtureResponse fixture) {
                                    Result result = fixture.getFixture().getResult();
                                    Integer goalsHome = result.getGoalsHomeTeam();
                                    Integer goalsAway = result.getGoalsAwayTeam();
                                    if (goalsAway != null && goalsHome != null) {
                                        String matchResult;
                                        if (goalsAway > goalsHome) {
                                            matchResult = "away";
                                        } else if (goalsHome > goalsAway) {
                                            matchResult = "home";
                                        } else {
                                            matchResult = "draw";
                                        }
                                        Map<String, Object> childUpdates = new HashMap<>();
                                        Bet closedBet;
                                        if (bet.bet.equals(matchResult)) {
                                            closedBet = new Bet(bet.homeTeam, bet.awayTeam, bet.date, bet.bet, bet.betOdd, true);
                                            childUpdates.put("wonBet", true);
                                        } else {
                                            closedBet = new Bet(bet.homeTeam, bet.awayTeam, bet.date, bet.bet, bet.betOdd, false);
                                            childUpdates.put("wonBet", false);
                                        }
                                        Log.d("ggg","soube resultado");
                                        EventBus.getDefault().post(new NewMyBetEvent(closedBet));
                                        myBetsRef.getReference().child(matchKey).updateChildren(childUpdates);
                                    } else {
                                        Log.d("ggg","nao sei resultado ainda");
                                        EventBus.getDefault().post(new NewMyBetEvent(bet));
                                    }
                                }
                            });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Bet bet = dataSnapshot.getValue(Bet.class);
                EventBus.getDefault().post(new NewMyBetEvent(bet));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Bet bet = dataSnapshot.getValue(Bet.class);
                EventBus.getDefault().post(new RemovedBetEvent(bet));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Bet bet = dataSnapshot.getValue(Bet.class);
                EventBus.getDefault().post(new NewMyBetEvent(bet));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new ErrorEvent());
            }
        });
    }

}
