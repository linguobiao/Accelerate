package com.lgb.accelerate.utils;

import android.content.Context;
import android.widget.TextView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.other.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linguobiao on 16/8/26.
 */
public class UnitUtils {

    public static boolean isMetric(String unitTypeKey) {
        int unit = SpUtils.getInstance().getInt(unitTypeKey, Constant.UNIT_IMPERIAL);
        if (unit == Constant.UNIT_METRIC) {
            return true;
        }
        return false;
    }

    public static void initUnit(Context context, TextView textView, String unitTypeKey) {

        if (UnitUtils.isMetric(unitTypeKey)) {
            if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_STRIDE)) {
                textView.setText(context.getString(R.string.settings_cm));

            } else if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_HEIGHT)) {
                textView.setText(context.getString(R.string.settings_cm));

            } else if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_WEIGHT)) {
                textView.setText(context.getString(R.string.settings_kg));

            } else if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_DISTANCE)) {
                textView.setText(context.getString(R.string.settings_km));

            }
        } else {
            if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_STRIDE)) {
                textView.setText(context.getString(R.string.settings_inch));

            } else if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_HEIGHT)) {
                textView.setText(context.getString(R.string.settings_inch));

            } else if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_WEIGHT)) {
                textView.setText(context.getString(R.string.settings_lbs));

            } else if (unitTypeKey.equalsIgnoreCase(Constant.KEY_UNIT_DISTANCE)) {
                textView.setText(context.getString(R.string.settings_miles));

            }
        }
    }

    public static List<Data> kmToMileForData(List<Data> lists) {
        if (lists != null) {
            for (Data data : lists) {
                data.setValue(kmToMile(data.getValue()));
            }
        }
        return lists;
    }

    public static double ftInToCm(int ft, int in) {
        int height = ft * 12 + in;
        return inchToCm(height);
    }

    public static int[] cmToFtIn(double height) {
        int[] heights= new int[2];
        double heightInch = cmToInch(height);
        heights[0] = (int) heightInch/12;
        heights[1] = (int) (heightInch%12 + 0.5);
        return heights;
    }

    public static double cmToInch(double cm) {
        return cm * 0.39370078740157;
    }

    public static double inchToCm(double inch) {
        return 2.54 * inch;
    }

    /**
     * kg 转换成 lbs
     *
     * @param kg
     * @return
     */
    public static double kgToLbs(double kg) {
        return kg / 0.4536;
    }

    /**
     * lbs 转换成 kg
     *
     * @param lbs
     * @return
     */
    public static double LbsToKg(double lbs) {
        return lbs * 0.4536;
    }

    public static double kmToMile(double km) {
        return km / 1.609;
    }

    public static double mileToKm(double mile) {
        return mile * 1.609;
    }
}
