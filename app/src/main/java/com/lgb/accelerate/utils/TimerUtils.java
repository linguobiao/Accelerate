package com.lgb.accelerate.utils;

import java.util.Timer;

/**
 * Created by linguobiao on 16/8/16.
 */
public class TimerUtils {

    public static void cancelTimer(Timer timer) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
