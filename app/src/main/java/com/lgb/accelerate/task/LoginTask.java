package com.lgb.accelerate.task;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.utils.http.HttpBaseUtil;

/**
 * 添加频道
 * 
 * @author lingb
 * 
 */
public class LoginTask extends AsyncTask<Map<String, Object>, Object, String> {

	private Handler myHandler;
	private String email;
	private String password;


	@Override
	protected String doInBackground(Map<String, Object>... params) {
		String result;
		myHandler = (Handler) params[0].get(Constant.KEY_HANDLER);
		email = (String) params[0].get(Constant.KEY_EMAIL);
		password = (String) params[0].get(Constant.KEY_PASSWORD);

		// 参数map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constant.KEY_EMAIL, email);
		paramsMap.put(Constant.KEY_PASSWORD, password);

		// 提交
		HttpBaseUtil httpBaseUtil = new HttpBaseUtil();
		result = httpBaseUtil.postData(Constant.URL_LOGIN, paramsMap);
		Log.i("test", "resutl = " + result);
		return result;
	}

	@Override
	protected void onPreExecute() { // onPreExecute方法用于在执行后台任务前做一些UI操作
		super.onPreExecute();
	}

	@Override
	protected void onCancelled() { // onCancelled方法用于在取消执行中的任务时更改UI
		Log.i("test", "onCancel");
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(String result) { // onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		super.onPostExecute(result);
		Log.i("test", "onPostExecute");
		if (myHandler == null) {
			return;
		}
	
		Message msg = Message.obtain();
		msg.what = Constant.HANDLER_LOGIN_RESULT;
		msg.obj = result;
		myHandler.sendMessage(msg);
	}

}
