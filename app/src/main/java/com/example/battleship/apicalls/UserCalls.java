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
import com.example.battleship.leadboard.Player;
import com.example.battleship.network.VolleyCallback;
import com.example.battleship.network.VolleyCallbackArrayVersion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class UserCalls {

    static public void leaveRoom(Context context,String ID){
        ArrayList<Player> players= new ArrayList();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.31.132:8080/api/leaveRoom?userId="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("calls", response.toString());
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("calls", "valamivolley");
                Log.i("calls", error.toString());

            }
        });

        queue.add(stringRequest);
        Log.i("calls", "valamisemmi");


    }
    static public void getRooms(Context context, VolleyCallbackArrayVersion volleyCallback){
        ArrayList<Player> players= new ArrayList();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.31.132:8080/api/rooms";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray= new JSONArray(response);
                          //  JSONObject jObject = (JSONObject)jsonArray.get(0) ;
                            volleyCallback.onSuccessArray(jsonArray,"rooms");

                        } catch (JSONException e) {
                            Log.i("calls", "error");
                            e.printStackTrace();

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("calls", "valamivolley");
                Log.i("calls", error.toString());

            }
        });

        queue.add(stringRequest);
        Log.i("calls", "valamisemmi rooms");


    }
    static public void connectRoom(Context context, VolleyCallback volleyCallback,String roomId,String ID){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.31.132:8080/api/play/"+roomId+"/opponent=user?userId="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response);

                            volleyCallback.onSuccess(jsonObject,"start");

                        } catch (JSONException e) {
                            Log.i("calls", "error");
                            e.printStackTrace();

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("calls", "valamivolley from connectRoom");
                Log.i("calls", error.toString());

            }
        });

        queue.add(stringRequest);
        Log.i("calls", "valamisemmi");


    }
    static public void createRoom(Context context, VolleyCallback volleyCallback,String ID){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.31.132:8080/api/createRoom?userId="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            volleyCallback.onSuccess(jsonObject,"start");

                        } catch (JSONException e) {
                            Log.i("calls", "error");
                            e.printStackTrace();

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("calls", "valamivolley");
                Log.i("calls", error.toString());

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }
    static public void saveUserName(Context context, String ID,String newName){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.31.132:8080/api/profile/changeUsername?Id="+ID+"&newUsername="+newName;
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
    static public void getProfile(Context context, VolleyCallback volleyCallback,String ID,int roomid){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.31.132:8080/api/profile/"+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                            volleyCallback.onSuccess(jObject,String.valueOf(roomid));

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

    }
    static public void leaveGame(Context context,String ID){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.31.132:8080/api//leaveGame?userId="+ID;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", error.getMessage());
            }
        });
        queue.add(stringRequest);

    }

}
