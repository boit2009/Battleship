package com.example.battleship.rooms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.battleship.R;

import java.util.ArrayList;

public class Rooms extends AppCompatActivity {
    private ArrayList<Room> rooms;
    private RecyclerView recyclerView;
    private RoomsAdapter roomsAdapter;
    public ArrayList<Room> mockRooms(){
        ArrayList<Room> mockrooms= new ArrayList();
        for(int i=0;i<20;i++) {
            mockrooms.add(new Room("Boti szobája" + String.valueOf(i)));
        }
        return mockrooms;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rooms= mockRooms();
        roomsAdapter= new RoomsAdapter(this, rooms);
        recyclerView.setAdapter(roomsAdapter);



    }
}