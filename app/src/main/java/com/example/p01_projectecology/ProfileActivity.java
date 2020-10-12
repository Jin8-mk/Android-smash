package com.example.p01_projectecology;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    EditText name, surname, email, geo, flag;
    PreferenceClass preferenceClass;
    String typeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferenceClass = new PreferenceClass(this, "");
        init();
    }

    @SuppressLint("ResourceAsColor")
    public void init() {
        geo = (EditText) findViewById(R.id.editTextTextPersonName2);
        name = (EditText) findViewById(R.id.editTextTextPersonName4);
        surname = (EditText) findViewById(R.id.editTextTextPersonName5);
        email = (EditText) findViewById(R.id.editTextTextPersonName3);
        geo.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        name.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        surname.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        email.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        geo.setText(preferenceClass.getGeo());
        name.setText(preferenceClass.getName());
        surname.setText(preferenceClass.getSurname());
        email.setText(preferenceClass.getEmail());
        geo.setFocusable(false);
        geo.setLongClickable(false);
        geo.setCursorVisible(false);
        name.setFocusable(false);
        name.setLongClickable(false);
        name.setCursorVisible(false);
        surname.setFocusable(false);
        surname.setLongClickable(false);
        surname.setCursorVisible(false);
        email.setFocusable(false);
        email.setLongClickable(false);
        email.setCursorVisible(false);
    }

    public void back(View view) {
        finish();
    }

    public void name(View view) {
        typeEdit = "name";
        setEditText(typeEdit, name);
        setEdit(name);
    }

    public void geo(View view) {
        typeEdit = "geo";
        setEditText(typeEdit, geo);
        setEdit(geo);

    }

    public void surname(View view) {
        typeEdit = "surname";
        setEditText(typeEdit, surname);
        setEdit(surname);
    }

    public void email(View view) {
        typeEdit = "email";
        setEditText(typeEdit, email);
        setEdit(email);
    }

    public void setEditText(String type , EditText edit){
        if (preferenceClass.getUserInfo(typeEdit).equals("Пусто")){
            edit.setText("");
        } else {
            edit.setText(preferenceClass.getUserInfo(typeEdit));
        }
    }


    @SuppressLint("ResourceAsColor")
    public void setEdit(final EditText edit) {
        edit.setFocusableInTouchMode(true);
        edit.setFocusable(true);
        edit.setLongClickable(true);
        edit.setCursorVisible(true);
        edit.requestFocus();
        edit.getBackground().clearColorFilter();
        flag = edit;
        showKeyboardFrom(ProfileActivity.this, edit);
        edit.setSelection(edit.getText().length());
        edit.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                preferenceClass.saveUserInfo(typeEdit, flag.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if (!edit.getText().toString().equals("")){
                                edit.setFocusableInTouchMode(false);
                                edit.setFocusable(false);
                                edit.setLongClickable(false);
                                edit.setCursorVisible(false);
                                edit.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
                                hideKeyboardFrom(ProfileActivity.this, edit);
                            } else {
                                edit.setFocusableInTouchMode(false);
                                edit.setFocusable(false);
                                edit.setLongClickable(false);
                                edit.setCursorVisible(false);
                                edit.setText("Пусто");
                                edit.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
                                hideKeyboardFrom(ProfileActivity.this, edit);
                            }
                        case KeyEvent.ACTION_DOWN:
                            if (!edit.getText().toString().equals("")){
                                edit.setFocusableInTouchMode(false);
                                edit.setFocusable(false);
                                edit.setLongClickable(false);
                                edit.setCursorVisible(false);
                                edit.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
                                hideKeyboardFrom(ProfileActivity.this, edit);
                            } else {
                                edit.setFocusableInTouchMode(false);
                                edit.setFocusable(false);
                                edit.setLongClickable(false);
                                edit.setCursorVisible(false);
                                edit.setText("Пусто");
                                edit.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
                                hideKeyboardFrom(ProfileActivity.this, edit);
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboardFrom(Context context, EditText edit) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
    }
}
