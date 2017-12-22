package com.lgb.accelerate.utils;

import com.lgb.accelerate.Global.Constant;

import java.util.Map;

/**
 * Created by linguobiao on 16/8/26.
 */
public class CheckUtils {

    public static boolean isStepsOk(String steps) {
        if (!StringUtils.isNull(steps) && Integer.parseInt(steps) > 0) {
            return true;
        }
        return false;
    }
}
