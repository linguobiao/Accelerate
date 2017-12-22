package com.lgb.accelerate.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.lgb.accelerate.Global.Global;

/**
 * 获取与设置SP文件，
 * SharedPreferences,
 * Editor
 * @author lingb
 *
 */
public class SpUtils {

	private static class SpUtilsInstance {
		public final static SpUtils instance = new SpUtils();
	}

	public static SpUtils getInstance() {
		return SpUtilsInstance.instance;
	}

	public void init(Context context) {
		this.sp = context.getSharedPreferences(Global.SP, Context.MODE_APPEND);
	}

	private SharedPreferences sp;

	// **** boolean ****
	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}
	public boolean putBoolean(String key, boolean value) {
		return sp.edit().putBoolean(key, value).commit();
	}
	// **** String ****
	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}
	public boolean putString(String key, String value) {
		return sp.edit().putString(key, value).commit();
	}
	// **** int ****
	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}
	public boolean putInt(String key, int value) {
		return sp.edit().putInt(key, value).commit();
	}
	// **** double ****
	public double getDouble(String key, double defValue) {
		return Double.longBitsToDouble(sp.getLong(key, Double.doubleToLongBits(defValue)));
	}
	public boolean putDouble(String key, double value) {
		return sp.edit().putLong(key, Double.doubleToLongBits(value)).commit();
	}

	public boolean clear() {
		return sp.edit().clear().commit();
	}
}
