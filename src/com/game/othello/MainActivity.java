/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import cn.waps.AppConnect;

import com.game.othello.Chess.ChessColor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final OthelloView othello = (OthelloView)findViewById(R.id.othello);
        final ImageView blackTurn = (ImageView)findViewById(R.id.current_turn_b);
        final Button blackCount = (Button)findViewById(R.id.black_count);
        final Button whiteCount = (Button)findViewById(R.id.white_count);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if((String)msg.obj != "draw") {
                    blackCount.setText(String.valueOf(msg.arg1));
                    whiteCount.setText(String.valueOf(msg.arg2));
                    blackTurn.setVisibility(msg.what == 0 ? View.GONE : View.VISIBLE);
                    othello.robotDropChess();
                } else {
                    othello.invalidate();
                }

            }
        };
        othello.setHandler(handler);
        Intent intent = getIntent();
        int level = 0;
        if ((level = intent.getIntExtra("level", 0)) != 0) {
            int color = intent.getIntExtra("color", 0);
            if (color == 0) {
                othello.addRobot(level, ChessColor.BLACK);
            } else {
                othello.addRobot(level, ChessColor.WHITE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(R.string.action_exit)
        .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	MainActivity.this.finish();
            }
        })
        .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
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
