package com.example.battleship.profile;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleship.R;
import com.example.battleship.apicalls.UserCalls;
import com.example.battleship.network.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity implements VolleyCallback {
    private TextView nameTexView,gamesPlayedVsAiTextView,gamesPlayedVsUserTextView,gamesWonVsAiTextView,gamesWonVsUserTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        gamesPlayedVsAiTextView = findViewById(R.id.gamesPlayedVsAi);
        gamesPlayedVsUserTextView = findViewById(R.id.gamesPlayedVsUser);
        gamesWonVsAiTextView = findViewById(R.id.gamesWonVsAi);
        gamesWonVsUserTextView = findViewById(R.id.gamesWonVsUser);

        nameTexView = findViewById(R.id.name);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        Button saveButton = findViewById(R.id.savebutton);
        Button renameButton = findViewById(R.id.renamebutton);
        editText.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        //get the data from server

        String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UserCalls.getProfile(getApplicationContext(),Profile.this::onSuccess,ID,0);
        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameButton.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                nameTexView.setText(editText.getText());
                editText.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                renameButton.setVisibility(View.VISIBLE);
                String newName= String.valueOf(editText.getText());
                UserCalls.saveUserName(getApplicationContext(),ID,newName);

            }
        });



    }

    @Override
    public void onSuccess(JSONObject result, String mode) throws JSONException {
        gamesPlayedVsAiTextView.setText("Games played in Single Player: "+result.getString("gamesPlayedVsAi"));
        gamesPlayedVsUserTextView.setText("Games played in Multiplayer: "+result.getString("gamesPlayedVsUser"));
        gamesWonVsAiTextView.setText("Games won in Single Player: "+result.getString("gamesWonVsAi"));
        gamesWonVsUserTextView.setText("Games won in Multiplayer: "+result.getString("gamesWonVsUser"));
        nameTexView.setText("User name: "+result.getString("name"));
    }
}