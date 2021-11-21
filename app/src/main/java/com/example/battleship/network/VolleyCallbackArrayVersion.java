package com.example.battleship.network;

import org.json.JSONArray;
import org.json.JSONException;


public interface VolleyCallbackArrayVersion {
    void onSuccessArray(JSONArray result, String mode) throws JSONException;
}
