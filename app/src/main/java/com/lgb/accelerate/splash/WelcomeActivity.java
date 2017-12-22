package com.lgb.accelerate.splash;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;

/**
 * Created by linguobiao on 16/8/16.
 */
public class WelcomeActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initUI();
        initReceiver();
    }

    private void initUI() {
        findViewById(R.id.txt_login).setOnClickListener(this);
        findViewById(R.id.txt_sign).setOnClickListener(this);
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.INTENT_FINISH_WELCOME_ACTIVITY);
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.INTENT_FINISH_WELCOME_ACTIVITY)) {
                WelcomeActivity.this.finish();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_login:
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                break;
            case R.id.txt_sign:
                startActivity(new Intent(WelcomeActivity.this, SignActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
