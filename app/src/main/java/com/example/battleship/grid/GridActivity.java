package com.example.battleship.grid;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.battleship.apicalls.PlayCalls;
import com.example.battleship.apicalls.UserCalls;
import com.example.battleship.network.NetworkUtil;
import com.example.battleship.R;
import com.example.battleship.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GridActivity extends AppCompatActivity implements VolleyCallback {

    private GridView myGrid,opponentGrid;
    private List<Field> myTrackField, opponentTrackField;
    private Button readyButton,leaveButton, newShipButton;
    private TrackAdapter myTrackAdapter,opponentTrackAdapter;
    private String mode,ID;
    private ArrayList<Integer>disabledFields;

    private void setGameState(GameState gameState){
        if(gameState.equals(GameState.host_waiting)){
            leaveButton.setVisibility(View.VISIBLE);
            readyButton.setVisibility(View.INVISIBLE);
            newShipButton.setVisibility(View.INVISIBLE);
            myGrid.setVisibility(View.INVISIBLE);
            opponentGrid.setVisibility(View.INVISIBLE);

        }
        if(gameState.equals(GameState.waiting_in_room)){
            leaveButton.setVisibility(View.INVISIBLE);
            readyButton.setVisibility(View.VISIBLE);
            newShipButton.setVisibility(View.VISIBLE);
            myGrid.setVisibility(View.VISIBLE);
            opponentGrid.setVisibility(View.INVISIBLE);
        }
        if(gameState.equals(GameState.in_game)){
            leaveButton.setVisibility(View.INVISIBLE);
            readyButton.setVisibility(View.INVISIBLE);
            newShipButton.setVisibility(View.INVISIBLE);
            myGrid.setVisibility(View.VISIBLE);
            opponentGrid.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        disabledFields= new ArrayList();
        Intent intent = getIntent();
        mode= intent.getStringExtra("mode");
        ID=intent.getStringExtra("ID");
        System.out.println(ID);
        myGrid = (GridView) findViewById(R.id.myGrid);
        if(mode.equals("robot"))
            PlayCalls.playWithRobot(getApplicationContext(),GridActivity.this::onSuccess, ID);
        opponentGrid =(GridView) findViewById(R.id.opponentGrid);
        newShipButton = (Button) findViewById(R.id.newshipbutton);
        leaveButton = (Button) findViewById(R.id.leavebutton);
        readyButton = (Button) findViewById(R.id.readybutton);
        newShipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayCalls.getNewShipPositions(getApplicationContext(),GridActivity.this::onSuccess, ID);
            }
        });
        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newShipButton.setVisibility(View.INVISIBLE);
                PlayCalls.ready(getApplicationContext(),GridActivity.this::onSuccess, ID);


            }
        });
        myTrackAdapter = new TrackAdapter(getApplicationContext(), _getMyTrackField());
        opponentTrackAdapter = new TrackAdapter(getApplicationContext(), _getMyTrackField());
        opponentGrid.setAdapter(opponentTrackAdapter);
        myGrid.setAdapter(myTrackAdapter);
        myGrid.setEnabled(false);
        opponentGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!disabledFields.contains(position)){
                    opponentGrid.setEnabled(false);
                    LinearLayout linearLayout = (LinearLayout) view;
                    TextView textView = (TextView) linearLayout.getChildAt(0);
                    PlayCalls.shoot(getApplicationContext(),GridActivity.this::onSuccess,ID,position);
                    disabledFields.add(position);
                }

            }
        });
        setGameState(GameState.waiting_in_room);
        if(mode.equals("newRoom")){
            setGameState(GameState.host_waiting);
            UserCalls.createRoom(getApplicationContext(),GridActivity.this::onSuccess, ID);

        }

    }

    List<Field> _getMyTrackField(){
        List<Field> fields = new ArrayList<Field>();
        for(int i=0; i<100; i++){
            fields.add(new Field(i, State.water));
        }
        return  fields;
    }

    @Override
    public void onSuccess(JSONObject result,String mode) throws JSONException {

        if (mode.equals("start") || mode.equals("newship")){
            if (mode.equals("start")){
                setGameState(GameState.waiting_in_room);
            }
            JSONArray asD = (JSONArray) result.get("field");
            myTrackAdapter.updateTrack(asD);

        }
        if(mode.equals("shoot") ||mode.equals("ready") ){
            if(mode.equals("ready")){
                setGameState(GameState.in_game);
            }
            Iterator<String> keys = result.keys();
            boolean nextFieldIsMyField=false;
            while (keys.hasNext()) {
                String key = keys.next();
                if (result.get(key) instanceof String) {
                  //  Log.i("valami",(String) result.get(key));
                  //  Log.i("valami",ID);
                    if(result.get(key).equals(ID)){
                        nextFieldIsMyField=true;
                    }
                }
                if (result.get(key) instanceof JSONObject) {
                 //   Log.i("valami",String.valueOf(nextFieldIsMyField));
                    JSONObject player = (JSONObject)result.get(key);
                    Iterator<String> keys2 = player.keys();
                    while (keys2.hasNext()) {
                        String key2 = keys2.next();
                        JSONArray array= (JSONArray) player.get("field");
                        if(nextFieldIsMyField){
                            nextFieldIsMyField=false;
                            myTrackAdapter.updateTrack(array);
                        }
                        else{
                            opponentTrackAdapter.updateTrack(array);
                        }
                    }

                }
                System.out.println(result.get("player1").toString());
            }
        }

        opponentGrid.setEnabled(true);
    }


}