package com.abreu.betgame.ui.presenters;

import android.provider.ContactsContract;

import com.abreu.betgame.events.ErrorEvent;
import com.abreu.betgame.events.NewCompetitionsEvent;
import com.abreu.betgame.events.NewMyBetEvent;
import com.abreu.betgame.events.RemovedBetEvent;
import com.abreu.betgame.model.MyBetsDatabaseReference;
import com.abreu.betgame.model.pojo.Bet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by ricar on 01/04/2017.
 */

public class MyBetsPresenter {

    private MyBetsDatabaseReference myBetsRef;

    @Inject
    public MyBetsPresenter(MyBetsDatabaseReference myBetsRef) {
        this.myBetsRef = myBetsRef;
    }

    public void loadBetsFromFirebase(){
        myBetsRef.getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Bet bet = dataSnapshot.getValue(Bet.class);
                EventBus.getDefault().post(new NewMyBetEvent(bet));
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
