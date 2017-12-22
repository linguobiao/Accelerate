package com.lgb.accelerate.utils;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;

/**
 * Created by linguobiao on 16/8/24.
 */
public class StringUtils {
    public static boolean isEmail(String email) {
        if (email.matches("\\w+@\\w+\\.\\w+")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isNull(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEquals(String str1, String str2) {
        if (!isNull(str1) && !isNull(str2) && str1.equals(str2)) {
            return true;
        }
        return false;
    }

    public static boolean isZero (String str) {
        if (isNull(str)) {
            return true;
        }
        if (Double.parseDouble(str) == 0) {
            return true;
        }
        return false;
    }
    public static String parseHeight(String ft, String in, String cm) {
        if (UnitUtils.isMetric(Constant.KEY_UNIT_HEIGHT)) {
            if (!isNull(cm) && !cm.equalsIgnoreCase(".") && Double.parseDouble(cm) > 0) {
                double _cm = Double.parseDouble(cm);
                if (_cm > 0) return cm;
            }
        } else {
            if (isNull(ft) && isNull(in)){
                return Constant.FALSE;
            }
            if (isNull(ft) && !isNull(in)) {
                int _in = Integer.parseInt(in);
                double _cm = UnitUtils.inchToCm(_in);
                if (_cm > 0) return String.valueOf(_cm);

            }
            if (!isNull(ft) && isNull(in)) {
                int _ft = Integer.parseInt(ft);
                double _cm = UnitUtils.inchToCm(_ft * 12);
                if (_cm > 0) return String.valueOf(_cm);
            }
            if (!isNull(ft) && !isNull(in)) {
                int _ft = Integer.parseInt(ft);
                int _in = Integer.parseInt(in);
                double _cm = UnitUtils.inchToCm(_ft * 12 + _in);
                if (_cm > 0) return String.valueOf(_cm);
            }
        }
        return Constant.FALSE;
    }
    public static String parseStride(String value) {
        if (!isNull(value) && !value.equalsIgnoreCase(".") && Double.parseDouble(value) > 0) {
            if (UnitUtils.isMetric(Constant.KEY_UNIT_STRIDE)) {
                return value;
            } else {
                return String.valueOf(UnitUtils.inchToCm(Double.parseDouble(value)));
            }
        }
        return Constant.FALSE;
    }

    public static String parseWeight(String value) {
        if (!isNull(value) && !value.equalsIgnoreCase(".") && Double.parseDouble(value) > 0) {
            if (UnitUtils.isMetric(Constant.KEY_UNIT_WEIGHT)) {
                return value;
            } else {
                return String.valueOf(UnitUtils.LbsToKg(Double.parseDouble(value)));
            }
        }
        return Constant.FALSE;
    }

}
