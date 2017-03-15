package com.abreu.betgame.model;

import com.abreu.betgame.model.pojo.Competition;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public class CompetitionsAPI {

    private final String BASE_URL = "http://api.football-data.org/v1/";

    private interface CompetitionService {
        @GET("competitions")
        Observable<List<Competition>> getCompetitionsList();
    }

    private Observable<List<Competition>> competitionsObservable = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CompetitionService.class).getCompetitionsList().cache();


    public Observable<List<Competition>> getCompetitionsObservable() {
        return competitionsObservable;
    }

}
