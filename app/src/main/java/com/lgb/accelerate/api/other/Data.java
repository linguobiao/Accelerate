package com.lgb.accelerate.api.other;

/**
 * Created by linguobiao on 16/8/27.
 */
public interface Data {

    void setWeek(int week);
    int getWeek();

    void setDate(String str);
    String getDate();

    void setValue(double o);
    double getValue();

    String toString();
}
