package com.lgb.accelerate.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.main.MainActivity;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.TimerUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by linguobiao on 16/8/16.
 */
public class SplashActivity extends Activity{

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Add code to print out the key hash
//        pLMtm9X7eq5tjsms1ykKR344pC0=
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ehomeproducts.fitmaker",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int id = SpUtils.getInstance().getInt(Constant.KEY_ID, -1);
                String session_id = SpUtils.getInstance().getString(Constant.KEY_SESSION_ID, null);
                if (id != -1 && session_id != null) {
                    int intoType = SpUtils.getInstance().getInt(Constant.KEY_TYPE_INTO, Constant.TYPE_INTO_NULL);
                    if (intoType == Constant.TYPE_INTO_NULL) {
                        startActivity(new Intent(SplashActivity.this, FacebookActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                }
                SplashActivity.this.finish();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimerUtils.cancelTimer(timer);
    }
}
