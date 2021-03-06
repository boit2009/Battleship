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
import com.example.battleship.apicalls.UserCalls;
import com.example.battleship.grid.GridActivity;
import com.example.battleship.information.InformationActivity;
import com.example.battleship.leadboard.LeaderBoard;
import com.example.battleship.network.VolleyCallback;
import com.example.battleship.profile.Profile;
import com.example.battleship.R;
import com.example.battleship.rooms.Rooms;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements VolleyCallback {

    private static final int INTERNET_PERMISSION_CODE = 100;
    private JSONObject jObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get device uniqueID
        String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UserCalls.getProfile(getApplicationContext(),MainActivity.this::onSuccess,ID,0);


        //get the neccessary Views

        final Button profileButton = findViewById(R.id.profilebutton);
        final Button leaderBoardRobotButton = findViewById(R.id.leaderboardrobot);
        final Button leaderBoardUserButton = findViewById(R.id.leaderboarduser);
        final Button singlePlayerButton = findViewById(R.id.singleplayerbutton);
        final Button multiPlayerButton = findViewById(R.id.multiplayergamebutton);
        final Button infoButton = findViewById(R.id.infobutton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
            }
        });
        multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Rooms.class);
                intent.putExtra("ID",ID);
                intent.putExtra("mode","user");
                startActivity(intent);
            }
        });

        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GridActivity.class);
                intent.putExtra("ID",ID);
                intent.putExtra("mode","robot");
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        leaderBoardRobotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), LeaderBoard.class);

                intent.putExtra("mode", "robot");
                startActivity(intent);
            }
        });
        leaderBoardUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), LeaderBoard.class);
                intent.putExtra("mode", "user");
                startActivity(intent);

            }
        });
    }

    @Override
    public void onSuccess(JSONObject result, String mode) throws JSONException {

    }
}