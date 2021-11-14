package com.example.battleship.grid;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.battleship.R;

import java.util.List;

public class TrackAdapter extends BaseAdapter {

    Context context;
    List<Field> fields;
    LayoutInflater inflater;
    public TrackAdapter(Context context, List<Field> fields){
        this.context = context;
        this.fields = fields;
        Log.i("adsAG", String.valueOf(fields.size()));
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

    public void updateTrack(List<Field> fields){
        this.fields = fields;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.track_field_item, null);
        TextView field = (TextView) view.findViewById(R.id.field);
        field.setBackgroundColor(Color.rgb(0,0,255));
        field.setText(String.valueOf(getItem(i).getId()));
        Log.i("adsAG", String.valueOf(field.getHeight()));
        // getColor(getItem(i).getState())
        return view;
    }

    int getColor(State state){
        if(state == State.water){
            return R.color.blue;
        } else if(state == State.ship){
            return R.color.grey;
        } else if(state == State.sunk){
            return R.color.red;
        } else if(state == State.missed){
            return R.color.green;
        } else if(state == State.hit){
            return R.color.black;
        } else {
            return R.color.black;
        }
    }
}
