package com.abreu.betgame.dagger;

import com.abreu.betgame.dagger.components.AppComponent;
import com.abreu.betgame.dagger.components.DaggerAppComponent;
import com.abreu.betgame.dagger.modules.AppModule;

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
