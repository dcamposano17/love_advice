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
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.model.Friends;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

    private List<Friends> friendList;
    private Context context;
    private static final int FADE_DURATION = 300;

    public FriendsAdapter(List<Friends> friendList, Context context){
        this.friendList = friendList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.Tada).playOn(holder.cardView);
        Friends friends = friendList.get(position);
        holder.friendItem.setText(Html.fromHtml(friends.getFriendsList()));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
    public void addAll(List<Friends> friendModel) {
        friendList = new ArrayList<>();
        friendList.addAll(friendModel);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendItem;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            friendItem = (TextView) itemView.findViewById(R.id.friendItem);
            cardView = (CardView) itemView.findViewById(R.id.friendCard);
        }
    }
}