package com.example.battleship.apicalls;

import android.content.Context;
import android.provider.Settings;
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
import com.example.battleship.network.VolleyCallback;
import com.example.battleship.network.VolleyCallbackPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayCalls {
    static public void playWithRobot(Context context, VolleyCallback volleyCallback, String ID){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://"+ BuildConfig.ipAddress +"/api/play/opponent=robot?userId="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                             volleyCallback.onSuccess(jObject,"start");
                        } catch (JSONException e) {
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
    static public void getNewShipPositions(Context context, VolleyCallback volleyCallback, String ID){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url ="http://"+ BuildConfig.ipAddress +"/api/play/getNewShipPositions?userId="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                            volleyCallback.onSuccess(jObject,"newship");
                        } catch (JSONException e) {
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
    static public void shoot(Context context, VolleyCallback volleyCallback, String ID,int position){
        ArrayList<Player> players= new ArrayList();
        RequestQueue queue = Volley.newRequestQueue(context);

        String url ="http://"+ BuildConfig.ipAddress +"/api/play/shoot?userId="+ID+"&fieldId="+String.valueOf(position);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                            volleyCallback.onSuccess(jObject,"shoot");
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("calls", error.toString());

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
    static public void ready(Context context, VolleyCallback volleyCallback, String ID){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url ="http://"+ BuildConfig.ipAddress +"/api/play/ready?userId="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                            volleyCallback.onSuccess(jObject,"ready");
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("calls", error.toString());

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}
