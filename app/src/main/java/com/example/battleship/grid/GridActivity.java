package com.example.battleship.grid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
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
import com.example.battleship.main.MainActivity;
import com.example.battleship.R;
import com.example.battleship.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GridActivity extends AppCompatActivity implements VolleyCallback {
    @Override
    public void onPause() {

        if(gameState.equals(GameState.host_waiting)) {
            UserCalls.leaveRoom(getApplicationContext(),ID);
        }
        else{
            if(!end_game){
                UserCalls.leaveGame(getApplicationContext(),ID);
            }

        }
        super.onPause();

    }
    @Override
    public void onResume(){

        if(resumeCounter==1){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        resumeCounter++;
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        if(gameState.equals(GameState.host_waiting)){
            UserCalls.leaveRoom(getApplicationContext(),ID);
            super.onBackPressed();

        }else{
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.backtomenu))
                    .setMessage(getResources().getString(R.string.areyousure))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserCalls.leaveGame(getApplicationContext(),ID);
                            end_game=true;
                            finish();
                        }

                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        }


    }

    private GridView myGrid,opponentGrid;
    private GameState gameState;
    private Button readyButton,leaveButton, newShipButton;
    private TrackAdapter myTrackAdapter,opponentTrackAdapter;
    private String mode,ID;
    private boolean end_game=false;
    private int resumeCounter;
    private LinearLayout linearLayout,mainLienarLayout;
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
        this.gameState=gameState;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        resumeCounter=0;
        disabledFields= new ArrayList();
        Intent intent = getIntent();
        mode= intent.getStringExtra("mode");
        ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println(ID);
        myGrid =  findViewById(R.id.myGrid);
        if(mode.equals("robot"))
            PlayCalls.playWithRobot(getApplicationContext(),GridActivity.this::onSuccess, ID);
        opponentGrid = findViewById(R.id.opponentGrid);
        newShipButton = findViewById(R.id.newshipbutton);
        leaveButton = findViewById(R.id.leavebutton);
        readyButton = findViewById(R.id.readybutton);
        linearLayout= findViewById(R.id.linear);
        mainLienarLayout= findViewById(R.id.mainLinearLayout);

        linearLayout.setBackgroundColor(Color.rgb(141,141,142));
        mainLienarLayout.setBackgroundColor(Color.rgb(141,141,142));
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserCalls.leaveRoom(getApplicationContext(),ID);
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
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
                    textView.setText("?");
                   // textView.setBackgroundColor();
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
        if(mode.equals("multiplayer")){
            setGameState(GameState.waiting_in_room);
            String roomID=intent.getStringExtra("roomID");
            UserCalls.connectRoom(getApplicationContext(),GridActivity.this::onSuccess,roomID, ID);

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
        if(mode.equals("-1")){
            String winner=result.getString("name");
            new AlertDialog.Builder(GridActivity.this)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("GAME OVER")
                    .setMessage(winner+ " won")
                    .setPositiveButton(getResources().getString(R.string.backtomenu), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(GridActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        }

                    }).show();

        }


        if (mode.equals("start") || mode.equals("newship")){
            if (mode.equals("start")){
                setGameState(GameState.waiting_in_room);
            }
            JSONArray asD = (JSONArray) result.get("field");
            myTrackAdapter.updateTrack(asD);

        }
        if(mode.equals("shoot") || mode.equals("ready") ){
            boolean finished=false;
            String winner="";
            if(mode.equals("ready")){
                setGameState(GameState.in_game);
            }
            Iterator<String> keys = result.keys();
            boolean nextFieldIsMyField=false;
            while (keys.hasNext()) {
                String key = keys.next();
                if (result.get(key) instanceof Boolean) {
                    finished =(Boolean)result.get(key);
                }
                if (result.get(key) instanceof String) {
                    if(key.equals("winner")){
                        winner =(String) result.get(key);
                    }
                    if(result.get(key).equals(ID)){
                        nextFieldIsMyField=true;
                    }
                }
                if (result.get(key) instanceof JSONObject) {
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
            }
            if(finished){
                UserCalls.getProfile(getApplicationContext(),GridActivity.this::onSuccess,winner,-1);
            }


        }

        opponentGrid.setEnabled(true);
    }


}