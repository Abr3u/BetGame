package com.abreu.betgame.ui.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.abreu.betgame.MyApplicationContext;
import com.abreu.betgame.model.pojo.User;
import com.abreu.betgame.ui.activities.MainActivity;
import com.abreu.betgame.ui.activities.SignInActivity;
import com.abreu.betgame.ui.screen_contracts.MainScreen;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class MainPresenter {

    // Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    public MainPresenter() {
    }

    public void OnShowCompetitionsButtonClick(MainScreen mainScreen) {
        mainScreen.launchCompetitonsActivity();
    }

    public void authenticateIfNeeded(MainScreen mainScreen){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setupGoogle();
        setupAuth(mainScreen);
    }

    private void setupGoogle() {
        Context context = MyApplicationContext.getInstance();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    private void setupAuth(MainScreen mainScreen) {
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Log.d("yyy", "user " + mFirebaseUser);
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            mainScreen.launchSignInActivity();
        } else {
            MyApplicationContext.getInstance().setFirebaseUser(mFirebaseUser);
            writeUserIfNeeded(mFirebaseUser);
        }
    }

    private void writeUserIfNeeded(final FirebaseUser firebaseUser) {
        final String userId = MyApplicationContext.getInstance().getFirebaseUser().getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            User newUser = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail());
                            writeNewUser(newUser);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("yyy", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    private void writeNewUser(User user) {
        Map<String, Object> userValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        String uid = MyApplicationContext.getInstance().getFirebaseUser().getUid();
        childUpdates.put("/users/" + uid, userValues);

        mDatabase.updateChildren(childUpdates);
    }


    public void OnShowMyBetsButtonClicked(MainScreen mainScreen) {
        mainScreen.launchMyBetsActivity();
    }
}
