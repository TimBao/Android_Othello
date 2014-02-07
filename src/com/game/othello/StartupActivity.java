package com.game.othello;

import android.app.Activity;
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
}
