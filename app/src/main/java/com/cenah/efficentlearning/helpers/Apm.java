package com.cenah.efficentlearning.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.Classes;
import com.cenah.efficentlearning.models.Material;
import com.cenah.efficentlearning.models.Shared;
import com.cenah.efficentlearning.models.TokenClasses;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class Apm {


    private static final String APP_SHARED_PREFS = "auth-preferences";
    private static final String SHARED_MODEL = "SHARED_MODEL";
    private static final String SHARED_CLASS = "SHARED_CLASS";
    private static final String SHARED_TASK = "SHARED_TASK";
    private static final String SHARED_Token_TASK = "SHARED_Token_TASK";
    private static final String SHARED_QUES = "SHARED_QUES";


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

    public void saveTask(Material sharedInfoModel) {
        String json = gson.toJson(sharedInfoModel);
        prefsEditor.putString(SHARED_TASK, json);
        prefsEditor.commit();
    }

    public Material getTask() {
        String json = appSharedPrefs.getString(SHARED_TASK, "");
        return gson.fromJson(json, Material.class);
    }


    public void saveTokenTask(TokenClasses sharedInfoModel) {
        String json = gson.toJson(sharedInfoModel);
        prefsEditor.putString(SHARED_Token_TASK, json);
        prefsEditor.commit();
    }

    public TokenClasses getTokenTask() {
        String json = appSharedPrefs.getString(SHARED_Token_TASK, "");
        return gson.fromJson(json, TokenClasses.class);
    }

    public void saveQuestion(Material sharedInfoModel) {
        String json = gson.toJson(sharedInfoModel);
        prefsEditor.putString(SHARED_QUES, json);
        prefsEditor.commit();
    }

    public Material getQuestion() {
        String json = appSharedPrefs.getString(SHARED_QUES, "");
        return gson.fromJson(json, Material.class);
    }

    public void saveLastNoti(Material sharedInfoModel) {
        String json = gson.toJson(sharedInfoModel);
        prefsEditor.putString(SHARED_QUES, json);
        prefsEditor.commit();
    }

    public Material getLastNoti() {
        String json = appSharedPrefs.getString(SHARED_QUES, "");
        return gson.fromJson(json, Material.class);
    }


}
