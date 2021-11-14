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

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    private JSONObject jObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView gamesPlayedVsAiTextView = findViewById(R.id.gamesPlayedVsAi);
        TextView gamesPlayedVsUserTextView = findViewById(R.id.gamesPlayedVsUser);
        TextView gamesWonVsAiTextView = findViewById(R.id.gamesWonVsAi);
        TextView gamesWonVsUserTextView = findViewById(R.id.gamesWonVsUser);
        TextView nameTexView = findViewById(R.id.name);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        Button saveButton = findViewById(R.id.savebutton);
        Button renameButton = findViewById(R.id.renamebutton);
        editText.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        //get the data from server
        RequestQueue queue = Volley.newRequestQueue(this);
        String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String url ="http://192.168.31.132:8080/api/profile/"+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jObject = new JSONObject(response);
                            gamesPlayedVsAiTextView.setText(jObject.getString("gamesPlayedVsAi"));
                            gamesPlayedVsUserTextView.setText(jObject.getString("gamesPlayedVsUser"));
                            gamesWonVsAiTextView.setText(jObject.getString("gamesWonVsAi"));
                            gamesWonVsUserTextView.setText(jObject.getString("gamesWonVsUser"));
                            nameTexView.setText(jObject.getString("name"));
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
        queue.add(stringRequest);
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
                String url ="http://192.168.31.132:8080/api/profile/changeUsername?Id=1&newUsername="+editText.getText();
                StringRequest renamerequest = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("tag", response);
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("tag", error.getMessage());
                    }
                });
                queue.add(renamerequest);


            }
        });



    }
}