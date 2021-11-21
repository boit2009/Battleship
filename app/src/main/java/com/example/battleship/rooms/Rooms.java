package com.example.battleship.rooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.battleship.R;
import com.example.battleship.apicalls.UserCalls;
import com.example.battleship.grid.GridActivity;
import com.example.battleship.network.VolleyCallback;
import com.example.battleship.network.VolleyCallbackArrayVersion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Rooms extends AppCompatActivity implements VolleyCallbackArrayVersion, VolleyCallback{
    private ArrayList<Room> rooms;
    private RecyclerView recyclerView;
    private Button createNewButton;
    private RoomsAdapter roomsAdapter;
    String ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rooms= new ArrayList();
        Intent intent = getIntent();
        ID= intent.getStringExtra("ID");
        roomsAdapter= new RoomsAdapter(this, rooms);
        recyclerView.setAdapter(roomsAdapter);
        createNewButton=findViewById(R.id.createroombutton);
        createNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), GridActivity.class);
                intent.putExtra("mode","newRoom");
                intent.putExtra("ID",ID);
                startActivity(intent);
                finish();
            }
        });
        UserCalls.getRooms(getApplicationContext(),Rooms.this::onSuccessArray);




    }

    @Override
    public void onSuccessArray(JSONArray result, String mode) throws JSONException {

        for(int i=0;i<result.length();i++){
            JSONObject jsonObject= (JSONObject) result.get(i);
            String userID= jsonObject.getString("userId");
            int roomid=jsonObject.getInt("id");
            UserCalls.getProfile(getApplicationContext(),Rooms.this::onSuccess, userID,roomid);

           // rooms.add(new Room(userID,roomid));
            Log.i("rooms","success");
        }

    }


    @Override
    public void onSuccess(JSONObject result, String mode) throws JSONException {
        String username=result.getString("name");
        Log.i("valami",mode);
        rooms.add(new Room(username,Integer.valueOf(mode)));
        roomsAdapter.upDateAdapter(rooms);
    }
}