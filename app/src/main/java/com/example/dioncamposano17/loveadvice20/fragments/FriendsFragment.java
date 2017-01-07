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
import com.example.dioncamposano17.loveadvice20.adapter.FriendsAdapter;
import com.example.dioncamposano17.loveadvice20.model.Friends;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView friendsRecycler;
    private FriendsAdapter friendsAdapter;
    private List<Friends> friendList = new ArrayList<>();

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        friendsRecycler = (RecyclerView) view.findViewById(R.id.friendsRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        friendsAdapter = new FriendsAdapter(friendList, getActivity());
        friendsRecycler.setLayoutManager(layoutManager);
        friendsRecycler.setItemAnimator(new DefaultItemAnimator());
        friendsRecycler.setAdapter(friendsAdapter);
        loadItem();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void loadItem() {
        String[] friendItem = getActivity().getResources().getStringArray(R.array.FriendsCategory);
        for (String itemName : friendItem) {
            Friends friends = new Friends(itemName);
            friendList.add(friends);
            friendsAdapter.addAll(friendList);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Friends> filteredModelList = filter(friendList, newText);
        friendsAdapter.addAll(filteredModelList);
        friendsAdapter.notifyDataSetChanged();
        return true;
    }

    private List<Friends> filter(List<Friends> models, String query) {
        query = query.toLowerCase();
        final List<Friends> filteredModelList = new ArrayList<>();
        for (Friends model : models) {
            final String text = model.getFriendsList().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}



