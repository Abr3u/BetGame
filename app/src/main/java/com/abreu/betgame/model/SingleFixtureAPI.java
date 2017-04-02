package com.abreu.betgame.model;

import com.abreu.betgame.model.pojo.Fixture;
import com.abreu.betgame.model.pojo.FixtureResponse;
import com.abreu.betgame.model.pojo.SingleFixtureResponse;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public class SingleFixtureAPI {

    private final String BASE_URL = "http://api.football-data.org/v1/";

    private interface SingleFixtureService {
        @GET("fixtures/{id}")
        Observable<SingleFixtureResponse> getSingleFixture(@Path("id") int fixtureId);
    }

    public Observable<SingleFixtureResponse> getSingleFixtureObservable(int fixtureId) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SingleFixtureService.class).getSingleFixture(fixtureId).cache();
    }

}
