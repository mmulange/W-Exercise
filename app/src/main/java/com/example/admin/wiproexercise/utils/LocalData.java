package com.example.admin.wiproexercise.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalData {

    private static LocalData instance;

    private static Context appContext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static synchronized LocalData getInstance(Context context) {
        appContext = context;
        if (instance == null) {
            instance = new LocalData();
        }
        return instance;
    }

    public LocalData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        editor = preferences.edit();
    }

    public String getTitle() {
        String title = preferences.getString(Constants.KEY_TITLE, "");
        return title;
    }

    public void setTitle(String title) {
        editor.putString(Constants.KEY_TITLE, title);
        editor.commit();
    }
}
