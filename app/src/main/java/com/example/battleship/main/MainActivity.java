package com.example.battleship.main;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleship.grid.GridActivity;
import com.example.battleship.leadboard.LeaderBoard;
import com.example.battleship.profile.Profile;
import com.example.battleship.R;
import com.example.battleship.rooms.Rooms;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private static final int INTERNET_PERMISSION_CODE = 100;
    private JSONObject jObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get device uniqueID
        String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //get the neccessary Views
        final TextView textView = findViewById(R.id.textView2);
        final Button button = findViewById(R.id.button);
        final Button profileButton = findViewById(R.id.profilebutton);
        final Button leaderBoardButton = findViewById(R.id.leaderboard);
        final Button singlePlayerButton = findViewById(R.id.singleplayerbutton);
        final Button multiPlayerButton = findViewById(R.id.multiplayergamebutton);
        multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Rooms.class);
                startActivity(intent);
            }
        });

        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GridActivity.class);
                startActivity(intent);
            }
        });

        textView.setText("Device ID: "+ ID);

        //get the user's data with http request
        /*RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.31.132:8080/api/profile/1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    Log.i("tag", response);
                        try {
                            jObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", error.getMessage());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(25000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);*/


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GridActivity.class);
                startActivity(intent);
                /*try {
                    textView.setText(jObject.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


            }

        });
        leaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), LeaderBoard.class);
                startActivity(intent);
            }
        });
    }

}