/**
 * Copyright (c) 2014, WanXiang Bao
 * All rights reserved.
 * Summary : 
 * Author : WanXiang Bao
 */
package com.game.othello;

import cn.waps.AppConnect;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class GameRuleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamerule_layout);

        LinearLayout adLayout = (LinearLayout)findViewById(R.id.ADLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adLayout);
    }

}
