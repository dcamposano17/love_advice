package com.example.dioncamposano17.loveadvice20.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.model.Glossary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dioncamposano17 on 28/12/2016.
 */
public class GlossaryAdapter extends RecyclerView.Adapter<GlossaryAdapter.MyViewHolder> {

    private List<Glossary> glossaryList;
    private Context context;

    public GlossaryAdapter(List<Glossary> glossaryList, Context context){
        this.glossaryList = glossaryList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.glossary_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glossary glossary = glossaryList.get(position);
        holder.glossaryItem.setText(Html.fromHtml(glossary.getGlossaryList()));
        if (position % 2 == 1)
            holder.glossaryItem.setBackgroundColor(context.getResources().getColor(R.color.inactiv));
        else
            holder.glossaryItem.setBackgroundColor(Color.WHITE);
    }
    @Override
    public int getItemCount() {
        return glossaryList.size();
    }
    public void addAll(List<Glossary> glossaryModel) {
        glossaryList = new ArrayList<>();
        glossaryList.addAll(glossaryModel);
        notifyDataSetChanged();
        List<String> glossaryList = new ArrayList<>();
        Collections.sort(glossaryList, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareToIgnoreCase(t1);
            }
        });
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView glossaryItem;

        public MyViewHolder(final View itemView) {
            super(itemView);
            glossaryItem = (TextView) itemView.findViewById(R.id.glossaryItem);
        }

    }
}
