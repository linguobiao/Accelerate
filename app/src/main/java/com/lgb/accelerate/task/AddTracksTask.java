package com.lgb.accelerate.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
public class AddTracksTask extends AsyncTask<Map<String, Object>, Object, String> {

	private Handler myHandler;

	@Override
	protected String doInBackground(Map<String, Object>... params) {
		String result;
		myHandler = (Handler) params[0].get(Constant.KEY_HANDLER);
		int id = (int) params[0].get(Constant.KEY_ID);
		String steps = (String) params[0].get(Constant.KEY_STEPS);
		double weight = (double) params[0].get(Constant.KEY_WEIGHT);
		double stride = (double) params[0].get(Constant.KEY_STRIDE);
		// 参数map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(Constant.KEY_UID, String.valueOf(id));
		paramsMap.put(Constant.KEY_STEPS, String.valueOf(steps));
		paramsMap.put(Constant.KEY_WEIGHT, String.valueOf(weight));
		paramsMap.put(Constant.KEY_STRIDE, String.valueOf(stride));

		// 提交
		HttpBaseUtil httpBaseUtil = new HttpBaseUtil();
		result = httpBaseUtil.postData(Constant.URL_SUBMIT_TRACK, paramsMap);

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
		msg.what = Constant.HANDLER_ADD_TRACK_RESULT;
		msg.obj = result;
		myHandler.sendMessage(msg);
	}

}
