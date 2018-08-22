package com.example.admin.wiproexercise.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Maroti Mulange on 21-08-2018.
 */

public class MBVolley {

    private final String TAG = getClass().getSimpleName();
    private static MBVolley volleySingleton;
    private RequestQueue requestQueue;
    private Context context;

    public MBVolley(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MBVolley getInstance(Context context) {
        if (volleySingleton == null)
            volleySingleton = new MBVolley(context);

        return volleySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public void cancelRequest() {
        getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {

            @Override
            public boolean apply(Request<?> arg0) {
                return true;
            }
        });
    }
}
