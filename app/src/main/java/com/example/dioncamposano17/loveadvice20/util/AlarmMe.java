package com.example.dioncamposano17.loveadvice20.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.dioncamposano17.loveadvice20.MainActivity;
import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.adapter.AlarmListAdapter;
import com.example.dioncamposano17.loveadvice20.fragments.AboutFragment;
import com.example.dioncamposano17.loveadvice20.util.Preferences;

/**
 * Created by dioncamposano17 on 18/01/2017.
 */

public class AlarmMe extends AppCompatActivity {

    private final String TAG = "AlarmMe";

    private ListView mAlarmList;
    private AlarmListAdapter mAlarmListAdapter;
    private Alarm mCurrentAlarm;

    private final int NEW_ALARM_ACTIVITY = 0;
    private final int EDIT_ALARM_ACTIVITY = 1;
    private final int PREFERENCES_ACTIVITY = 2;
    private final int ABOUT_ACTIVITY = 3;

    private final int CONTEXT_MENU_EDIT = 0;
    private final int CONTEXT_MENU_DELETE = 1;
    private final int CONTEXT_MENU_DUPLICATE = 2;

    public static final String RINGTONE_PREF = "ringtone_pref";
    public static SharedPreferences sp;

    public static Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        Log.i(TAG, "AlarmMe.onCreate()");

        mAlarmList = (ListView)findViewById(R.id.alarm_list);

        mAlarmListAdapter = new AlarmListAdapter(this);
        mAlarmList.setAdapter(mAlarmListAdapter);
        mAlarmList.setOnItemClickListener(mListOnItemClickListener);
        registerForContextMenu(mAlarmList);

        mCurrentAlarm = null;
    }

    public static String getRingtone() {
        return sp.getString(RINGTONE_PREF, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.toString());
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "AlarmMe.onDestroy()");
//    mAlarmListAdapter.save();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "AlarmMe.onResume()");
        mAlarmListAdapter.updateAlarms();
    }

    public void onAddAlarmClick(View view)
    {
        Intent intent = new Intent(getBaseContext(), EditAlarm.class);

        mCurrentAlarm = new Alarm(this);
        mCurrentAlarm.toIntent(intent);

        AlarmMe.this.startActivityForResult(intent, NEW_ALARM_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarm.fromIntent(data);
                mAlarmListAdapter.add(mCurrentAlarm);
            }
            mCurrentAlarm = null;
        }
        else if (requestCode == EDIT_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarm.fromIntent(data);
                mAlarmListAdapter.update(mCurrentAlarm);
            }
            mCurrentAlarm = null;
        }
        else if (requestCode == PREFERENCES_ACTIVITY)
        {
            mAlarmListAdapter.onSettingsUpdated();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_settings == item.getItemId()) {
            Intent intent = new Intent(getBaseContext(), Preferences.class);
            startActivityForResult(intent, PREFERENCES_ACTIVITY);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        if (v.getId() == R.id.alarm_list)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(mAlarmListAdapter.getItem(info.position).getTitle());
            menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE, "Edit");
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
            menu.add(Menu.NONE, CONTEXT_MENU_DUPLICATE, Menu.NONE, "Duplicate");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = item.getItemId();

        if (index == CONTEXT_MENU_EDIT)
        {
            Intent intent = new Intent(getBaseContext(), EditAlarm.class);

            mCurrentAlarm = mAlarmListAdapter.getItem(info.position);
            mCurrentAlarm.toIntent(intent);
            startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
        else if (index == CONTEXT_MENU_DELETE)
        {
            mAlarmListAdapter.delete(info.position);
        }
        else if (index == CONTEXT_MENU_DUPLICATE)
        {
            Alarm alarm = mAlarmListAdapter.getItem(info.position);
            Alarm newAlarm = new Alarm(this);
            Intent intent = new Intent();

            alarm.toIntent(intent);
            newAlarm.fromIntent(intent);
            newAlarm.setTitle(alarm.getTitle() + " (copy)");
            mAlarmListAdapter.add(newAlarm);
        }

        return true;
    }

    private AdapterView.OnItemClickListener mListOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(getBaseContext(), EditAlarm.class);

            mCurrentAlarm = mAlarmListAdapter.getItem(position);
            mCurrentAlarm.toIntent(intent);
            AlarmMe.this.startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Back to Main")
                    .setIcon(R.drawable.ic_cancel_black_36dp)
                    .setMessage("Leave Reminders?")
                    .setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("Stay Here", new DialogInterface.OnClickListener() {
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
