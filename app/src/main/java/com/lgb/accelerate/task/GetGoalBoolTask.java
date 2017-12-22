package com.lgb.accelerate.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.utils.http.HttpBaseUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加频道
 * 
 * @author lingb
 * 
 */
public class GetGoalBoolTask extends AsyncTask<Map<String, Object>, Object, String> {

	private Handler myHandler;

	@Override
	protected String doInBackground(Map<String, Object>... params) {
		String result;
		myHandler = (Handler) params[0].get(Constant.KEY_HANDLER);
		int id = (int) params[0].get(Constant.KEY_ID);
//		String from_date = (String) params[0].get(Constant.KEY_FROM_DATE);
//		String to_date = (String) params[0].get(Constant.KEY_TO_DATE);
		// 参数map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constant.KEY_UID, String.valueOf(id));
//		paramsMap.put(Constant.KEY_FROM_DATE, from_date);
//		paramsMap.put(Constant.KEY_TO_DATE, to_date);

		// 提交
		HttpBaseUtil httpBaseUtil = new HttpBaseUtil();
		result = httpBaseUtil.postData(Constant.URL_GET_WEEKLY_GOAL_BOOL, paramsMap);
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
		msg.what = Constant.HANDLER_GET_GAOL_BOOL_RESULT;
		msg.obj = result;
		myHandler.sendMessage(msg);
	}

}
