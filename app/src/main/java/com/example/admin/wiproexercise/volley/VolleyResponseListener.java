package com.example.admin.wiproexercise.volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by  Maroti Mulange on 21-08-2018.
 */
public interface VolleyResponseListener {

    void onSuccess(JSONObject responseData, String url);

    void onFailure(String message, String url);
}
