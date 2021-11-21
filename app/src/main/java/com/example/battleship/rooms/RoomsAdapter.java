package com.example.battleship.rooms;

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
import com.example.battleship.grid.GridActivity;
import com.example.battleship.main.MainActivity;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Room> arrayList;
    public RoomsAdapter(Context c, ArrayList<Room> arrayList){
        context=c;
        this.arrayList=arrayList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.roomname.setText(arrayList.get(position).getRoomName());
        holder.connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, GridActivity.class);
                intent.putExtra("mode","multiplayer");
                context.startActivity(intent);
            }
        });

    }
    public void upDateAdapter(ArrayList<Room>rooms){
        this.arrayList=rooms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView roomname;
        Button connectButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomname=(TextView) itemView.findViewById(R.id.roomname);
            connectButton=(Button) itemView.findViewById(R.id.connectbutton);

        }
    }
}
