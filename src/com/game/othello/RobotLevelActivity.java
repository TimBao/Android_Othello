package com.game.othello;

import com.game.othello.Chess.ChessColor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class RobotLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_level_layout);

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

}
