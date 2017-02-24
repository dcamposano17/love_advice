package com.example.dioncamposano17.loveadvice20.assessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dioncamposano17.loveadvice20.Callback;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.VolleyRequest;
import com.example.dioncamposano17.loveadvice20.quiz.Options;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txt_question, txt_num_question;
    private RadioGroup rdo_grp;
    private Button btn_next;
    private String id;
    private ArrayList questionList = new ArrayList<>();
    private ArrayList choiceList = new ArrayList<>();
    private ArrayList adviceList = new ArrayList<>();
    private int current_index = 0, total_item;
    private RadioButton rdo_1, rdo_2, rdo_3, rdo_4;
    private int choice_a_total = 0,
            choice_b_total = 0,
            choice_c_total = 0,
            choice_d_total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        onInitialize();
    }

    private void onInitialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_question = (TextView) findViewById(R.id.txt_question);
        txt_num_question = (TextView) findViewById(R.id.txt_num_question);
        rdo_grp = (RadioGroup) findViewById(R.id.rdo_grp);
        btn_next = (Button) findViewById(R.id.btn_next);
        rdo_1 = (RadioButton) findViewById(R.id.rdo_1);
        rdo_2 = (RadioButton) findViewById(R.id.rdo_2);
        rdo_3 = (RadioButton) findViewById(R.id.rdo_3);
        rdo_4 = (RadioButton) findViewById(R.id.rdo_4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        if (getIntent().getStringExtra("id") != null ||
                getIntent().getStringExtra("id").equalsIgnoreCase("")) {
            id = getIntent().getStringExtra("id");
        }
        onGetData();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdo_grp.getCheckedRadioButtonId() != -1) {

                    getCheckedRadio();
                    current_index++;
                    rdo_grp.clearCheck();
                    txt_num_question.setText("Question " + (current_index + 1) + " of " + total_item);

                    if (current_index == (total_item - 2)) {
                        btn_next.setText("Finish");
                    }

                    if (current_index < (total_item - 1)) {
                        txt_question.setText(String.valueOf(questionList.get(current_index)));
                        android.util.Log.e("current_index", String.valueOf(current_index));
                    } else {
                        android.util.Log.e("MAX", "YOU EXCEED THE LIMIT");
                        startActivity(new Intent(getApplicationContext(), ResultActivity.class)
                                .putExtra("title", getSupportActionBar().getTitle())
                                .putExtra("advice", String.valueOf(adviceList.get(getHighestChoice())))
                                .putExtra("best_advice", getBestAdvice()));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Choose your answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onGetData() {
        new VolleyRequest(QuizActivity.this, "get_question.php", "", "Loading assessment...")
                .onRequest(new Callback() {
                               @Override
                               public void onSuccess(String response) {
                                   android.util.Log.e("onSuccess", response);
                                   // Clear first the question list!
                                   questionList.clear();
                                   try {
                                       JSONObject jsonObject = new JSONObject(response);
                                       if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                                           JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                                           // Get the total item of question!
                                           total_item = jsonArray.length();
                                           android.util.Log.e("total_item", String.valueOf(total_item));
                                           for (int i = 0; i < jsonArray.length(); i++) {
                                               JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                               questionList.add(jsonObject1.getString("question"));
                                           }

                                           JSONArray jsonArray2 = new JSONArray(jsonObject.getString("data2"));
                                           for (int i = 0; i < jsonArray2.length(); i++) {
                                               JSONObject jsonObject1 = jsonArray2.getJSONObject(i);
                                               choiceList.add(jsonObject1.getString("choice"));
                                               adviceList.add(jsonObject1.getString("advice"));
                                           }

                                           // Shuffle the question inside the list!
                                           Collections.shuffle(questionList);
                                           txt_question.setText(String.valueOf(questionList.get(0)));
                                           // Set the radio text!
                                           rdo_1.setText(String.valueOf(choiceList.get(0)));
                                           rdo_2.setText(String.valueOf(choiceList.get(1)));
                                           rdo_3.setText(String.valueOf(choiceList.get(2)));
                                           rdo_4.setText(String.valueOf(choiceList.get(3)));
                                           txt_num_question.setText("Question " + (current_index + 1) + " of " + (total_item));
                                       }
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }

                               @Override
                               public void onError(String response) {
                                   Toast.makeText(getApplicationContext(), "Can't connect in the server right now!", Toast.LENGTH_SHORT).show();
                                   android.util.Log.e("onError", response);
                                   startActivity(new Intent(getApplicationContext(), Options.class));
                                   finish();
                               }
                           }, new String[]{"assess_id",}
                        , new String[]{id}
                        , true);
    }

    private void getCheckedRadio() {
        if (rdo_1.isChecked()) {
            choice_a_total++;
        } else if (rdo_2.isChecked()) {
            choice_b_total++;
        } else if (rdo_3.isChecked()) {
            choice_c_total++;
        } else if (rdo_4.isChecked()) {
            choice_d_total++;
        }
    }

    // This method is to determine the highest choice of user!
    private int getHighestChoice() {
        int highest_choice;
        if (choice_a_total > choice_b_total) {
            highest_choice = 0;
        } else if (choice_b_total > choice_c_total) {
            highest_choice = 1;
        } else if (choice_c_total > choice_d_total) {
            highest_choice = 2;
        } else {
            highest_choice = 3;
        }
        return highest_choice;
    }

    private String getBestAdvice() {
        String advice = "";
        if (AssessmentActivity.assessment_type.equalsIgnoreCase("family")) {
            switch (getHighestChoice()) {
                case 0:
                    advice = getString(R.string.FamilyAdviceHigh);
                    break;
                case 1:
                    advice = getString(R.string.FamilyAdviceMod);
                    break;
                case 2:
                    advice = getString(R.string.FamilyAdviceLow);
                    break;
                case 3:
                    advice = getString(R.string.FamilyAdviceLow);
                    break;
            }
        } else if (AssessmentActivity.assessment_type.equalsIgnoreCase("friends")) {
            switch (getHighestChoice()) {
                case 0:
                    advice = getString(R.string.FriendsAdviceHigh);
                    break;
                case 1:
                    advice = getString(R.string.FriendsAdviceMod);
                    break;
                case 2:
                    advice = getString(R.string.FriendsAdviceLow);
                    break;
                case 3:
                    advice = getString(R.string.FriendsAdviceLow);
                    break;
            }
        } else {
            switch (getHighestChoice()) {
                case 0:
                    advice = getString(R.string.SpecialAdviceHigh);
                    break;
                case 1:
                    advice = getString(R.string.SpecialAdviceMod);
                    break;
                case 2:
                    advice = getString(R.string.SpecialAdviceLow);
                    break;
                case 3:
                    advice = getString(R.string.SpecialAdviceLow);
                    break;
            }
        }
        return advice;
    }

}
