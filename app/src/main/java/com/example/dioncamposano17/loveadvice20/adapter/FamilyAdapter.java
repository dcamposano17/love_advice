package com.example.dioncamposano17.loveadvice20.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.model.Family;

import java.util.ArrayList;
import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.MyViewHolder>{

    private List<Family> familyList;
    private Context context;
    private final static int FADE_DURATION = 1000;
    private static final String LOGCAT = "SubjectAdapter";

    public FamilyAdapter(List<Family> familyList, Context context){
        this.familyList = familyList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.family_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.Pulse).playOn(holder.cardView);
        Family family = familyList.get(position);
        holder.familyItem.setText(Html.fromHtml(family.getFamilyList()));
    }
    @Override
    public int getItemCount() {
        return familyList.size();
    }
    public void addAll(List<Family> familyModel) {
        familyList = new ArrayList<>();
        familyList.addAll(familyModel);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView familyItem;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            familyItem = (TextView) itemView.findViewById(R.id.familyItem);
            cardView = (CardView) itemView.findViewById(R.id.familyCard);
        }
    }
}
