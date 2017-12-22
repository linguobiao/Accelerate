package com.lgb.accelerate.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.utils.CalendarHelper;
import com.lgb.accelerate.utils.http.HttpBaseUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加频道
 * 
 * @author lingb
 * 
 */
public class GetDataTask extends AsyncTask<Map<String, Object>, Object, String> {

	private Handler myHandler;
	private int handler_type;

	@Override
	protected String doInBackground(Map<String, Object>... params) {
		String result;
		myHandler = (Handler) params[0].get(Constant.KEY_HANDLER);
		// 获取今日数据
		if (Global.type_data_time == Constant.TYPE_DAY) {
			handler_type = Constant.HANDLER_GET_DATA_TODAY_RESULT;
		}
		// 获取这周数据
		else if (Global.type_data_time == Constant.TYPE_WEEK) {
			handler_type = Constant.HANDLER_GET_DATA_WEEK_RESULT;
		}
		// 获取一个自然月数据
		else if (Global.type_data_time == Constant.TYPE_MONTH) {
			handler_type = Constant.HANDLER_GET_DATA_MONTH_RESULT;
		}
		int id = (int) params[0].get(Constant.KEY_ID);
		String from_date = (String) params[0].get(Constant.KEY_FROM_DATE);
		String to_date = (String) params[0].get(Constant.KEY_TO_DATE);
		// 参数map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constant.KEY_UID, String.valueOf(id));
		paramsMap.put(Constant.KEY_FROM_DATE, from_date);
		paramsMap.put(Constant.KEY_TO_DATE, to_date);

		// 提交
		HttpBaseUtil httpBaseUtil = new HttpBaseUtil();
		result = httpBaseUtil.postData(Constant.URL_GET_DATA, paramsMap);
		return result;
	}

	@Override
	protected void onPreExecute() { // onPreExecute方法用于在执行后台任务前做一些UI操作
		super.onPreExecute();
	}

	@Override
	protected void onCancelled() { // onCancelled方法用于在取消执行中的任务时更改UI
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(String result) { // onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		super.onPostExecute(result);

		if (myHandler == null) {
			return;
		}
	
		Message msg = Message.obtain();
		msg.what = handler_type;
		msg.obj = result;
		myHandler.sendMessage(msg);
	}

}
