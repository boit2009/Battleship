package com.example.battleship.network;

import com.example.battleship.leadboard.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface VolleyCallbackPlayer {
    void onSuccess(ArrayList<Player> result) throws JSONException;
}
