package com.example.admin.wiproexercise.contractor;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.wiproexercise.R;
import com.example.admin.wiproexercise.model.Feeds;
import com.example.admin.wiproexercise.utils.Utils;
import com.example.admin.wiproexercise.volley.APIConstant;
import com.example.admin.wiproexercise.volley.MBVolley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  Maroti Mulange on 22-08-2018.
 * <p>
 * Fetching data from web services and send to feed presenter.
 */

public class GetFeedData implements MainContract.GetFeedDataIntractor {

    @Override
    public void getFeedList(final Context context, final String url, final OnFinishedListener onFinishedListener) {
        Log.d("GetFeedData", "url :" + url);

        if (Utils.getDataConnection(context)) {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    Feeds feedsData = gson.fromJson(response.toString(), Feeds.class);

                    onFinishedListener.onSuccess(feedsData.getRows(), feedsData.getTitle());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("GetFeedData", "VolleyError :" + error.getMessage());
                    try {
                        JSONObject objectError = new JSONObject(error.getMessage());
                        JSONObject objectMessage = objectError.getJSONObject(APIConstant.KEY_ERROR);
                        String message = objectMessage.getString(APIConstant.KEY_MESSAGE);
                        Log.d("GetFeedData", "VolleyError :" + message);

                        onFinishedListener.onFailure(message, url);

                    } catch (Exception e) {
                        Log.e("GetFeedData", "" + e.getMessage());
                        onFinishedListener.onFailure(context.getResources().getString(R.string.something_went_wrong), url);
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
            MBVolley.getInstance(context).addToRequestQueue(request);
        } else {
            onFinishedListener.onFailure(context.getResources().getString(R.string.no_internet), url);
        }
    }
}
