package com.lgb.accelerate.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.bean.User;
import com.lgb.accelerate.utils.http.HttpBaseUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加频道
 * 
 * @author lingb
 * 
 */
public class SignUpTask extends AsyncTask<Map<String, Object>, Object, String> {

	private static class LoginTaskInstance {
		public static final SignUpTask instance = new SignUpTask();
	}

	public static SignUpTask getInstance() {
		return LoginTaskInstance.instance;
	}

	private Handler myHandler;

	@Override
	protected String doInBackground(Map<String, Object>... params) {
		String result;
		myHandler = (Handler) params[0].get(Constant.KEY_HANDLER);

		String name = (String) params[0].get(Constant.KEY_NAME);
		int gender = (int) params[0].get(Constant.KEY_GENDER);
		String email = (String) params[0].get(Constant.KEY_EMAIL);
		String password = (String) params[0].get(Constant.KEY_PASSWORD);
		String stride = (String) params[0].get(Constant.KEY_STRIDE);
		String height = (String) params[0].get(Constant.KEY_HEIGHT);
		String weight = (String) params[0].get(Constant.KEY_WEIGHT);
		String date = (String) params[0].get(Constant.KEY_DOB);

		// 参数map
		Map<String, String> paramsMap = new HashMap<String, String>();

		paramsMap.put(Constant.KEY_NAME, name);
		paramsMap.put(Constant.KEY_GENDER, String.valueOf(gender));
		paramsMap.put(Constant.KEY_EMAIL, email);
		paramsMap.put(Constant.KEY_PASSWORD, password);
		paramsMap.put(Constant.KEY_STRIDE, stride);
		paramsMap.put(Constant.KEY_HEIGHT, height);
		paramsMap.put(Constant.KEY_WEIGHT, weight);
		paramsMap.put(Constant.KEY_DOB, date);

//		user = (User) params[0].get(User.class.getName());
//		String name = user.getName();
//		String gender = String.valueOf(user.getGender());
//		String email = user.getEmail();
//		String password = user.getPassword();
//		String stride = user.getStride();
//		String height = user.getHeight();
//		String weight = user.getWeight();
//		String date = user.getDate();
//
//		// 参数map
//		Map<String, String> paramsMap = new HashMap<String, String>();
//		paramsMap.put("name", name);
//		paramsMap.put("gender", gender);
//		paramsMap.put("email", email);
//		paramsMap.put("password", password);
//		paramsMap.put("stride", stride);
//		paramsMap.put("height", height);
//		paramsMap.put("weight", weight);
//		paramsMap.put("dob", date);

		// 提交
		HttpBaseUtil httpBaseUtil = new HttpBaseUtil();
		result = httpBaseUtil.postData(Constant.URL_SIGNUP, paramsMap);

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
		msg.what = Constant.HANDLER_SIGN_UP_RESULT;
		msg.obj = result;
		myHandler.sendMessage(msg);
	}

}
