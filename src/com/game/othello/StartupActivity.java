/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.waps.AppConnect;

public class StartupActivity extends Activity {

    private Button btnPtp;
    private Button btnPtr;
    private static String APPPID = "default";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_layout);

        btnPtp = (Button)findViewById(R.id.ptp);
        btnPtr = (Button)findViewById(R.id.ptr);

        btnPtp.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StartupActivity.this, MainActivity.class);
                startActivity(intent);
            }
            
        });

        btnPtr.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StartupActivity.this, RobotLevelActivity.class);
                startActivity(intent);
            }
            
        });

        String appPId = "default";
        if (APPPID == "") {
            appPId = "default";
        } else if (APPPID == "google") {
            appPId = "google";
        } else if (APPPID == "91") {
            appPId = "91";
        } else if (APPPID == "hiapk") {
            appPId = "hiapk";
        } else if (APPPID == "goapk") {
            appPId = "goapk";
        } else if (APPPID == "appChina") {
            appPId = "appChina";
        } else if (APPPID == "feiliu") {
            appPId = "feiliu";
        } else if (APPPID == "huawei") {
            appPId = "huawei";
        } else if (APPPID == "QQ") {
            appPId = "QQ";
        } else if (APPPID == "3G") {
            appPId = "3G";
        } else if (APPPID == "360") {
            appPId = "360";
        } else if (APPPID == "baidu") {
            appPId = "baidu";
        } else if (APPPID == "sohu") {
            appPId = "sohu";
        } else if (APPPID == "UC") {
            appPId = "UC";
        } else if (APPPID == "dangle") {
            appPId = "dangle";
        } else if (APPPID == "samsung") {
            appPId = "samsung";
        } else if (APPPID == "xiaomi") {
            appPId = "xiaomi";
        } else if (APPPID == "nearme") {
            appPId = "nearme";
        } else if (APPPID == "partner") {
            appPId = "";
        }

        if (appPId != null) {
            AppConnect.getInstance("6757a538e6da409f6a5720cf6df8aa28", appPId, this);
        } else {
            AppConnect.getInstance(this);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(R.string.action_exit)
        .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppConnect.getInstance(StartupActivity.this).close();
                StartupActivity.this.finish();
            }
        })
        .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
}
