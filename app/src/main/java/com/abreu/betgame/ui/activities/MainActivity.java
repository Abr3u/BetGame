package com.abreu.betgame.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abreu.betgame.R;
import com.abreu.betgame.dagger.DaggerInjector;
import com.abreu.betgame.ui.presenters.MainPresenter;
import com.abreu.betgame.ui.screen_contracts.MainScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainScreen {

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.show_posts_button)
    public void onListSampleButtonClick() {
        mainPresenter.OnShowCompetitionsButtonClick(this);
    }

    @Override
    public void launchCompetitonsActivity() {
        Intent intent = new Intent(this, CompetitionsActivity.class);
        startActivity(intent);
    }

}
