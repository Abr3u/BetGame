package com.abreu.betgame.dagger.modules;

import com.abreu.betgame.model.CompetitionFixturesAPI;
import com.abreu.betgame.model.CompetitionsAPI;
import com.abreu.betgame.model.MyBetsDatabaseReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    CompetitionsAPI provideCompetitionsApi() {
        return new CompetitionsAPI();
    }

    @Provides
    @Singleton
    CompetitionFixturesAPI provideCompetitionFixturesApi() {return new CompetitionFixturesAPI();
    }

    @Provides
    @Singleton
    MyBetsDatabaseReference provideMyBetsDatabaseReference(){return new MyBetsDatabaseReference();}


}
