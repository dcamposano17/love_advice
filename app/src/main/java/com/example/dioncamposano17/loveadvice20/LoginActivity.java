package com.example.dioncamposano17.loveadvice20;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText inputUsername, inputPassword;
    private TextInputLayout inputLayoutUser, inputLayoutPass;
    private Button btnLogin, btnReg;
    private ImageView imageEye, imageEyeOff;
    private static final String DEFAULT = "N/A";
    String stud_no, middlename;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageEye = (ImageView) findViewById(R.id.imageEye);
        imageEyeOff = (ImageView) findViewById(R.id.imageEyeOff);
        imageEye.setVisibility(View.GONE);
        imageEyeOff.setVisibility(View.GONE);
        inputLayoutUser = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputUsername = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputUsername.addTextChangedListener(new MyTextWatcher(inputUsername));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnReg = (Button) findViewById(R.id.btnReg);
        onFocusChangeListener();
        onTextWatcherListener();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "Registration Form", Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        stud_no = sharedPreferences.getString("stud_no", DEFAULT);
        middlename = sharedPreferences.getString("middlename", DEFAULT);

        if(!stud_no.equals(DEFAULT) || !middlename.equals(DEFAULT)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    public void onFocusChangeListener() {
        inputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    imageEye.setVisibility(View.VISIBLE);
                } else {
                    imageEye.setVisibility(View.GONE);
                    imageEyeOff.setVisibility(View.GONE);
                    inputPassword.setInputType(0x00000081);

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog();
    }

    public void showDialog(){
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        manager = getFragmentManager();
        WifiManagerClass wifiManagerClass = new WifiManagerClass();

        if(!wifi.isWifiEnabled()){
            if(wifiManagerClass == null)
                wifiManagerClass.show(manager, "WifiManager");
        }
    }

    private void submitForm(){

        String getUser = inputUsername.getText().toString().trim();
        String getPass = inputPassword.getText().toString().trim();

        String method = "login";
        BackGroundTask backGroundTask = new BackGroundTask(this);

        if(!validateUsername()){
            if (getUser.length() == 0){
                inputUsername.setError("student number is required");
            }
            return;
        }
        else if(!validatePassword()){
            if (getPass.length() == 0){
                inputPassword.setError("password is required");
            }
            return;
        } else {
            String finalUser = getUser.replaceAll("-", "");
            backGroundTask.execute(method, finalUser, getPass);
        }
    }

    private boolean validateUsername(){
        if (inputUsername.getText().toString().trim().isEmpty()){
            inputLayoutUser.setError(getString(R.string.err_msg_user));
            requestFocus(inputUsername);
            return false;
        } else {
            inputLayoutUser.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePassword(){
        if (inputPassword.getText().toString().trim().isEmpty()){
            inputLayoutPass.setError(getString(R.string.err_msg_pass));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }
        return true;
    }
    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onTextWatcherListener() {
        inputUsername.addTextChangedListener(new TextWatcher() {
            int len = 0;
            String a = inputUsername.getText().toString();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                len = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 3 && len < editable.length()) {
                    editable.append("-");
                }

                if (editable.length() == 8 && len < editable.length()) {
                    editable.append("-");
                }
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view){
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {

            switch (view.getId()) {
                case R.id.input_username:
                    validateUsername();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
    public void onClick(View v){
        if (v.getId() == R.id.imageEye){
            if (imageEyeOff.getVisibility() == View.GONE) {
                imageEyeOff.setVisibility(View.VISIBLE);
                imageEye.setVisibility(View.GONE);
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        } else if (v.getId() == R.id.imageEyeOff){
            if (imageEye.getVisibility() == View.GONE) {
                imageEyeOff.setVisibility(View.GONE);
                imageEye.setVisibility(View.VISIBLE);
                inputPassword.setInputType(0x00000081);
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit App")
                    .setIcon(R.drawable.ic_cancel_black_36dp)
                    .setMessage("Do you really want to exit?")
                    .setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AppExit();
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
    public void AppExit() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

    public class BackGroundTask extends AsyncTask<String, Void, String>{
        Context context;
        AlertDialog alertDialog;
        Dialog loadingDialog;

        BackGroundTask(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... voids) {

            String method = voids[0];
            String loginUrl = "http://192.168.43.245/LAfinal/login_student.php";

            if(method.equals("login")){
                String stud_no = voids[1];
                String middlename = voids[2];

                try {
                    URL url = new URL(loginUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("stud_no", "UTF-8") + "=" + URLEncoder.encode(stud_no, "UTF-8") + "&" +
                            URLEncoder.encode("middlename", "UTF-8") + "=" + URLEncoder.encode(middlename, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line + "\n";
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            if(result == null){
                loadingDialog.dismiss();

                manager = getFragmentManager();
                WifiManagerClass wifiManagerClass = new WifiManagerClass();
                wifiManagerClass.show(manager, "WifiManager");

            } else {
                android.util.Log.e("result", result);

                String status;

                loadingDialog.dismiss();

                try {
                    JSONObject response = new JSONObject(result);

                    status = response.getString("status");

                    if(status.equals("success")) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

                        loadingDialog.dismiss();

                        Intent i = new Intent(context, MainActivity.class);

                        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("stud_no", response.getString("stud_no"));
                        editor.putString("name", response.getString("lastname") + " " + response.getString("firstname") + " " + response.getString("middlename"));
                        editor.putString("program", response.getString("program"));
                        editor.putString("gender", response.getString("gender"));
                        editor.apply();
                        i.putExtra("jsondata", result);

                        context.startActivity(i);
                    } else {
                        alertDialog = new AlertDialog.Builder(context).create();

                        String a = "Incorrect student number or password. Please try again.";
                        alertDialog.setMessage(a);
                        alertDialog.show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(context, "", "Loading... Please Wait");
        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
    }
}
