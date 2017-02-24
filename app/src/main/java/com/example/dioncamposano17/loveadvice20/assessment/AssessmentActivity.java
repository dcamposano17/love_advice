package com.example.dioncamposano17.loveadvice20.assessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dioncamposano17.loveadvice20.Callback;
import com.example.dioncamposano17.loveadvice20.Communicator;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.VolleyRequest;
import com.example.dioncamposano17.loveadvice20.adapter.AssessmentAdapter;
import com.example.dioncamposano17.loveadvice20.model.Assessment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssessmentActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private SwipeRefreshLayout swipe_refresh;
    private AssessmentAdapter assessmentAdapter;
    private List<Assessment> assessmentList = new ArrayList<>();
    private Toolbar toolbar;
    public static String assessment_type;
    private LinearLayout linear_layout;
    private TextView txt_error_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        if (getIntent().getStringExtra("title").equalsIgnoreCase("family assessment")) {
            assessment_type = "family";
        } else if (getIntent().getStringExtra("title").equalsIgnoreCase("friends assessment")) {
            assessment_type = "friends";
        } else {
            assessment_type = "special someone";
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
        txt_error_message = (TextView) findViewById(R.id.txt_error_message);
        assessmentAdapter = new AssessmentAdapter(assessmentList, this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(assessmentAdapter);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setHasFixedSize(true);

        onInitialize();
        onLoadAssessment(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onInitialize() {
        onSwipeListener();
        onItemClickListener();
    }


    // Swipe refresh listener
    private void onSwipeListener() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onLoadAssessment(false);
            }
        });
    }

    // Get all the available family assessment in the web server!
    private void onLoadAssessment(final boolean flag) {
        new VolleyRequest(AssessmentActivity.this, "get_assessment.php", "", "Loading assessment...")
                .onRequest(new Callback() {
                               @Override
                               public void onSuccess(String response) {
                                   android.util.Log.e("onSuccess", response);
                                   if (!flag) {
                                       swipe_refresh.setRefreshing(false);
                                   }
                                   // Clear the arraylist first before anything else!
                                   assessmentList.clear();
                                   try {
                                       JSONObject jsonObject = new JSONObject(response);
                                       if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                                           linear_layout.setVisibility(View.VISIBLE);
                                           txt_error_message.setVisibility(View.GONE);
                                           JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                                           for (int i = 0; i < jsonArray.length(); i++) {
                                               JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                               assessmentList.add(new Assessment(
                                                       jsonObject1.getString("id"),
                                                       jsonObject1.getString("assessment_name")
                                               ));
                                           }
                                           assessmentAdapter.addAll(assessmentList);
                                       } else {
                                           // Display the error message!
                                           linear_layout.setVisibility(View.GONE);
                                           txt_error_message.setVisibility(View.VISIBLE);
                                           txt_error_message.setText("No available assessment");
                                       }
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }

                               @Override
                               public void onError(String response) {
                                   android.util.Log.e("onError", response);
                                   // Display the error message!
                                   linear_layout.setVisibility(View.GONE);
                                   txt_error_message.setVisibility(View.VISIBLE);
                                   txt_error_message.setText("Can't connect in the server");
                                   if (!flag) {
                                       swipe_refresh.setRefreshing(false);
                                   }
                               }
                           }, new String[]{"id", "assess_type"}
                        , new String[]{"1", assessment_type}
                        , flag);
    }

    private void onItemClickListener() {
        assessmentAdapter.onItemClickListener(new Communicator() {
            @Override
            public void onItemClickListener(String id, String name) {
                android.util.Log.e("onItemClickListener", "ID: " + id + " NAME: " + name);
                startActivity(new Intent(getApplicationContext(), QuizActivity.class)
                        .putExtra("id", id)
                        .putExtra("name", name));
            }
        });
    }

}
