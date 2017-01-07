package com.example.dioncamposano17.loveadvice20;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String MyData = "MyData";

    public static void addSharedPrefs(Activity activity, String identifier, String data) {
        SharedPreferences prefs = activity.getSharedPreferences(MyData, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(identifier, data);
        editor.apply();
    }
    public static String getSharedPrefs(Activity activity, String identifier) {
        SharedPreferences prefs = activity.getSharedPreferences(MyData, Context.MODE_PRIVATE);
        return prefs.getString(identifier, "No Data");
    }
}
