package com.example.dioncamposano17.loveadvice20;

import android.app.Activity;
import android.app.ProgressDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MicoRodriguez on 12/24/2016.
 */
public class VolleyRequest {

    private Activity mActivity;
    private ProgressDialog progressDialog;
    private String url, mTitle, mMessage;

    public VolleyRequest(Activity mActivity, String url, String mTitle, String mMessage) {
        this.mActivity = mActivity;
        this.url = url;
        this.mTitle = mTitle;
        this.mMessage = mMessage;
    }

    public void onRequest(final Callback callback, final String[] parameters, final String[] value, final boolean flag) {
        StringRequest requestData = new StringRequest(Request.Method.POST, Configuration.configURL(url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (flag) {
                            progressDialog.dismiss();
                        }
                        callback.onSuccess(s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                if (flag) {
                    progressDialog.dismiss();
                }
                callback.onError(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < parameters.length && i < value.length; i++) {
                    params.put(parameters[i], value[i]);
                }
                return params;
            }
        };
        if (flag) {
            progressDialog = ProgressDialog.show(mActivity, mTitle, mMessage);
        }
        AppController.getInstance().addToRequestQueue(requestData);
    }

}
