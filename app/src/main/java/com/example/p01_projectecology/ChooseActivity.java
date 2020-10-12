package com.example.p01_projectecology;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {
    private LinearLayout bio, paper, plastuk;
    private String choose;
    private Boolean userActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        userActive = arguments.getBoolean("userActive");
        if (userActive){
            setContentView(R.layout.activity_choose);
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(ChooseActivity.this).create();
            alertDialog.setTitle("Login Status");
            alertDialog.setMessage("Користувач не залогінився");
            alertDialog.show();
            alertDialog.getWindow().setLayout(1000,1000);
            setContentView(R.layout.activity_main);
            finish();
        }
        init();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bio.setBackgroundResource(R.drawable.linearlayout_border);
                plastuk.setBackgroundResource(R.drawable.linearlayout_border);
                paper.setBackgroundResource(R.drawable.linearlayout_border);
                switch (view.getId()) {
                    case R.id.bio:
                        bio.setBackgroundResource(R.drawable.border_green);
                        choose = "bio";
                        break;
                    case R.id.paper:
                        paper.setBackgroundResource(R.drawable.border_green);
                        choose = "paper";
                        break;
                    case R.id.plastuk:
                        plastuk.setBackgroundResource(R.drawable.border_green);
                        choose = "plastuk";
                        break;
                }
            }
        };
        initClick(onClickListener);
    }

    public void init() {
        bio = (LinearLayout) findViewById(R.id.bio);
        paper = (LinearLayout) findViewById(R.id.paper);
        plastuk = (LinearLayout) findViewById(R.id.plastuk);
    }

    public void initClick(View.OnClickListener onClickListener) {
        bio.setOnClickListener(onClickListener);
        paper.setOnClickListener(onClickListener);
        plastuk.setOnClickListener(onClickListener);
    }

    public void go(View view) {
        if (TextUtils.isEmpty(choose)){
            AlertDialog dialog = new AlertDialog.Builder(ChooseActivity.this).create();
            dialog.setTitle("Status");
            dialog.setMessage("Ви не вибрали урну");
            dialog.show();
            dialog.getWindow().setLayout(1000,1000);
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("choose", choose);
            editor.apply();
            Intent connect = new Intent(ChooseActivity.this, ConnectActivity.class);
            connect.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            connect.putExtra("userActive", userActive);
            connect.putExtra("chooseSmash", choose);
            startActivity(connect);
        }
    }

}
