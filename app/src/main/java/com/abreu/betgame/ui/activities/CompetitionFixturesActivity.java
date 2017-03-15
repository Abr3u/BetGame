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
import com.abreu.betgame.events.NewCompetitionFixturesEvent;
import com.abreu.betgame.ui.adapters.CompetitionFixturesListAdapter;
import com.abreu.betgame.ui.decorators.DividerItemDecoration;
import com.abreu.betgame.ui.presenters.CompetitionFixturesPresenter;
import com.abreu.betgame.ui.screen_contracts.CompetitionFixturesScreen;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ricar on 10/03/2017.
 */

public class CompetitionFixturesActivity extends AppCompatActivity implements CompetitionFixturesScreen {

    @Inject
    CompetitionFixturesPresenter competitionFixturesPresenter;

    @BindView(R.id.posts_recycler_view)
    RecyclerView competitionFixturesRecyclerView;

    @BindView(R.id.error_view)
    TextView errorView;

    CompetitionFixturesListAdapter competitionFixturesListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts);

        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);

        int competitionId = getIntent().getIntExtra("competitionId",426);

        initRecyclerView();
        competitionFixturesPresenter.loadCompetitonFixturesFromAPI(competitionId);
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
        competitionFixturesRecyclerView.setHasFixedSize(true);
        competitionFixturesRecyclerView.setLayoutManager(new LinearLayoutManager(competitionFixturesRecyclerView.getContext()));
        competitionFixturesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        competitionFixturesRecyclerView.addItemDecoration(new DividerItemDecoration(competitionFixturesRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL_LIST));

        competitionFixturesListAdapter = new CompetitionFixturesListAdapter(this);
        competitionFixturesRecyclerView.setAdapter(competitionFixturesListAdapter);
    }


    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewCompetitionFixturesEvent newCompetitionFixturesEvent) {
        hideError();
        competitionFixturesListAdapter.addFixtures(newCompetitionFixturesEvent.getCompetitionFixtures());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent errorEvent) {
        showError();
    }

    private void hideError() {
        competitionFixturesRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError() {
        competitionFixturesRecyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
