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
    private Button button;
    private TrackAdapter myTrackAdapter,opponentTrackAdapter;
    private String mode,ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        Intent intent = getIntent();
        mode= intent.getStringExtra("mode");
        ID=intent.getStringExtra("ID");
        myGrid = (GridView) findViewById(R.id.myGrid);
        PlayCalls.playWithRobot(mode,getApplicationContext(),GridActivity.this::onSuccess, ID);
        opponentGrid =(GridView) findViewById(R.id.opponentGrid);
        button = (Button) findViewById(R.id.button);
        myTrackAdapter = new TrackAdapter(getApplicationContext(), _getMyTrackField());
        opponentTrackAdapter = new TrackAdapter(getApplicationContext(), _getMyTrackField());
        opponentGrid.setAdapter(opponentTrackAdapter);
        myGrid.setAdapter(myTrackAdapter);
        myGrid.setEnabled(false);
        opponentGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                opponentGrid.setEnabled(false);
                LinearLayout linearLayout = (LinearLayout) view;
                TextView textView = (TextView) linearLayout.getChildAt(0);
               // textView.setText("?");
                //textView.setBackgroundColor(Color.rgb(2,221,12));
                PlayCalls.shoot(getApplicationContext(),GridActivity.this::onSuccess,ID,position);

            }
        });
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
        if (mode.equals("start")){
            JSONArray asD = (JSONArray) result.get("field");
            myTrackAdapter.updateTrack(asD);
        }else {
            Iterator<String> keys = result.keys();
            boolean nextFieldIsMyField=false;
            while (keys.hasNext()) {
                String key = keys.next();
                if (result.get(key) instanceof String) {
                    Log.i("valami",(String) result.get(key));
                    Log.i("valami",ID);

                    if(result.get(key).equals(ID)){
                        nextFieldIsMyField=true;
                    }
                }
                if (result.get(key) instanceof JSONObject) {
                    Log.i("valami",String.valueOf(nextFieldIsMyField));
                    JSONObject player = (JSONObject)result.get(key);
                    Iterator<String> keys2 = player.keys();
                    while (keys2.hasNext()) {
                        //Log.i("valami",);

                        String key2 = keys2.next();
                       // Log.i("valami","key2");
                       // Log.i("valami",key2);
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
                // JSONObject jsonObject = (JSONObject) result.get("player1");
            }
        }

        opponentGrid.setEnabled(true);
    }
}