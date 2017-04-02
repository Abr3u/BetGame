package com.abreu.betgame.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abreu.betgame.R;
import com.abreu.betgame.model.pojo.Bet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBetsAdapter extends RecyclerView.Adapter<MyBetsAdapter.ViewHolder> {

    private List<Bet> bets;
    private Context mContext;

    public MyBetsAdapter(Context context) {
        this.mContext = context;
        this.bets = new ArrayList<>();
    }

    public void addBet(Bet bet) {
        if (bets.contains(bet)) {
            bets.remove(bet);
        }
        bets.add(bet);
        notifyDataSetChanged();
    }

    public void removeBet(Bet bet) {
        if (bets.contains(bet)) {
            bets.remove(bet);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.competition_row_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Bet bet = bets.get(position);

        Boolean wonBet = bet.wonBet;
        String won = "";
        if(wonBet != null){
            won = (bet.wonBet) ? "WON" : "LOST";
            if(bet.wonBet){
                viewHolder.itemView.setBackgroundColor(Color.GREEN);
            }else{
                viewHolder.itemView.setBackgroundColor(Color.RED);
            }
        }

        String title = bet.homeTeam + " vs " + bet.awayTeam;
        String body = "bet on " + bet.bet + " (" + bet.betOdd + ") " + won;
        viewHolder.postTitle.setText(title);
        viewHolder.postBody.setText(body);
    }

    @Override
    public int getItemCount() {
        return bets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.post_title)
        TextView postTitle;
        @BindView(R.id.post_body)
        TextView postBody;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
