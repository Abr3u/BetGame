package com.abreu.betgame.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.abreu.betgame.MyApplicationContext;
import com.abreu.betgame.R;
import com.abreu.betgame.dagger.DaggerInjector;
import com.abreu.betgame.model.pojo.User;
import com.abreu.betgame.ui.presenters.MainPresenter;
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

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainScreen{

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);

        mainPresenter.authenticateIfNeeded(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.show_posts_button)
    public void onShowCompetitionsButtonClick() {
        mainPresenter.OnShowCompetitionsButtonClick(this);
    }

    @OnClick(R.id.my_bets_button)
    public void onShowMyBetsButtonClicked(){
        mainPresenter.OnShowMyBetsButtonClicked(this);
    }

    @Override
    public void launchCompetitonsActivity() {
        Intent intent = new Intent(this, CompetitionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchMyBetsActivity() {
        Intent intent = new Intent(this, MyBetsActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
