package com.example.dioncamposano17.loveadvice20.reminder.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by dioncamposano17 on 23/01/2017.
 */

public class PhoneStateChangedBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getSimpleName(), "onReceive()");
    }
}
