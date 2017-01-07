package com.example.dioncamposano17.loveadvice20;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class WifiManagerClass extends DialogFragment implements View.OnClickListener {

    Button btnOk, btnSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.custom_alert, null);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnSettings = (Button) view.findViewById(R.id.btnSettings);
        btnOk.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        return view;
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnOk){
            dismiss();
        } else {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            dismiss();
        }
    }
}
