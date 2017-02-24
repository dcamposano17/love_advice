package com.example.dioncamposano17.loveadvice20.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.dioncamposano17.loveadvice20.Communicator;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.RevealLayout;
import com.example.dioncamposano17.loveadvice20.model.Assessment;
import com.example.dioncamposano17.loveadvice20.model.Family;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dioncamposano17 on 21/02/2017.
 */

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.MyViewHolder> {

    private List<Assessment> assessmentList;
    private Communicator mCommunicator;
    private Context context;

    public AssessmentAdapter(List<Assessment> assessmentList, Context context) {
        this.assessmentList = assessmentList;
        this.context = context;
    }

    @Override
    public AssessmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_row, parent, false);
        return new AssessmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.MyViewHolder holder, int position) {
        YoYo.with(Techniques.Pulse).playOn(holder.card_view);
        holder.txt_id.setText(assessmentList.get(position).getId());
        holder.txt_assessment_name.setText(assessmentList.get(position).getAssess_name());
    }

    public void onItemClickListener(Communicator mCommunicator) {
        this.mCommunicator = mCommunicator;
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public void addAll(List<Assessment> assessmentModel) {
        assessmentList = new ArrayList<>();
        assessmentList.addAll(assessmentModel);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_assessment_name, txt_id;
        CardView card_view;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_assessment_name = (TextView) itemView.findViewById(R.id.txt_assessment_name);
            txt_id = (TextView) itemView.findViewById(R.id.txt_id);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);

            if (mCommunicator != null) {
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCommunicator.onItemClickListener(txt_id.getText().toString(), txt_assessment_name.getText().toString());
                    }
                });
            }
        }
    }
}
