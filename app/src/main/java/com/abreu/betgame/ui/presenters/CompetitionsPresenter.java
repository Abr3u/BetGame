package com.abreu.betgame.ui.presenters;

import com.abreu.betgame.events.ErrorEvent;
import com.abreu.betgame.events.NewCompetitionsEvent;
import com.abreu.betgame.model.CompetitionsAPI;
import com.abreu.betgame.model.pojo.Competition;
import com.abreu.betgame.utility.CollectionsUtility;
import com.abreu.betgame.utility.IPredicate;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CompetitionsPresenter {
    CompetitionsAPI competitionsAPI;
    List<String> mLeagueCodes;

    @Inject
    public CompetitionsPresenter(CompetitionsAPI competitionsAPI) {
        this.competitionsAPI = competitionsAPI;
        mLeagueCodes = new ArrayList<String>(){{
            add("PL");add("BL1");add("SA");add("PD");add("CDR");add("FL1");add("DED");add("PPL");add("GSL");add("CL");add("EL");
        }};
    }

    public void loadCompetitonsFromAPI() {
        competitionsAPI.getCompetitionsObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                                                                                     .mainThread())
                .subscribe(new Subscriber<List<Competition>>() {
                    @Override
                    public void onNext(List<Competition> newCompetitions) {
                        IPredicate<Competition> myLeagues = new IPredicate<Competition>() {
                            public boolean apply(Competition competition) {
                                return mLeagueCodes.contains(competition.getLeague());
                            }
                        };
                        List<Competition> filteredCompetitions = new ArrayList<Competition>(
                                CollectionsUtility.filter(newCompetitions,myLeagues));
                        EventBus.getDefault().post(new NewCompetitionsEvent(filteredCompetitions));
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent());
                    }

                });
    }

}
