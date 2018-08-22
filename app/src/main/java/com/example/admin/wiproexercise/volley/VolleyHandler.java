package com.example.admin.wiproexercise.volley;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.wiproexercise.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Maroti Mulange on 21-08-2018.
 * <p>
 * Volley request for all APIs.
 */

public class VolleyHandler {

    private final String TAG = getClass().getSimpleName();

    private VolleyResponseListener volleyResponseListener;
    private Context context;

    public VolleyHandler(VolleyResponseListener volleyResponseListener) {
        this.volleyResponseListener = volleyResponseListener;
        if (volleyResponseListener instanceof Activity) {
            context = (Context) volleyResponseListener;
        }
    }

    public VolleyHandler(VolleyResponseListener volleyResponseListener, Context context) {
        this.volleyResponseListener = volleyResponseListener;
        this.context = context;
    }

    public void makeJsonObjectRequest(int methodType, JSONObject jsonObject, final String url) {
        Log.d(TAG, "url :" + url);
        JsonObjectRequest request = new JsonObjectRequest(methodType, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyResponseListener.onSuccess(response, url);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "VolleyError :" + error.getMessage());
                try {
                    JSONObject objectError = new JSONObject(error.getMessage());
                    JSONObject objectMessage = objectError.getJSONObject(APIConstant.KEY_ERROR);
                    String message = objectMessage.getString(APIConstant.KEY_MESSAGE);
                    Log.d(TAG, "VolleyError :" + message);

                    volleyResponseListener.onFailure(message, url);

                } catch (Exception e) {
                    Log.e(TAG, "" + e.getMessage());
                    volleyResponseListener.onFailure(context.getResources().getString(R.string.something_went_wrong), url);
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap();
                header.put("Content-Type", "application/json");
                return header;
            }

        };
        
        int socketTimeout = 30000;//30 seconds - change to what you want
        request.setRetryPolicy(new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MBVolley.getInstance(this.context).addToRequestQueue(request);
    }
}
