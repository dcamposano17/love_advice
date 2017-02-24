package com.example.dioncamposano17.loveadvice20.assessment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.adapter.ChatAdapter;
import com.example.dioncamposano17.loveadvice20.model.Chat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txt_send;
    private EditText edt_message;
    private List<Chat> chatList = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private RecyclerView recycler_message;
    private Chat chat;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference root;
    private String user_chat_id, key;
    private SharedPreferences sharedPreferences;
    private static final String DEFAULT = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        onInitialize();
    }

    private void onInitialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        txt_send = (TextView) findViewById(R.id.txt_send);
        edt_message = (EditText) findViewById(R.id.edt_message);
        chatAdapter = new ChatAdapter(chatList, this);
        recycler_message = (RecyclerView) findViewById(R.id.recycler_message);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recycler_message.setLayoutManager(linearLayoutManager);
        recycler_message.setAdapter(chatAdapter);
        recycler_message.setItemAnimator(new DefaultItemAnimator());
        recycler_message.setHasFixedSize(true);
        if (getIntent().getStringExtra("user_chat_id") != null) {
            user_chat_id = getIntent().getStringExtra("user_chat_id");
        }
        edt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {
                    txt_send.setEnabled(false);
                    txt_send.setTextColor(getResources().getColor(R.color.main_color_gray_dk));
                } else {
                    txt_send.setEnabled(true);
                    txt_send.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });
        // onSetChatData();

        // Get reference in the firebase root!
        root = FirebaseDatabase.getInstance().getReference().child(user_chat_id);
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        onSendChat();
        //onDataChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void onSetChatData() {
        for (int i = 0; i < 10; i++) {
            chat = new Chat("1", "Sample message " + i, "1");
            chatList.add(chat);
        }
        chatAdapter.addAll(chatList);
    }

    private void onSendChat() {
        txt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.util.Log.e("onClick", "onClick");
//                chat = new Chat("1", edt_message.getText().toString());
//                chatList.add(chat);
//                chatAdapter.addAll(chatList);
//                linearLayoutManager.scrollToPosition(chatList.size() - 1);
//                edt_message.setText("");


                HashMap<String, Object> map = new HashMap<String, Object>();
                key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(key);
                HashMap<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", sharedPreferences.getString("name", DEFAULT));
                map2.put("message", edt_message.getText().toString());
                map2.put("message_type", "1");

                message_root.updateChildren(map2);
            }
        });


        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onChildAddedListener(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                onChildAddedListener(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void onChildAddedListener(DataSnapshot dataSnapshot) {
        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
//        Iterator i = dataSnapshot.getChildren().iterator();
//        String message;
//        while (i.hasNext()) {
//            chat = new Chat("1", String.valueOf(newPost.get("message")));
//            chatList.add(chat);
//        }
        chat = new Chat("1", String.valueOf(newPost.get("message")), String.valueOf(newPost.get("message_type")));
        chatList.add(chat);
        chatAdapter.addAll(chatList);
        linearLayoutManager.scrollToPosition(chatList.size() - 1);
        edt_message.setText("");
        //onUserNotify();
    }

    private void onUserNotify() {
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                requestCode,
                new Intent(this, ChatActivity.class),
                PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder noBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.love_letter)
                .setContentTitle("Chat message")
                .setContentText("New message arrived")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(sound);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        noBuilder.setVibrate(new long[] {0, 1000, 1000, 1000, 1000 });
        notificationManager.notify(0, noBuilder.build());
    }

    private void onDataChanged() {
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();

                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    chat = new Chat("1", ((DataSnapshot) i.next()).getKey(), "1");
                    chatList.add(chat);
                }

                while (i.hasNext()) {
                }
                chatAdapter.addAll(chatList);
                linearLayoutManager.scrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
