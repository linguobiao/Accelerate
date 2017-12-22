package com.lgb.accelerate.utils;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class CalendarHelper {

	public static Calendar get20010101Datetime() {
		Calendar cal_ = Calendar.getInstance();
		cal_.set(Calendar.YEAR, 2000);
		cal_.set(Calendar.MONTH, Calendar.JANUARY);
		cal_.set(Calendar.DATE, 1);
		cal_.set(Calendar.HOUR_OF_DAY, 0);
		cal_.set(Calendar.MINUTE, 0);
		cal_.set(Calendar.SECOND, 0);
		cal_.set(Calendar.MILLISECOND, 0);

		return cal_;
	}

	/**
	 * 格式 yyyy.mm.dd
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar setDayFormat(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	/**
	 * 格式 yyyy.mm.dd hh
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar setHourFormat(Calendar cal) {
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	/**
	 * 格式 yyyy.mm.dd hh mm
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar setMinuteFormat(Calendar cal) {
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	/**
	 * 格式 yyyy.mm.01
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar setMonthFormat(Calendar cal) {
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	/**
	 * 获取今天日期 yyyy.mm.dd
	 * 
	 * @return
	 */
	public static Calendar getToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	/**
	 * 获取该日期的yesterday
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar getYesterday(Calendar cal) {
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(cal.getTimeInMillis());

		temp = minADay(temp);

		return temp;
	}

	/**
	 * 获取该日期的 the date before yesterday
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar getDateBeforeYesterday(Calendar cal) {
		Calendar temp = Calendar.getInstance();
		temp.setTimeInMillis(cal.getTimeInMillis());

		temp = minADay(temp);
		temp = minADay(temp);
		return temp;
	}

	/**
	 * 加一日
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar addADay(Calendar cal) {
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);

		return cal;
	}

	/**
	 * 减一天
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar minADay(Calendar cal) {
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);

		return cal;
	}

	/**
	 * 添加一个小时
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar addAnHour(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);

		return cal;
	}

	/**
	 * 添加一个月
	 * 
	 * @return
	 */
	public static Calendar addAMonth(Calendar cal) {
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);

		return cal;
	}


	/**
	 * 昨天日期、明天日期
	 * 
	 * @param today
	 */
	public static Calendar[] getYesterdayToTomorrow(Calendar today) {

		// 昨天
		Calendar yesterday = Calendar.getInstance();
		yesterday.setTimeInMillis(today.getTimeInMillis());
		yesterday = minADay(yesterday);
		// yyyy.mm.dd
		yesterday = setDayFormat(yesterday);

		// 明天
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow = addADay(tomorrow);
		// yyyy.mm.dd
		tomorrow = setDayFormat(tomorrow);

		Calendar[] calArray = new Calendar[2];
		calArray[0] = yesterday;
		calArray[1] = tomorrow;

		return calArray;
	}

	/**
	 * 今天日期，明天日期
	 * 
	 * @param today
	 * @return
	 */
	public static Calendar[] getTodayToTomorrow(Calendar today) {
		// 明天
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow = addADay(tomorrow);
		// yyyy.mm.dd
		tomorrow = setDayFormat(tomorrow);

		Calendar[] calArray = new Calendar[2];
		calArray[0] = today;
		calArray[1] = tomorrow;

		return calArray;
	}

	/**
	 * 获取夜晚时间
	 * 
	 * @param today
	 * @return
	 */
	public static Calendar[] getSleepTime(Context context, int profileID, Calendar today) {

		Calendar cal_today = Calendar.getInstance();
		cal_today.setTimeInMillis(today.getTimeInMillis());
		addADay(cal_today);

		Calendar cal_yestoday = Calendar.getInstance();
		cal_yestoday.setTimeInMillis(today.getTimeInMillis());
		minADay(cal_yestoday);

		Calendar[] calArray = new Calendar[2];
		calArray[0] = cal_yestoday;
		calArray[1] = cal_today;

		return calArray;
	}

	/**
	 * 明天日期
	 * 
	 * @param today
	 * @return
	 */
	public static Calendar getTomorrow(Calendar today) {
		// 明天
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow = addADay(tomorrow);
		// yyyy.mm.dd
		tomorrow = setDayFormat(tomorrow);

		return tomorrow;
	}

	/**
	 * 上星期日期、明天日期
	 * 
	 * @param today
	 * @return
	 */
	public static Calendar[] getLastWeekToTomorrow(Calendar today) {
		// 上星期
		Calendar lastWeek = Calendar.getInstance();
		lastWeek.setTimeInMillis(today.getTimeInMillis());
		lastWeek.set(Calendar.DATE, lastWeek.get(Calendar.DATE) - 7);
		// yyyy.mm.dd
		lastWeek = setDayFormat(lastWeek);

		// 明天
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow = addADay(tomorrow);
		// yyyy.mm.dd
		tomorrow = setDayFormat(tomorrow);

		Calendar[] calArray = new Calendar[2];
		calArray[0] = lastWeek;
		calArray[1] = tomorrow;

		return calArray;
	}

	/**
	 * 上个月日期、明天日期
	 * 
	 * @param today
	 * @return
	 */
	public static Calendar[] getLastMonthToTomorrow(Calendar today) {
		// 上个月
		Calendar lastMonth = Calendar.getInstance();
		lastMonth.setTimeInMillis(today.getTimeInMillis());
		// lastMonth.set(Calendar.MONTH, lastMonth.get(Calendar.MONTH) - 1);
		// lastMonth = minADay(lastMonth);
		lastMonth.set(Calendar.DATE, lastMonth.get(Calendar.DATE) - 30);
		// yyyy.mm.dd
		lastMonth = setDayFormat(lastMonth);

		// 明天
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow = addADay(tomorrow);
		// yyyy.mm.dd
		tomorrow = setDayFormat(tomorrow);

		Calendar[] calArray = new Calendar[2];
		calArray[0] = lastMonth;
		calArray[1] = tomorrow;

		return calArray;
	}

	/**
	 * 上一年日期、明天
	 * 
	 * @param today
	 * @return
	 */
	public static Calendar[] getLastYearToTomorrow(Calendar today) {
		// 上一年
		Calendar lastYear = Calendar.getInstance();
		lastYear.setTimeInMillis(today.getTimeInMillis());
		lastYear.set(Calendar.YEAR, lastYear.get(Calendar.YEAR) - 1);
		// yyyy.mm.dd
		lastYear = setDayFormat(lastYear);

		// 明天
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTimeInMillis(today.getTimeInMillis());
		tomorrow = addADay(tomorrow);
		// yyyy.mm.dd
		tomorrow = setDayFormat(tomorrow);

		Calendar[] calArray = new Calendar[2];
		calArray[0] = lastYear;
		calArray[1] = tomorrow;

		return calArray;
	}
	
	/**
	 * 判断两个日期先后。
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDate1AfterDate2(Calendar date1, Calendar date2) {
		date1 = setDayFormat(date1);
		date2 = setDayFormat(date2);
		if (date1.after(date2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取周日日期
	 * @return
	 */
	public static Calendar getDateOfSunday () {
		Calendar cal = Calendar.getInstance();
		cal = setDayFormat(cal);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - (dayOfWeek - 1));
		return cal;
	}

	/**
	 * 获取今天的星期
	 * @return
	 */
	public static int getWeekOfToday() {
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}

	public static Calendar getMonthBegin(Calendar cal) {
		Calendar cal_begin = Calendar.getInstance();
		cal_begin.setTimeInMillis(cal.getTimeInMillis());
		cal_begin.set(Calendar.DAY_OF_MONTH, 1);
		Log.i("testDate", "month begin = " + FormatHelper.sdf_yyyy_MM_dd_HH_mm_ss.format(cal_begin.getTime()));
		return cal_begin;
	}

	public static Calendar getMonthEnd(Calendar cal) {
		Calendar cal_end = Calendar.getInstance();
		cal_end.setTimeInMillis(cal.getTimeInMillis());
		cal_end.set(Calendar.DAY_OF_MONTH, cal_end.getActualMaximum(Calendar.DAY_OF_MONTH));
		Log.i("testDate", "month end = " + FormatHelper.sdf_yyyy_MM_dd_HH_mm_ss.format(cal_end.getTime()));
		return cal_end;
	}


}
