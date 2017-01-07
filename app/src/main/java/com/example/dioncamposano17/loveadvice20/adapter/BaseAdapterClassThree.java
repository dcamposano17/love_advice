package com.example.dioncamposano17.loveadvice20.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dioncamposano17.loveadvice20.R;

import java.util.ArrayList;

public class BaseAdapterClassThree extends BaseAdapter {

    private Activity activity;
    private static ArrayList title, data;
    private static LayoutInflater inflater = null;

    public BaseAdapterClassThree(Activity act, ArrayList title, ArrayList data){
        activity = act;
        this.title = title;
        this.data = data;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.custom_list_information, null);

        if(position % 2 == 1){
            vi.setBackgroundColor(Color.parseColor("#E1E1E1"));
        } else {
            vi.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        TextView textViewTitle = (TextView) vi.findViewById(R.id.textViewTitle);
        String title2 = title.get(position).toString();
        textViewTitle.setText(title2);

        TextView textViewData = (TextView) vi.findViewById(R.id.textViewData);
        String data2 = data.get(position).toString();
        textViewData.setText(data2);
        return  vi;
    }
}
