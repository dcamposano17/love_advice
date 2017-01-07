package com.example.dioncamposano17.loveadvice20.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.adapter.GlossaryAdapter;
import com.example.dioncamposano17.loveadvice20.model.Glossary;

import java.util.ArrayList;
import java.util.List;


public class GlossaryFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerGlossary;
    private GlossaryAdapter glossaryAdapter;
    private List<Glossary> glossaryList = new ArrayList<>();

    public GlossaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glossary, container, false);
        recyclerGlossary = (RecyclerView) view.findViewById(R.id.recyclerGlossary);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        glossaryAdapter = new GlossaryAdapter(glossaryList, getActivity());
        recyclerGlossary.setLayoutManager(layoutManager);
        recyclerGlossary.setItemAnimator(new DefaultItemAnimator());
        recyclerGlossary.setAdapter(glossaryAdapter);
        loadItem();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        onSearchView(menu);
    }

    private void loadItem() {
        String[] glossaryItem = getActivity().getResources().getStringArray(R.array.GlossaryList);
        for (String itemName: glossaryItem) {
            Glossary glossary = new Glossary(itemName);
            glossaryList.add(glossary);
            glossaryAdapter.addAll(glossaryList);
        }
    }

    private void onSearchView(Menu menu) {
        MenuItem item1 = menu.findItem(R.id.action_search);
        item1.setVisible(true);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        glossaryAdapter.addAll(glossaryList);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true;
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Glossary> filteredModelList = filter(glossaryList, newText);
        glossaryAdapter.addAll(filteredModelList);
        glossaryAdapter.notifyDataSetChanged();
        return true;
    }

    private List<Glossary> filter(List<Glossary> models, String query) {
        query = query.toLowerCase();
        final List<Glossary> filteredModelList = new ArrayList<>();
        for (Glossary model : models) {
            final String text = model.getGlossaryList().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
