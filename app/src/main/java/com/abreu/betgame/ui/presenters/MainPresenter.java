package com.abreu.betgame.ui.presenters;

import com.abreu.betgame.ui.screen_contracts.MainScreen;

import javax.inject.Inject;

public class MainPresenter {

    @Inject
    public MainPresenter() {
    }

    public void OnShowCompetitionsButtonClick(MainScreen mainScreen) {
        mainScreen.launchCompetitonsActivity();
    }

}
