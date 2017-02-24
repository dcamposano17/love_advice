package com.example.dioncamposano17.loveadvice20.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.model.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dioncamposano17 on 22/02/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<Chat> chatList;
    private Context context;

    public ChatAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        if (chatList.get(position).getMessageType().equalsIgnoreCase("2")) {
            params.setMargins(10, 10, 50, 10);
            params.gravity = Gravity.START;
            holder.txt_message.setTextColor(Color.BLACK);
            holder.txt_message.setBackground(context.getResources().getDrawable(R.drawable.round_corner_two));
        } else {
            params.setMargins(50, 10, 10, 10);
            params.gravity = Gravity.END;
            holder.txt_message.setTextColor(Color.WHITE);
            holder.txt_message.setBackground(context.getResources().getDrawable(R.drawable.round_corner));
        }
        holder.txt_message.setPadding(30, 20, 30, 20);
        holder.txt_id.setText(chatList.get(position).getId());
        holder.txt_message.setText(chatList.get(position).getMessage());
        holder.txt_message.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void addAll(List<Chat> messagModel) {
        chatList = new ArrayList<>();
        chatList.addAll(messagModel);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_message, txt_id;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_message = (TextView) itemView.findViewById(R.id.txt_message);
            txt_id = (TextView) itemView.findViewById(R.id.txt_id);
        }
    }
}
