package com.example.dioncamposano17.loveadvice20.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.adapter.FamilyAdapter;
import com.example.dioncamposano17.loveadvice20.model.Family;

import java.util.ArrayList;
import java.util.List;

public class FamilyFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView familyRecycler;
    private FamilyAdapter familyAdapter;
    private List<Family> familyList = new ArrayList<>();


    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family, container, false);
        familyRecycler = (RecyclerView) view.findViewById(R.id.familyRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        familyRecycler.setHasFixedSize(true);
        familyAdapter = new FamilyAdapter(familyList, getActivity());
        familyRecycler.setLayoutManager(layoutManager);
        familyRecycler.setItemAnimator(new DefaultItemAnimator());
        familyRecycler.setAdapter(familyAdapter);
        loadItem();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        familyRecycler.setItemAnimator(itemAnimator);

    }
    private void loadItem() {
        String[] familyItem = getActivity().getResources().getStringArray(R.array.FamilyCategory);
        for (String itemName : familyItem) {
            Family family = new Family(itemName);
            familyList.add(family);
            familyAdapter.addAll(familyList);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Family> filteredModelList = filter(familyList, newText);
        familyAdapter.addAll(filteredModelList);
        familyAdapter.notifyDataSetChanged();
        return true;
    }
    private List<Family> filter(List<Family> models, String query) {
        query = query.toLowerCase();
        final List<Family> filteredModelList = new ArrayList<>();
        for (Family model : models) {
            final String text = model.getFamilyList().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}



