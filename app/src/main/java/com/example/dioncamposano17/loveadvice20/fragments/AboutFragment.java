package com.example.dioncamposano17.loveadvice20.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dioncamposano17.loveadvice20.MainActivity;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.RevealLayout;
import com.example.dioncamposano17.loveadvice20.WifiManagerClass;

import java.util.Locale;

public class AboutFragment extends Fragment {

    int result;
    public static TextToSpeech textToSpeech;
    FragmentManager manager;
    Thread thread;
    private RevealLayout revealLayout;


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        killTextToSpeech();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        killTextToSpeech();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        killTextToSpeech();
    }


    private void init() {
        startThread();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        revealLayout = (RevealLayout)view.findViewById(R.id.reveal_layout);
        initRevealLayout();
        return view;
    }

    private void setTextToSpeech() {
        killTextToSpeech();
        textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.UK);
                    String aboutText = getResources().getString(R.string.about);
                    textToSpeech.speak(aboutText, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getActivity(), "No feature supported !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void killTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private void startThread() {
        thread = new Thread() {
            @Override
            public void run() {

                try {

                    int waited = 0;

                    while (waited < 200) {
                        sleep(100);
                        waited += 100;
                    }

                    setTextToSpeech();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
    private void initRevealLayout(){

        revealLayout.setOnClickListener(null);
        revealLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    Log.d("AboutFragment", "x: " + event.getX() + ", y: " + event.getY());
                    revealLayout.next((int) event.getX(), (int) event.getY());
                }
                return false;
            }
        });
    }


}
