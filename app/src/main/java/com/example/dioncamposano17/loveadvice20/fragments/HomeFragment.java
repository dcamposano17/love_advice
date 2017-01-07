package com.example.dioncamposano17.loveadvice20.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.adapter.BaseAdapterClassThree;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class HomeFragment extends Fragment {

    private static final String DEFAULT = "N/A";
    ListView listInfo;
    String setStudNo, setName, setProgram, setGender;
    BaseAdapterClassThree baseAdapterClassThree;
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> data = new ArrayList<String>();
    ImageView imageHeader;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    public void init(){
        imageHeader = (ImageView) getActivity().findViewById(R.id.imageHeader);
        Picasso.with(getActivity())
                .load(R.drawable.home_img)
                .placeholder(R.drawable.home_img)
                .error(R.drawable.home_img)
                .into(imageHeader);
        listInfo = (ListView)getActivity().findViewById(R.id.listInfo);
        try {
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
            setStudNo = sharedPrefs.getString("stud_no", DEFAULT);
            setName = sharedPrefs.getString("name", DEFAULT);
            setProgram = sharedPrefs.getString("program", DEFAULT);
            setGender = sharedPrefs.getString("gender", DEFAULT);
            arr(setStudNo, setName, setProgram, setGender);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public void arr(String studno, String name, String program, String gender){
        String studentNumber = studno.substring(0, 3) + "-" + setStudNo.substring(3, 7) + "-" + setStudNo.substring(7);
        data.add(studentNumber);
        data.add(name);
        data.add(program);
        data.add(gender);
        title.add("Student Number:");
        title.add("Student Name:");
        title.add("Program:");
        title.add("Gender:");
        baseAdapterClassThree = new BaseAdapterClassThree(getActivity(), title, data);
        listInfo.setAdapter(baseAdapterClassThree);
    }
}
