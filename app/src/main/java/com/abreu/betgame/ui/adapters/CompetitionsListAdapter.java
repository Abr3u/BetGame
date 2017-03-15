package com.abreu.betgame.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abreu.betgame.R;
import com.abreu.betgame.model.pojo.Competition;
import com.abreu.betgame.ui.activities.CompetitionFixturesActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompetitionsListAdapter extends RecyclerView.Adapter<CompetitionsListAdapter.ViewHolder> {

    private List<Competition> competitions;
    private Context mContext;

    public CompetitionsListAdapter(Context context) {
        this.mContext = context;
        this.competitions = new ArrayList<>();
    }

    public void addCompetitions(List<Competition> newCompetitions) {
        competitions.addAll(newCompetitions);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.competition_row_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, CompetitionFixturesActivity.class);
                Log.d("yyy","click position "+viewHolder.getAdapterPosition());
                i.putExtra("competitionId",Integer.parseInt(""+competitions.get(viewHolder.getAdapterPosition()).getId()));
                mContext.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.postTitle.setText(competitions.get(position).getCaption());
        viewHolder.postBody.setText(""+competitions.get(position).getLeague());
    }

    @Override
    public int getItemCount() {
        return competitions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
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
