package com.abreu.betgame.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abreu.betgame.R;
import com.abreu.betgame.model.pojo.Fixture;
import com.abreu.betgame.model.pojo.Odds;
import com.abreu.betgame.ui.activities.CompetitionFixturesActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompetitionFixturesListAdapter extends RecyclerView.Adapter<CompetitionFixturesListAdapter.ViewHolder> {

    private final Context mContext;
    private List<Fixture> fixtures;

    public CompetitionFixturesListAdapter(Context context) {
        this.mContext = context;
        this.fixtures = new ArrayList<>();
    }

    public void addFixtures(List<Fixture> fixtures) {
        this.fixtures.addAll(fixtures);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_row_item_odds, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.date.setText(fixtures.get(position).getDate());
        viewHolder.home.setText(fixtures.get(position).getHomeTeamName());
        viewHolder.away.setText(fixtures.get(position).getAwayTeamName());

        Odds odds = fixtures.get(position).getOdds();
        if(odds != null){
            viewHolder.homeOdd.setText(odds.getHomeWin().toString());
            viewHolder.drawOdd.setText(odds.getDraw().toString());
            viewHolder.awayOdd.setText(odds.getAwayWin().toString());
        }
        setupViewHolderListeners(viewHolder,fixtures.get(position));
    }

    private void setupViewHolderListeners(ViewHolder viewHolder,final Fixture fixture) {
        View.OnClickListener betHome = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(fixture,fixture.getHomeTeamName(),"home");
            }
        };
        View.OnClickListener betAway = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(fixture,fixture.getAwayTeamName(),"away");
            }
        };
        View.OnClickListener betDraw = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(fixture,"draw","draw");
            }
        };
        viewHolder.away.setOnClickListener(betAway);
        viewHolder.awayOdd.setOnClickListener(betAway);
        viewHolder.home.setOnClickListener(betHome);
        viewHolder.homeOdd.setOnClickListener(betHome);
        viewHolder.drawOdd.setOnClickListener(betDraw);
    }

    private void showAlertDialog(final Fixture fixture, String betTeamName,final String bet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Place bet");
        builder.setMessage("Do you wish to place a bet on "+betTeamName+" ?");

        String positiveText = "Yes";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((CompetitionFixturesActivity)mContext).betOnTeam(fixture,bet);
                    }
                });

        String negativeText = "Cancel";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return fixtures.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.fixture_date)
        TextView date;
        @BindView(R.id.homeTextView)
        TextView home;
        @BindView(R.id.awayTextView)
        TextView away;
        @BindView(R.id.homeOddTextView)
        TextView homeOdd;
        @BindView(R.id.drawOddTextView)
        TextView drawOdd;
        @BindView(R.id.awayOddTextView)
        TextView awayOdd;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
