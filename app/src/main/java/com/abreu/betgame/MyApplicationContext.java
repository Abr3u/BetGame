package com.abreu.betgame;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

public class MyApplicationContext extends Application {

    private FirebaseUser firebaseUser;
    private static MyApplicationContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = (MyApplicationContext) getApplicationContext();
    }

    public static MyApplicationContext getInstance() {return instance;}

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

}
