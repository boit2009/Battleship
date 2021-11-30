package com.example.battleship.grid;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.battleship.R;
import com.example.battleship.leadboard.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class TrackAdapter extends BaseAdapter {

    Context context;
    List<Field> fields;
    LayoutInflater inflater;
    public TrackAdapter(Context context, List<Field> fields){
        this.context = context;
        this.fields = fields;
        inflater = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return fields.size();
    }

    @Override
    public Field getItem(int i) {
        return fields.get(i);
    }

    @Override
    public long getItemId(int i) {
        return fields.get(i).getId();
    }

    public void updateTrack(JSONArray asD) throws JSONException {
        for(int i=0;i<asD.length();i++){
            String state= (String) asD.get(i);
            if(state.equals("WATER"))
                fields.get(i).setState(State.water);
            if(state.equals("SUNKEN"))
                fields.get(i).setState(State.sunk);
            if(state.equals("HIT"))
                fields.get(i).setState(State.hit);
            if(state.equals("MISS"))
                fields.get(i).setState(State.missed);
            if(state.equals("SHIP"))
                fields.get(i).setState(State.ship);
        }

        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.track_field_item, null);
        TextView field = view.findViewById(R.id.field);
        if(getItem(i).getState()==State.hit){
            field.setBackgroundColor(Color.rgb(253,88,81));

        }
        if(getItem(i).getState()==State.ship) {
            field.setBackgroundColor(Color.rgb(220,220,220));

        }
        if(getItem(i).getState()==State.water) {
            field.setBackgroundColor(Color.rgb(0,0,255));
        }
        if(getItem(i).getState()==State.sunk) {
            field.setBackgroundColor(Color.rgb(203, 129, 0));
        }
        if(getItem(i).getState()==State.missed) {
            field.setBackgroundColor(Color.rgb(2, 0, 5));

        }

        return view;
    }

}
