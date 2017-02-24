package com.example.dioncamposano17.loveadvice20;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    Button btnReg;
    FragmentManager manager;
    EditText studno, lname, fname, mname, program;
    TextInputLayout inputLayoutStudno, inputLayoutLname, inputLayoutFname, inputLayoutMname, inputLayoutProg, inputLayoutGender;
    RadioGroup radioG, radioG2;
    RadioButton radioM, radioF, radioStem, radioMawd, radioDigar;
    private String gender2, prog2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnReg = (Button) findViewById(R.id.btnReg);
        studno = (EditText) findViewById(R.id.input_studno);
        lname = (EditText) findViewById(R.id.input_lastname);
        fname = (EditText) findViewById(R.id.input_firstname);
        mname = (EditText) findViewById(R.id.input_middlename);
        radioG = (RadioGroup) findViewById(R.id.radioG);
        radioG2 = (RadioGroup) findViewById(R.id.radioG2);
        radioM = (RadioButton) findViewById(R.id.radioMale);
        radioF = (RadioButton) findViewById(R.id.radioFemale);
        radioMawd = (RadioButton) findViewById(R.id.radioMawd);
        radioStem = (RadioButton) findViewById(R.id.radioStem);
        radioDigar = (RadioButton) findViewById(R.id.radioDigar);
        inputLayoutStudno = (TextInputLayout) findViewById(R.id.input_layout_studno);
        inputLayoutLname = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        inputLayoutFname = (TextInputLayout) findViewById(R.id.input_layout_firstname);
        inputLayoutMname = (TextInputLayout) findViewById(R.id.input_layout_middlename);
        clickRegisterListener();

        radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton radioButton = (RadioButton) findViewById(id);
                gender2 = radioButton.getText().toString();
            }
        });
        radioG2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton radioButton = (RadioButton) findViewById(id);
                prog2 = radioButton.getText().toString();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog();
    }

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

    private void clickRegisterListener() {
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getStudno = studno.getText().toString().trim();
                String getLname = lname.getText().toString().trim();
                String getFname = fname.getText().toString().trim();
                String getMname = mname.getText().toString().trim();
                if (getStudno.length() == 0 && getLname.length() == 0 && getFname.length() == 0 && getMname.length() == 0 && prog2.equalsIgnoreCase("") && gender2.equalsIgnoreCase("")) {
                    studno.setError("Student Number is required!");
                    lname.setError("Last name is required!");
                    fname.setError("First name is required!");
                    mname.setError("Middle name is required!");
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (getStudno.length() == 0) {
                    studno.setError("Student Number is required!");
                } else if (getLname.length() == 0) {
                    lname.setError("Last name is required!");
                } else if (getFname.length() == 0) {
                    fname.setError("First name is required!");
                } else if (getMname.length() == 0) {
                    mname.setError("Middle name is required!");
                } else if (prog2.equalsIgnoreCase("") && gender2.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "These fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    onRegisterStudent(getStudno, getLname, getFname, getMname, gender2, prog2);
                }
            }
        });
    }

    private void onRegisterStudent(String stud_no, String lastname, String firstname, String middlename, String program, String gender) {
        new VolleyRequest(RegisterActivity.this, "register_student.php", "", "Inserting Data... Please Wait")
                .onRequest(new Callback() {
                               @Override
                               public void onSuccess(String response) {
                                   android.util.Log.wtf("onSuccess", response);
                                   try {
                                       JSONObject jsonObject = new JSONObject(response);
                                       if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                                           Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                           startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                       }
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }
                               @Override
                               public void onError(String response) {
                                   android.util.Log.e("onError", response);
                               }
                           }, new String[]{"stud_no", "lastname", "firstname", "middlename", "program", "gender"},
                        new String[]{stud_no, lastname, firstname, middlename, program, gender}, true);
    }
}
