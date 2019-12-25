package com.cenah.efficentlearning.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.cenah.efficentlearning.models.Auth;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class ApplicationPreferenceManager {


    private static final String APP_SHARED_PREFS = "auth-preferences";
    private static final String SHARED_MODEL = "SHARED_MODEL";


    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    public ApplicationPreferenceManager(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        this.context = context;
        gson = new Gson();
    }


    public void saveSharedInfo(Auth sharedInfoModel) {
        String json = gson.toJson(sharedInfoModel);
        prefsEditor.putString(SHARED_MODEL, json);
        prefsEditor.commit();
    }

    public Auth getSharedInfo() {
        String json = appSharedPrefs.getString(SHARED_MODEL, "");
        return gson.fromJson(json, Auth.class);

    }

}
