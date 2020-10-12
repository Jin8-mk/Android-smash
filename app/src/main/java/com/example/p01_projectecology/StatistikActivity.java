package com.example.p01_projectecology;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Response;

import java.util.HashMap;
import java.util.Map;

public class StatistikActivity extends AppCompatActivity{
    ProgressBar pb, pbc;
    TextView progress;
    String choose, smashLvl;
    Integer smshLvl;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch wifi, clean;
    private Boolean userActive;

    private BroadcastReceiver BatInfo = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctx, Intent intent){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            pb.setProgress(level);
            smshLvl = Integer.parseInt(smashLvl);
            pbc.setProgress(smshLvl);
            progress.setText(smashLvl + "%");
        }
    };


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        userActive = arguments.getBoolean("userActive");
        choose = arguments.get("choose").toString();
        smashLvl = arguments.get("smashLvl").toString();
        if (userActive){
            setContentView(R.layout.activity_statistik);
            prog();
            init();
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(StatistikActivity.this).create();
            alertDialog.setTitle("Login Status");
            alertDialog.setMessage("Користувач не залогінився");
            alertDialog.show();
            alertDialog.getWindow().setLayout(1000,1000);
            setContentView(R.layout.activity_main);
            finish();
        }
    }
    public void init(){
        wifi = (Switch) findViewById(R.id.switch1);
        clean = (Switch) findViewById(R.id.switch2);
        progress = (TextView) findViewById(R.id.textView17);
    }

    public void prog(){
        pb = (ProgressBar) findViewById(R.id.progressBar2);
        pbc = (ProgressBar) findViewById(R.id.progressBar);
//        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
//        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        this.registerReceiver(this.BatInfo, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void clickSeek(View view){

    }


    public void game(View view) {
        Intent game = new Intent(StatistikActivity.this, ChooseGameActivity.class);
        game.putExtra("userActive", userActive);
        startActivity(game);
    }

    public void profile(View view) {
        Intent profile = new Intent(StatistikActivity.this, ProfileActivity.class);
        startActivity(profile);
    }
}


