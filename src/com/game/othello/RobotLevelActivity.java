/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import cn.waps.AppConnect;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class RobotLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_level_layout);

        LinearLayout adLayout = (LinearLayout)findViewById(R.id.ADLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adLayout);

        final ImageButton btnBlack = (ImageButton)findViewById(R.id.btn_black_color);
        final ImageButton btnWhite = (ImageButton)findViewById(R.id.btn_white_color);
        Button btnLevel1 = (Button)findViewById(R.id.level_1);
        Button btnLevel2 = (Button)findViewById(R.id.level_2);
        Button btnLevel3 = (Button)findViewById(R.id.level_3);
        btnBlack.setSelected(true);

        btnBlack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                btnBlack.setSelected(true);
                btnWhite.setSelected(false);
            }
            
        });

        btnWhite.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                btnBlack.setSelected(false);
                btnWhite.setSelected(true);
            }
            
        });

        btnLevel1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("color", btnWhite.isSelected() ? 0 : 1);
                intent.putExtra("level", 1);
                intent.setClass(RobotLevelActivity.this, MainActivity.class);
                startActivity(intent);
            }
            
        });

        btnLevel2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("color", btnWhite.isSelected() ? 0 : 1);
                intent.putExtra("level", 2);
                intent.setClass(RobotLevelActivity.this, MainActivity.class);
                startActivity(intent);
            }
            
        });

        btnLevel3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("color", btnWhite.isSelected() ? 0 : 1);
                intent.putExtra("level", 3);
                intent.setClass(RobotLevelActivity.this, MainActivity.class);
                startActivity(intent);
            }
            
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent();               
            intent.setClass(this, GameRuleActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.action_ad) {
            AppConnect.getInstance(this).showOffers(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
