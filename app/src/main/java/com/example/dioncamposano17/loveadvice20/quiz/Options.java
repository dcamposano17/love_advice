package com.example.dioncamposano17.loveadvice20.quiz;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dioncamposano17.loveadvice20.LoginActivity;
import com.example.dioncamposano17.loveadvice20.MainActivity;
import com.example.dioncamposano17.loveadvice20.R;

/**
 * Created by dioncamposano17 on 20/01/2017.
 */

public class Options extends Activity{

    Button btnFamily,btnFriends,btnSpecial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu);

        btnFamily = (Button) findViewById(R.id.btnFamily);
        btnFriends = (Button) findViewById(R.id.btnFriends);
        btnSpecial = (Button) findViewById(R.id.btnSpecial);

        btnFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Options.this,
                        QuizActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Family Category", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Options.this,
                        QuizActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Friends Category", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Options.this,
                        QuizActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Special Ones Category", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Back to Main")
                    .setIcon(R.drawable.ic_cancel_black_36dp)
                    .setMessage("Don't you want to continue assessment?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setCancelable(true)
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
