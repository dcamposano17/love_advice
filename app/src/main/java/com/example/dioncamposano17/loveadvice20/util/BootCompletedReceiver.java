package com.example.dioncamposano17.loveadvice20.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dioncamposano17.loveadvice20.adapter.AlarmListAdapter;

/**
 * Created by dioncamposano17 on 18/01/2017.
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // just create AlarmListAdapter and it will load alarms and start them
        new AlarmListAdapter(context);
    }
}
