package com.example.battleship.grid;

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

import com.example.battleship.network.NetworkUtil;
import com.example.battleship.R;
import com.example.battleship.network.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity implements VolleyCallback {

    private GridView myGrid,opponentGrid;
    private List<Field> myTrackField, opponentTrackField;
    private Button button;
    private TrackAdapter myTrackAdapter,opponentTrackAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        myGrid = (GridView) findViewById(R.id.myGrid);
        opponentGrid =(GridView) findViewById(R.id.opponentGrid);
        button = (Button) findViewById(R.id.button);
        myTrackAdapter = new TrackAdapter(getApplicationContext(), _getMyTrackField());
        opponentTrackAdapter = new TrackAdapter(getApplicationContext(), _getMyTrackField());
        opponentGrid.setAdapter(opponentTrackAdapter);
        myGrid.setAdapter(myTrackAdapter);
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myGrid.setEnabled(false);
                LinearLayout linearLayout = (LinearLayout) view;
                TextView textView = (TextView) linearLayout.getChildAt(0);
                textView.setText("asd");
                textView.setBackgroundColor(Color.rgb(2,221,12));
                NetworkUtil.post(getApplicationContext(), GridActivity.this::onSuccess,position);

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
    public void onSuccess(JSONObject result) throws JSONException {
      //  Log.i("NSUCCES:",result.getString("player1"));

        myTrackAdapter.updateTrack(result);


        myGrid.setEnabled(true);
    }
}