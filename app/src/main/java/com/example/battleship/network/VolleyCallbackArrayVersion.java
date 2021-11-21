package com.example.battleship.network;

import org.json.JSONArray;
import org.json.JSONException;


public interface VolleyCallbackArrayVersion {
    void onSuccess(JSONArray result, String mode) throws JSONException;
}
