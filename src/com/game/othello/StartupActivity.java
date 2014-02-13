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

public class StartupActivity extends Activity {

    private Button btnPtp;
    private Button btnPtr;

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
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(R.string.action_exit)
        .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
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
