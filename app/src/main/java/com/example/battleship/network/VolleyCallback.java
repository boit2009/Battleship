package com.example.battleship.network;

import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject result,String mode) throws JSONException;
}
