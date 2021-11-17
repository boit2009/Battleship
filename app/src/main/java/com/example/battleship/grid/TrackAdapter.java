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

import org.json.JSONException;
import org.json.JSONObject;

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

    public void updateTrack(JSONObject jObject) throws JSONException {
        jObject.getString("player1");
        System.out.println(jObject.toString());
        String field= jObject.getString("field1");

        String state= jObject.getString("gridstate1");
        State state1= null;
        switch (state){
            case "HIT":
                state1=State.hit;

        }
        fields.get(Integer.valueOf(field)).setState(state1);
        Log.i("tag", "siker");
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
        if(getItem(i).getState()==State.hit){
            field.setBackgroundColor(Color.rgb(0,255,0));
            field.setText("h");
        }
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
