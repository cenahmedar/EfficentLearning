package com.cenah.efficentlearning.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.Classes;
import com.cenah.efficentlearning.models.Shared;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class Apm {


    private static final String APP_SHARED_PREFS = "auth-preferences";
    private static final String SHARED_MODEL = "SHARED_MODEL";
    private static final String SHARED_CLASS = "SHARED_CLASS";

    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Gson gson;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    public Apm(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        this.context = context;
        gson = new Gson();
    }


    public void saveSharedInfo(Shared sharedInfoModel) {
        String json = gson.toJson(sharedInfoModel);
        prefsEditor.putString(SHARED_MODEL, json);
        prefsEditor.commit();
    }

    public Shared getSharedInfo() {
        String json = appSharedPrefs.getString(SHARED_MODEL, "");
        return gson.fromJson(json, Shared.class);
    }

    public void deleteSharedInfo() {
       appSharedPrefs.edit().remove(SHARED_MODEL).apply();
    }


    public void saveClasses(Classes sharedInfoModel) {
        String json = gson.toJson(sharedInfoModel);
        prefsEditor.putString(SHARED_CLASS, json);
        prefsEditor.commit();
    }

    public Classes getClasses() {
        String json = appSharedPrefs.getString(SHARED_CLASS, "");
        return gson.fromJson(json, Classes.class);
    }
}
