package com.example.battleship.network;

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

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkUtil {

    public static void post(Context context, VolleyCallback callback, int position){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url ="http://192.168.31.132:8080/api/play/shoot?userId=114c7684a88bbcc2&fieldId="+String.valueOf(position);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    Log.i("tag", response);
                        try {
                            JSONObject jObject = new JSONObject(response);

                            // JsonObject -> List<Filed>
                            callback.onSuccess(jObject,"start");
                        } catch (JSONException e) {
                           // callback.onSuccess(e.getMessage());
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // callback.onSuccess(error.getMessage());
                Log.i("tag", error.getMessage());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(25000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}
