package com.abreu.betgame.model;

import com.abreu.betgame.MyApplicationContext;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ricar on 01/04/2017.
 */

public class MyBetsDatabaseReference {

    private static final String BETS_REF = "/bets/";

    public DatabaseReference getReference(){
        String myId = MyApplicationContext.getInstance().getFirebaseUser().getUid();
        return FirebaseDatabase.getInstance().getReference(BETS_REF).child(myId);
    }
}
