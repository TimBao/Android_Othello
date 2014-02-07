package com.game.othello;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RobotLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_level_layout);

        Button btnLevel1 = (Button)findViewById(R.id.level_1);
        Button btnLevel2 = (Button)findViewById(R.id.level_2);
        Button btnLevel3 = (Button)findViewById(R.id.level_3);

        btnLevel1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("level", 1);
                intent.setClass(RobotLevelActivity.this, MainActivity.class);
                startActivity(intent);
            }
            
        });

        btnLevel2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("level", 2);
                intent.setClass(RobotLevelActivity.this, MainActivity.class);
                startActivity(intent);
            }
            
        });
    }

}
