package com.lgb.accelerate.Global;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.lgb.accelerate.utils.SpUtils;


/**
 * Created by linguobiao on 16/8/19.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SpUtils.getInstance().init(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplicationContext());
    }
}
