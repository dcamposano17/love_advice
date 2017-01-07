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
import com.example.dioncamposano17.loveadvice20.adapter.SpecialAdapter;
import com.example.dioncamposano17.loveadvice20.model.Special;

import java.util.ArrayList;
import java.util.List;

public class SpecialFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView specialRecycler;
    private SpecialAdapter specialAdapter;
    private List<Special> specialList = new ArrayList<>();

    public SpecialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_special, container, false);
        specialRecycler = (RecyclerView) view.findViewById(R.id.specialRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        specialAdapter = new SpecialAdapter(specialList, getActivity());
        specialRecycler.setLayoutManager(layoutManager);
        specialRecycler.setItemAnimator(new DefaultItemAnimator());
        specialRecycler.setAdapter(specialAdapter);
        loadItem();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    private void loadItem() {
        String[] specialItem = getActivity().getResources().getStringArray(R.array.SpecialCategory);
        for (String itemName : specialItem) {
            Special special = new Special(itemName);
            specialList.add(special);
            specialAdapter.addAll(specialList);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Special> filteredModelList = filter(specialList, newText);
        specialAdapter.addAll(filteredModelList);
        specialAdapter.notifyDataSetChanged();
        return true;
    }
    private List<Special> filter(List<Special> models, String query) {
        query = query.toLowerCase();
        final List<Special> filteredModelList = new ArrayList<>();
        for (Special model : models) {
            final String text = model.getSpecialList().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }





}
