package com.lgb.accelerate.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FormatHelper {

	/**
	 * 整数
	 */
	public static DecimalFormat df_0() {

		Locale currentLocale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat("0", otherSymbols);

		return df;
	}

	/**
	 * 保留一位小数
	 */
	public static DecimalFormat df_0_0() {
		
		Locale currentLocale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.'); 
		DecimalFormat df = new DecimalFormat("0.0", otherSymbols);
		
		return df;
	}
	
	/**
	 * 保留两位小数
	 */
	public static DecimalFormat df_0_00() {
		
		Locale currentLocale = Locale.ENGLISH;
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.'); 
		DecimalFormat df = new DecimalFormat("0.00", otherSymbols);
		
		return df;
	}

	/**
	 * 整数 00
	 */
	public static final DecimalFormat df_00 = new DecimalFormat("00");
	/**
	 * 整数0
	 */
	public static final DecimalFormat df_0 = new DecimalFormat("0");

	// ////////////////////////////////////////////////

	/**
	 * yyyy-MM-dd HH:mm
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_yyyy_MM_dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * HH:mm:ss
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_HH_mm_ss = new SimpleDateFormat("HH:mm:ss");

	/**
	 * yyyy-MM-dd
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * MM-dd
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_MM_dd = new SimpleDateFormat("MM-dd");

	/**
	 * yyyyMMdd
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

	/**
	 * yyyy-MM-dd HH
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_yyyy_MM_dd_HH = new SimpleDateFormat("yyyy-MM-dd HH");

	/**
	 * HH
	 */
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat sdf_HH = new SimpleDateFormat("HH");

	
	/**
	 * 字体
	 * @param root
	 * @param act
	 */
	public static void changeFonts(ViewGroup root, Activity act) {
		Typeface tf = null;
		
		for (int i = 0; i < root.getChildCount(); i++) {

			View v = root.getChildAt(i);

			if (v instanceof TextView) {

				((TextView) v).setTypeface(tf);

			} else if (v instanceof Button) {

				((Button) v).setTypeface(tf);

			} else if (v instanceof EditText) {

				((EditText) v).setTypeface(tf);

			} else if (v instanceof ViewGroup) {

				changeFonts((ViewGroup) v, act);

			}

		}

	}

	public static Map<String, String> getDataBaseMap() {
		Map<String, String> map = new HashMap<>();

		map.put(Constant.KEY_STEPS, "0");
		map.put(Constant.KEY_DISTANCES, "0.00");
		map.put(Constant.KEY_CALORIES, "0.0");
		return map;
	}

	/**
	 * 返回英文月份
	 *
	 * @param calendar
	 * @return
	 */
	public static String getMonthStr(Context context, Calendar calendar) {

		int month = calendar.get(Calendar.MONTH);

		String[] months = context.getResources().getStringArray(R.array.month);

		String monthStr = months[month];

		return monthStr;
	}

	/**
	 * 返回 31 August 2016
	 *
	 * @param calendar
	 * @return
	 */
	public static String getDDMonthYYYY(Context context, Calendar calendar) {

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String[] months = context.getResources().getStringArray(R.array.month2);

		String monthStr = months[month];

		return day + " " + monthStr + " " + year;
	}

	/**
	 * 返回 31 August 2016
	 *
	 * @param date
	 * @return
	 */
	public static String getDDMonthYYYY(Context context, String date) {

		String[] dates = date.split(" ")[0].split("-");

		String year = dates[0];
		int month = Integer.parseInt(dates[1]) - 1;
		String day = dates[2];

		String[] months = context.getResources().getStringArray(R.array.month2);

		String monthStr = months[month];

		return day + " " + monthStr + " " + year;
	}

}
