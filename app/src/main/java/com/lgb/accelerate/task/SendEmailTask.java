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
public class SendEmailTask extends AsyncTask<Map<String, Object>, Object, String> {

	private Handler myHandler;

	@Override
	protected String doInBackground(Map<String, Object>... params) {
		String result;
		myHandler = (Handler) params[0].get(Constant.KEY_HANDLER);
		String email = (String) params[0].get(Constant.KEY_EMAIL);
		// 参数map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constant.KEY_EMAIL, String.valueOf(email));

		// 提交
		HttpBaseUtil httpBaseUtil = new HttpBaseUtil();
		result = httpBaseUtil.postData(Constant.URL_GET_BACK_PASSWORD, paramsMap);

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
		System.out.println(result);

		if (myHandler == null) {
			return;
		}


		Message msg = new Message();
		msg.what = Constant.HANDLER_SEND_EMAIL_RESULT;
		msg.obj = result;
		myHandler.sendMessage(msg);
	}

}
