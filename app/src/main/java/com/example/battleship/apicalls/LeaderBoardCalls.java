package com.example.battleship.apicalls;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleship.BuildConfig;
import com.example.battleship.leadboard.Player;
import com.example.battleship.network.VolleyCallbackPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class LeaderBoardCalls {
    static public void getLeaderBoard(String mode, Context context, VolleyCallbackPlayer volleyCallbackPlayer){
        ArrayList<Player>players= new ArrayList();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://"+ BuildConfig.ipAddress +"/api/leaderboard/"+mode;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);

                            JSONArray asD = (JSONArray) jObject.get("leaderboard");
                            for(int i=0;i<asD.length();i++){
                                JSONObject a = (JSONObject) asD.get(i);
                                Iterator<String>keys = a.keys();
                                int iteration=0;
                                String name=null;
                                int vsRobot=0;
                                int vsUser=0;
                                while (keys.hasNext()){
                                    String key = keys.next();
                                    if (iteration==0)
                                        name =String.valueOf(a.get(key));
                                    if (iteration==3)
                                        vsRobot =Integer.valueOf((Integer) a.get(key));
                                    if (iteration==4)
                                        vsUser =Integer.valueOf((Integer) a.get(key));

                                    Log.i("calls", String.valueOf(a.get(key)));
                                    iteration++;

                                }
                                players.add(new Player(name, vsRobot,vsUser));
                            }
                            volleyCallbackPlayer.onSuccess(players);





                        } catch (JSONException e) {
                            Log.i("calls", "error");
                            e.printStackTrace();

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("calls", error.toString());
            }
        });
        queue.add(stringRequest);

    }
}
