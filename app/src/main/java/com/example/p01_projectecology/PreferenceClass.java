package com.example.p01_projectecology;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;


public class PreferenceClass {
    public static final String PREFERENCE_NAME = "User";
    private final SharedPreferences sharedpreferences;
    private String isActive;
    final String SAVED_ID = "id";
    final String SAVED_EMAIL = "email";
    final String SAVED_USERNAME = "username";
    final String SAVED_NAME = "name";
    final String SAVED_SURNAME = "surname";
    final String SAVED_GEO = "geo";
    final String arr[] = new String[4];
    final String key;

    public void saveUser(ArrayList<String> ar) {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SAVED_ID, ar.get(0));
        editor.putString(SAVED_USERNAME, ar.get(1));
        editor.putString(SAVED_EMAIL, ar.get(2));
        if (ar.size() > 3) {
            if (!TextUtils.isEmpty(ar.get(3))) {
                editor.putString(SAVED_NAME, ar.get(3));
            }
        }
        if (ar.size() > 4) {
            if (!TextUtils.isEmpty(ar.get(4))) {
                editor.putString(SAVED_SURNAME, ar.get(4));
            }
        }

        if (ar.size() > 5) {
            if (!TextUtils.isEmpty(ar.get(5))) {
                editor.putString(SAVED_GEO, ar.get(5));
            }
        }

        editor.apply();
    }

    public void deleteUser() {
        sharedpreferences.edit().clear().apply();
    }

    public void savePoint(int point) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("sortPoint", point);
        editor.apply();
    }

    public void editProfileInfo(String name, String surname, String geo) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (!TextUtils.isEmpty(name)) {
            editor.putString(SAVED_NAME, name);
        }
        if (!TextUtils.isEmpty(surname)) {
            editor.putString(SAVED_SURNAME, surname);
        }
        if (!TextUtils.isEmpty(geo)) {
            editor.putString(SAVED_GEO, geo);
        }
        editor.apply();
    }

    public Integer getPoint() {
        return sharedpreferences.getInt("sortPoint", 0);
    }

    public String getUser() {
        return sharedpreferences.getString(SAVED_ID, "0");
    }

    public String getName(){
        return sharedpreferences.getString(SAVED_NAME, "Пусто");
    }
    public String getSurname(){
        return sharedpreferences.getString(SAVED_SURNAME, "Пусто");
    }
    public String getGeo(){
        return sharedpreferences.getString(SAVED_GEO, "Пусто");
    }
    public String getEmail(){
        return sharedpreferences.getString(SAVED_EMAIL, "Пусто");
    }

    public String getUserInfo(String type){
        return sharedpreferences.getString(type, "Пусто");
    }

    public void saveUserInfo(String type, String text){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(type, text);
        editor.apply();
    }

    public PreferenceClass(Context context, String key) {
        this.key = key;
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public boolean userIsActive() {
        return sharedpreferences.contains("id");
    }
}