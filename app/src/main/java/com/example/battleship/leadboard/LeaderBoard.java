package com.example.battleship.leadboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.battleship.R;
import com.example.battleship.apicalls.LeaderBoardCalls;
import com.example.battleship.grid.GridActivity;
import com.example.battleship.network.VolleyCallbackPlayer;
import com.example.battleship.rooms.LeaderBoardAdapter;
import com.example.battleship.rooms.Room;
import com.example.battleship.rooms.RoomsAdapter;

import org.json.JSONException;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity implements VolleyCallbackPlayer {
    private ArrayList<Player> players;
    private RecyclerView recyclerView;
    private LeaderBoardAdapter leaderBoardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        recyclerView= findViewById(R.id.leaderecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       LeaderBoardCalls.getLeaderBoard("user",this, LeaderBoard.this::onSuccess);

    }

    @Override
    public void onSuccess(ArrayList<Player> players) throws JSONException {
        leaderBoardAdapter=new LeaderBoardAdapter(this, players);
        recyclerView.setAdapter(leaderBoardAdapter);
    }
}