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
import com.abreu.betgame.events.NewMyBetEvent;
import com.abreu.betgame.events.RemovedBetEvent;
import com.abreu.betgame.ui.adapters.MyBetsAdapter;
import com.abreu.betgame.ui.decorators.DividerItemDecoration;
import com.abreu.betgame.ui.presenters.MyBetsPresenter;
import com.abreu.betgame.ui.screen_contracts.MyBetsScreen;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBetsActivity extends AppCompatActivity implements MyBetsScreen {

    @Inject
    MyBetsPresenter myBetsPresenter;

    @BindView(R.id.posts_recycler_view)
    RecyclerView betsRecyclerView;

    @BindView(R.id.error_view)
    TextView errorView;

    MyBetsAdapter myBetsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts);

        DaggerInjector.get().inject(this);
        ButterKnife.bind(this);

        initRecyclerView();
        myBetsPresenter.loadBetsFromFirebase();
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
        betsRecyclerView.setHasFixedSize(true);
        betsRecyclerView.setLayoutManager(new LinearLayoutManager(betsRecyclerView.getContext()));
        betsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        betsRecyclerView.addItemDecoration(new DividerItemDecoration(betsRecyclerView.getContext(),
                                                                      DividerItemDecoration.VERTICAL_LIST));

        myBetsAdapter = new MyBetsAdapter(this);
        betsRecyclerView.setAdapter(myBetsAdapter);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewMyBetEvent myBetEvent) {
        hideError();
        myBetsAdapter.addBet(myBetEvent.getBet());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RemovedBetEvent betEvent) {
        hideError();
        myBetsAdapter.removeBet(betEvent.getBet());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent errorEvent) {
        showError();
    }

    private void hideError() {
        betsRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showError() {
        betsRecyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
