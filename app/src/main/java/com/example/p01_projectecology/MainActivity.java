package com.example.p01_projectecology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("id")){
            Intent statistik = new Intent(MainActivity.this, ConnectActivity.class);
            statistik.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            statistik.putExtra("userActive", sharedPreferences.contains("id"));
            statistik.putExtra("chooseSmash", sharedPreferences.getString("choose", ""));
            startActivity(statistik);
        }
    }

    public void signIn(View view) {
        Intent signIn = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(signIn);
    }

    public void signUp(View view) {
        Intent signUp = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUp);
    }
}