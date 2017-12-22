package com.lgb.accelerate.utils;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.bean.DataCalories;
import com.lgb.accelerate.bean.DataDistance;
import com.lgb.accelerate.bean.DataSteps;
import com.lgb.accelerate.main.settings.UnitFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by linguobiao on 16/8/26.
 */
public class CalculateUtils {

    public static String calculateDistance(int steps, double stride) {
        String distance;
        double distanceDouble = steps * stride / 100 / 1000;
        if (!UnitUtils.isMetric(Constant.KEY_UNIT_DISTANCE)) {
            distanceDouble = UnitUtils.kmToMile(distanceDouble);
        }
        distance = FormatHelper.df_0_00().format(distanceDouble);
        return distance;
    }

    public static String calculateCalories(int steps, double weight) {
        String cal;
        double calDouble = steps * weight * 0.000398;
        cal = FormatHelper.df_0_0().format(calDouble);
        return cal;
    }

    public static List<Data> calculateWeekList(List<Data> lists) {
        List<Data> weekList= new ArrayList<>();
        if (lists != null) {
            Calendar cal = CalendarHelper.getDateOfSunday();
            int week = CalendarHelper.getWeekOfToday();
            double steps = 0;
            for (int i = 0; i < 7; i ++) {

                Data data_week = null;
                if ( Global.type_data == Constant.TYPE_STEPS) {
                   data_week = new DataSteps();
                } else if (Global.type_data == Constant.TYPE_DISTANCE) {
                    data_week = new DataDistance();
                } else if (Global.type_data == Constant.TYPE_CALORIES) {
                    data_week = new DataCalories();
                }

                String date = FormatHelper.sdf_yyyy_MM_dd_HH_mm_ss.format(cal.getTime());
                for (Data data : lists) {
                    if (date.equalsIgnoreCase(data.getDate())) {
                        steps = data.getValue();
                    }

                }

                data_week.setValue(steps);
                data_week.setWeek(week);
                weekList.add(data_week);
                steps = 0;
                cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
            }
        }
        return weekList;
    }

    public static String getWeekAverage(List<Data> lists) {
        double sum = 0;
        if (lists != null) {
            for (Data data : lists) {
                sum = sum + (double) data.getValue();
            }
        }
        return FormatHelper.df_0_00().format(sum/7);
    }
}
