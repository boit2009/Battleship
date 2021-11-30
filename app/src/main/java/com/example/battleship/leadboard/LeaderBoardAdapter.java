package com.example.battleship.leadboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.battleship.R;
import com.example.battleship.leadboard.Player;
import com.example.battleship.main.MainActivity;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Player> arrayList;
    private String mode;
    public LeaderBoardAdapter(Context c, ArrayList<Player> arrayList, String mode){
        context=c;
        this.arrayList=arrayList;
        this.mode= mode;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.leaderboard_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText(arrayList.get(position).getName());
        if(mode.equals("user"))
            holder.score.setText("wins: " + String.valueOf(arrayList.get(position).getWinvsUser()));
        else
            holder.score.setText("wins: " + String.valueOf(arrayList.get(position).getWinvsRobot()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username,score;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username=(TextView) itemView.findViewById(R.id.username);
            score=(TextView) itemView.findViewById(R.id.score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}
