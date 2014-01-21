package com.game.othello;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OthelloView view = new OthelloView(this);
        view.setBackgroundColor(Color.rgb(50, 100, 250));
        setContentView(view);
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
