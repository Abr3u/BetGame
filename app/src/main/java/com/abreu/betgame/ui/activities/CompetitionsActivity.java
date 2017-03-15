package com.abreu.betgame.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abreu.betgame.R;
import com.abreu.betgame.dagger.DaggerInjector;
import com.abreu.betgame.events.ErrorEvent;
import com.abreu.betgame.events.NewCompetitionsEvent;
import com.abreu.betgame.ui.adapters.CompetitionsListAdapter;
import com.abreu.betgame.ui.decorators.DividerItemDecoration;
import com.abreu.betgame.ui.presenters.CompetitionsPresenter;
import com.abreu.betgame.ui.screen_contracts.CompetitionsScreen;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompetitionsActivity extends AppCompatActivity implements CompetitionsScreen {

    @Inject
    CompetitionsPresenter competitionsPresenter;

    @BindView(R.id.posts_recycler_view)
    RecyclerView competitionsRecyclerView;

    @BindView(R.id.error_view)
    TextView errorView;

    CompetitionsListAdapter competitionsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts);

        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);

        initRecyclerView();
        competitionsPresenter.loadCompetitonsFromAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void initRecyclerView() {
        competitionsRecyclerView.setHasFixedSize(true);
        competitionsRecyclerView.setLayoutManager(new LinearLayoutManager(competitionsRecyclerView.getContext()));
        competitionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        competitionsRecyclerView.addItemDecoration(new DividerItemDecoration(competitionsRecyclerView.getContext(),
                                                                      DividerItemDecoration.VERTICAL_LIST));

        competitionsListAdapter = new CompetitionsListAdapter(this);
        competitionsRecyclerView.setAdapter(competitionsListAdapter);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewCompetitionsEvent newCompetitionsEvent) {
        hideError();
        competitionsListAdapter.addCompetitions(newCompetitionsEvent.getCompetitions());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent errorEvent) {
        showError();
    }

    private void hideError() {
        competitionsRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError() {
        competitionsRecyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
