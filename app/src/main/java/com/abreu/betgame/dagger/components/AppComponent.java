package com.abreu.betgame.dagger.components;

import com.abreu.betgame.dagger.modules.AppModule;
import com.abreu.betgame.ui.activities.CompetitionFixturesActivity;
import com.abreu.betgame.ui.activities.MainActivity;
import com.abreu.betgame.ui.activities.CompetitionsActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(CompetitionsActivity activity);
    void inject(CompetitionFixturesActivity activity);
}
