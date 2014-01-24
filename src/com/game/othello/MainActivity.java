package com.game.othello;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OthelloView othello = (OthelloView)findViewById(R.id.othello);
        final TextView text = (TextView)findViewById(R.id.current_color);
        final TextView blackText = (TextView)findViewById(R.id.black_color);
        final TextView whiteText = (TextView)findViewById(R.id.white_color);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what) {
                case 0:
                    text.setText((String)msg.obj);
                    blackText.setText(String.valueOf(msg.arg1));
                    whiteText.setText(String.valueOf(msg.arg2));
                    break;
                default:
                    break;
                }
            }
        };
        othello.setHandler(handler);
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
        }
        return super.onOptionsItemSelected(item);
    }

}
