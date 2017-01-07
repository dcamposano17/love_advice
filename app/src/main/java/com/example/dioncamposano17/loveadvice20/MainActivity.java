package com.example.dioncamposano17.loveadvice20;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.dioncamposano17.loveadvice20.fragments.AboutFragment;
import com.example.dioncamposano17.loveadvice20.fragments.CategoriesFragment;
import com.example.dioncamposano17.loveadvice20.fragments.GlossaryFragment;
import com.example.dioncamposano17.loveadvice20.fragments.HomeFragment;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout main_page;
    FragmentManager manager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        quotesOfTheDay();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
        main_page = (FrameLayout)findViewById(R.id.main_container);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        onLoadHomePage();
    }
    private void quotesOfTheDay(){
        final MediaPlayer soundQuotes = MediaPlayer.create(this, R.raw.ding);
        String[] listOfQuotes = getResources().getStringArray(R.array.QuotesList);
        final Random random = new Random();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Quotes for Today")
                .setMessage(listOfQuotes[random.nextInt(37)])
                .setIcon(R.drawable.love_letter)
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
        soundQuotes.start();
        soundQuotes.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public void onLoadHomePage(){
        main_page = (FrameLayout)findViewById(R.id.main_container);
        setHomeAnimation();
    }
    public void setHomeAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        main_page.startAnimation(animation);
    }
    public void setAboutAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        main_page.startAnimation(animation);
    }
    public void setCategoriesAnim(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        main_page.startAnimation(animation);
    }
    public void setGlossaryAnim(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        main_page.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_logout:
                SharedPreferences sharedPreferences2 = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                setHomeAnimation();
                toolbar.setTitle(getString(R.string.Home));
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AboutFragment()).commit();
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                setAboutAnimation();
                toolbar.setTitle(getString(R.string.About));
                break;
            case R.id.nav_categories:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new CategoriesFragment()).commit();
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                setCategoriesAnim();
                toolbar.setTitle(getString(R.string.Categories));
                break;
            case R.id.nav_glossary:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new GlossaryFragment()).commit();
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                setGlossaryAnim();
                toolbar.setTitle(getString(R.string.Glossary));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit Main")
                    .setIcon(R.drawable.ic_cancel_black_36dp)
                    .setMessage("Do you really want to logout?")
                    .setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences sharedPreferences2 = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences2.edit();
                            editor.clear();
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
