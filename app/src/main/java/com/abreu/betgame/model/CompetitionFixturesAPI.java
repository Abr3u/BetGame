package com.abreu.betgame.model;

import com.abreu.betgame.model.pojo.FixtureResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public class CompetitionFixturesAPI {

    private final String BASE_URL = "http://api.football-data.org/v1/";

    private interface CompetitionFixturesService {
        @GET("competitions/{id}/fixtures?timeFrame=n7")
        Observable<FixtureResponse> getCompetitionFixtures(@Path("id") int competitionId);
    }

    public Observable<FixtureResponse> getCompetitionFixturesObservable(int competitionId) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CompetitionFixturesService.class).getCompetitionFixtures(competitionId).cache();
    }

}
