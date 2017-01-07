package com.example.dioncamposano17.loveadvice20.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.model.Special;

import java.util.ArrayList;
import java.util.List;

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.MyViewHolder> {

    private List<Special> specialList;
    private Context context;
    private static final int FADE_DURATION = 1000;

    public SpecialAdapter(List<Special> specialList, Context context){
        this.specialList = specialList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.Pulse).playOn(holder.cardView);
        Special special = specialList.get(position);
        holder.specialItem.setText(Html.fromHtml(special.getSpecialList()));

    }

    @Override
    public int getItemCount() {
        return specialList.size();
    }
    public void addAll(List<Special> specialModel) {
        specialList = new ArrayList<>();
        specialList.addAll(specialModel);
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView specialItem;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            specialItem = (TextView) itemView.findViewById(R.id.specialItem);
            cardView = (CardView) itemView.findViewById(R.id.specialCard);
        }
    }
}