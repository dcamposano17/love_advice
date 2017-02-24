package com.example.dioncamposano17.loveadvice20.assessment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.quiz.Options;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class ResultActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txt_advice;
    private String bestAdvice, title;
    private Button btn_chat, btn_advice;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private SharedPreferences sharedPreferences;
    private static final String DEFAULT = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        onInitialize();
    }

    private void onInitialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        btn_advice = (Button) findViewById(R.id.btn_advice);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        txt_advice = (TextView) findViewById(R.id.txt_advice);
        txt_advice.setText(getIntent().getStringExtra("advice"));
        bestAdvice = getIntent().getStringExtra("best_advice");
        title = getIntent().getStringExtra("title");
        onChatButtonClick();
        onReadAdviceClick();
        notifyChange();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Options.class));
        finish();
    }

    private void onReadAdviceClick() {
        btn_advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(ResultActivity.this);
                dialog.setTitle("Best advice")
                        .setMessage(bestAdvice)
                        .setIcon(R.drawable.love_letter)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void onChatButtonClick() {
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                String user_chat_id = "user_chat_id_" + sharedPreferences.getString("stud_no", DEFAULT);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put(user_chat_id, "");

                root.updateChildren(map);

                startActivity(new Intent(getApplicationContext(), ChatActivity.class)
                        .putExtra("title", title)
                        .putExtra("user_chat_id", user_chat_id));
            }
        });
    }

    private void notifyChange() {
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();

                Iterator i = dataSnapshot.getChildren().iterator();


                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }

                android.util.Log.e("notifyChange", String.valueOf(set));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
