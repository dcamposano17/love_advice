package com.example.dioncamposano17.loveadvice20;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {

    Button btnReg;
    FragmentManager manager;
    EditText studno,lname,fname,mname,program,gender;
    TextInputLayout inputLayoutStudno,inputLayoutLname,inputLayoutFname,inputLayoutMname,inputLayoutProg,inputLayoutGender;
    SaveInformation saveInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnReg = (Button)findViewById(R.id.btnReg);
        studno = (EditText)findViewById(R.id.input_studno);
        lname = (EditText)findViewById(R.id.input_lastname);
        fname = (EditText)findViewById(R.id.input_firstname);
        mname = (EditText)findViewById(R.id.input_middlename);
        program = (EditText)findViewById(R.id.input_program);
        gender = (EditText)findViewById(R.id.input_gender);
        inputLayoutStudno = (TextInputLayout)findViewById(R.id.input_layout_studno);
        inputLayoutLname = (TextInputLayout)findViewById(R.id.input_layout_lastname);
        inputLayoutFname = (TextInputLayout)findViewById(R.id.input_layout_firstname);
        inputLayoutMname = (TextInputLayout)findViewById(R.id.input_layout_middlename);
        inputLayoutProg = (TextInputLayout)findViewById(R.id.input_layout_program);
        inputLayoutGender = (TextInputLayout)findViewById(R.id.input_layout_gender);
        saveInformation = new SaveInformation(this);
        clickRegisterListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        showDialog();
    }

//    public void onClick(View v) {
//        String getStudno = studno.getText().toString().trim();
//        String getLname = lname.getText().toString().trim();
//        String getFname = fname.getText().toString().trim();
//        String getMname = mname.getText().toString().trim();
//        String getProg = program.getText().toString().trim();
//        String getGender = gender.getText().toString().trim();
//        if (v == btnReg) {
//            if (getStudno.length() == 0 && getLname.length() == 0 && getFname.length() == 0 && getMname.length() == 0 && getProg.length() == 0 && getGender.length() == 0) {
//                studno.setError("Student Number is required!");
//                lname.setError("Last name is required!");
//                fname.setError("First name is required!");
//                mname.setError("Middle name is required!");
//                program.setError("Program is required!");
//                gender.setError("Gender is required!");
//                Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
//            } else if (getStudno.length() == 0) {
//                studno.setError("Student Number is required!");
//            } else if (getLname.length() == 0) {
//                lname.setError("Last name is required!");
//            } else if (getFname.length() == 0) {
//                fname.setError("First name is required!");
//            } else if (getMname.length() == 0) {
//                mname.setError("Middle name is required!");
//            } else if (getProg.length() == 0) {
//                program.setError("Program is required!");
//            } else if (getGender.length() == 0) {
//                gender.setError("Gender is required!");
//            }
//        } else {
//            saveInformation.execute("login",
//                    studno.getText().toString(), lname.getText().toString(),
//                    fname.getText().toString(), mname.getText().toString(),
//                    program.getText().toString(), gender.getText().toString());
//            Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cancel Registration")
                    .setIcon(R.drawable.ic_question)
                    .setMessage("Do you want to cancel registration?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                            Toast.makeText(getApplicationContext(), "Registration Cancelled", Toast.LENGTH_SHORT).show();
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

    public void showDialog() {

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        manager = getFragmentManager();
        WifiManagerClass wifiManagerClass = new WifiManagerClass();

        if (!wifi.isWifiEnabled()) {

            if (wifiManagerClass == null)
                wifiManagerClass.show(manager, "WifiManager");

        }
    }
    private void clickRegisterListener(){
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getStudno = studno.getText().toString().trim();
                String getLname = lname.getText().toString().trim();
                String getFname = fname.getText().toString().trim();
                String getMname = mname.getText().toString().trim();
                String getProg = program.getText().toString().trim();
                String getGender = gender.getText().toString().trim();
                if (getStudno.length() == 0 && getLname.length() == 0 && getFname.length() == 0 && getMname.length() == 0 && getProg.length() == 0 && getGender.length() == 0) {
                    studno.setError("Student Number is required!");
                    lname.setError("Last name is required!");
                    fname.setError("First name is required!");
                    mname.setError("Middle name is required!");
                    program.setError("Program is required!");
                    gender.setError("Gender is required!");
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (getStudno.length() == 0) {
                    studno.setError("Student Number is required!");
                } else if (getLname.length() == 0) {
                    lname.setError("Last name is required!");
                } else if (getFname.length() == 0) {
                    fname.setError("First name is required!");
                } else if (getMname.length() == 0) {
                    mname.setError("Middle name is required!");
                } else if (getProg.length() == 0) {
                    program.setError("Program is required!");
                } else if (getGender.length() == 0) {
                    gender.setError("Gender is required!");
                } else {
                    saveInformation.execute("login",
                            studno.getText().toString(), lname.getText().toString(),
                            fname.getText().toString(), mname.getText().toString(),
                            program.getText().toString(), gender.getText().toString());
                    Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
    }
    public class SaveInformation extends AsyncTask<String, Void, String> {
        Context context;
        Dialog dialog;
        String stud_no, lastname, firstname, middlename, program, gender;

        SaveInformation(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... voids) {
            String method = voids[0];

            String loginUrl = "http://192.168.43.245/LAfinal/insertStudents.php";

            if(method.equals("login")){
                stud_no = voids[1];
                lastname = voids[2];
                firstname = voids[3];
                middlename = voids[4];
                program = voids[5];
                gender = voids[6];

                try {
                    URL url = new URL(loginUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("stud_no", "UTF-8") + "=" + URLEncoder.encode(stud_no, "UTF-8")
                            + "&" +
                            URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8")
                            + "&" +
                            URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(firstname, "UTF-8")
                            + "&" +
                            URLEncoder.encode("middlename", "UTF-8") + "=" + URLEncoder.encode(middlename, "UTF-8")
                            + "&" +
                            URLEncoder.encode("program", "UTF-8") + "=" + URLEncoder.encode(program, "UTF-8")
                            + "&" +
                            URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line;
                    while ((line = bufferReader.readLine()) != null) {
                        response += line + "\n";
                    }
                    bufferReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return  null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();

            if (result == null) {

                manager = getFragmentManager();
                WifiManagerClass wifiManagerClass = new WifiManagerClass();
                wifiManagerClass.show(manager, "WifiManager");

            } else {
                android.util.Log.e("result", result);
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "", "Registering... Please Wait");
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
