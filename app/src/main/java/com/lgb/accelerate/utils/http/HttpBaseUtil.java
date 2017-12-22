package com.lgb.accelerate.utils.http;

import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.utils.SpUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpBaseUtil {

	private static final String TAG = "HttpBaseUtil";

	private final static int CONNECT_TIMEOUT = 10 * 1000;
	private final static int READ_TIMEOUT = 60 * 1000;

	/**
	 * 保持单个
	 */
	private static OkHttpClient client = null;

	public HttpBaseUtil() {
		if (client == null) {
			client = new OkHttpClient.Builder()
					.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
					.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
					.build();
		}
	}
	/** 设置请求头
	 * @param headersParams
	 * @return
	 */
	private Headers SetHeaders(Map<String, String> headersParams){
		Headers headers=null;
		okhttp3.Headers.Builder headersbuilder=new okhttp3.Headers.Builder();

		if(headersParams != null)
		{
			Iterator<String> iterator = headersParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				headersbuilder.add(key, headersParams.get(key));
				Log.d("get http", "get_headers==="+key+"===="+headersParams.get(key));
			}
		}
		headers=headersbuilder.build();

		return headers;
	}

	/**
	 * post请求参数
	 * @param BodyParams
	 * @return
	 */
	private RequestBody SetRequestBody(Map<String, String> BodyParams){
		RequestBody body;
		FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
		if(BodyParams != null){
			Iterator<String> iterator = BodyParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				formEncodingBuilder.add(key, BodyParams.get(key));
				Log.d("okhttp", "post_Params==="+key+"===="+BodyParams.get(key));
			}
		}
		body = formEncodingBuilder.build();
		return body;
	}



	public String postData(final String urlStr, Map<String, String> paramsMap) {

		String result = null;

		if (urlStr == null) {
			return null;
		}

		RequestBody formBody = SetRequestBody(paramsMap);

		String session_id = SpUtils.getInstance().getString(Constant.KEY_SESSION_ID, "");
		Log.d("okhttp", "urlStr==="+urlStr);
		Request request = new Request.Builder()
				.url(urlStr)
				.post(formBody)
				.header("Cookie", session_id)
				.build();

		// 访问返回
		Response response = null;
		Call call;
		try {
			call = client.newCall(request);

			response = call.execute();
			if (response.isSuccessful()) {
				result = response.body().string();
				if (urlStr.equalsIgnoreCase(Constant.URL_LOGIN)) {
					// 保存sessionID
					String sessionID = response.header("Set-Cookie");
					if (sessionID != null && !sessionID.isEmpty()) {
						String[] array = sessionID.split(";");
						if (array.length > 0) {
							SpUtils.getInstance().putString(Constant.KEY_SESSION_ID, array[0]);
						}
					}
				}
			} else {
            	/*
            	 * 我们服务器定义
            	 *  1、404（404访问）2、NotLogin（用户没有登录，访问被拦截）3、ParamException（参数异常）4、PrimissionDenied（没有访问权限）5、Exception（其他异常）
            	 */
				Log.d(TAG, "server code:" + response.code() + "\n" + response.body().string());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.body().close();
			}
		}

//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
////				String str = response.body().string();
////				Log.i("wangshu", str);
//
////				response = call.execute();
//				if (response.isSuccessful()) {
//					result[0] = response.body().string();
//					if (urlStr.equalsIgnoreCase(Constant.URL_LOGIN)) {
//						// 保存sessionID
//						String sessionID = response.header("Set-Cookie");
//						if (sessionID != null && !sessionID.isEmpty()) {
//							String[] array = sessionID.split(";");
//							if (array.length > 0) {
//								SpUtils.getInstance().putString(Constant.KEY_SESSION_ID, array[0]);
//							}
//						}
//					}
//				} else {
//            	/*
//            	 * 我们服务器定义
//            	 *  1、404（404访问）2、NotLogin（用户没有登录，访问被拦截）3、ParamException（参数异常）4、PrimissionDenied（没有访问权限）5、Exception（其他异常）
//            	 */
//					Log.d(TAG, "server code:" + response.code() + "\n" + response.body().string());
//				}
//
//			}
//
//		});
		Log.e("okhttp", "result=" + result);
		return result;
	}

//	/**
//	 * 使用post方法发送数据
//	 * @param urlStr
//	 * @param paramsMap
//	 * @return
//	 */
//	public String postData(String urlStr, Map<String, String> paramsMap) {
//
//		String result = null;
//
//		if (urlStr == null) {
//			return result;
//		}
//		// 访问
//		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
//		if (paramsMap != null) {
//			for (Entry<String, String> entry : paramsMap.entrySet()) {
//				Log.i("test", "temp        " + entry.getKey() + ",   " + entry.getValue());
//				formEncodingBuilder.add(entry.getKey(), entry.getValue());
//
//			}
//		}
//		com.squareup.okhttp.Request.Builder requestBuilder = new Request.Builder();
//		requestBuilder.url(urlStr);
//		Log.i("test", "url = " + urlStr);
//		requestBuilder.post(formEncodingBuilder.build());
//		String session_id = SpUtils.getInstance().getString(Constant.KEY_SESSION_ID, null);
//		if (session_id != null) {
//			requestBuilder.header("Cookie", session_id);
//		}
//		Request request = requestBuilder.build();
//		// 访问返回
//		Response response = null;
//		Call call;
//		try {
//			call = client.newCall(request);
//
//			response = call.execute();
//			if (response.isSuccessful()) {
//				result = response.body().string();
//				if (urlStr.equalsIgnoreCase(Constant.URL_LOGIN)) {
//					// 保存sessionID
//					String sessionID = response.header("Set-Cookie");
//					if (sessionID != null && !sessionID.isEmpty()) {
//						String[] array = sessionID.split(";");
//						if (array.length > 0) {
//							SpUtils.getInstance().putString(Constant.KEY_SESSION_ID, array[0]);
//						}
//					}
//				}
//			} else {
//            	/*
//            	 * 我们服务器定义
//            	 *  1、404（404访问）2、NotLogin（用户没有登录，访问被拦截）3、ParamException（参数异常）4、PrimissionDenied（没有访问权限）5、Exception（其他异常）
//            	 */
//				Log.d(TAG, "server code:" + response.code() + "\n" + response.body().string());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (response != null) {
//				try {
//					response.body().close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		Log.i("test", "result = " + result);
//		return result;
//	}
//
//
//	/**
//	 * 使用post方法发送数据
//	 * @param urlStr
//	 * @param paramsMap
//	 * @return
//	 */
//	public String postData2(String urlStr, Map<String, String> paramsMap) {
//
//		String result = null;
//
//		if (urlStr == null) {
//			return result;
//		}
//
////		Log.d(TAG, "url: " + urlStr);
////		OkHttpClient client = new OkHttpClient();
//		// 访问
//		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
//		if (paramsMap != null) {
//			for (Entry<String, String> entry : paramsMap.entrySet()) {
//				formEncodingBuilder.add(entry.getKey(), entry.getValue());
//			}
//		}
//		com.squareup.okhttp.Request.Builder requestBuilder = new Request.Builder();
//		requestBuilder.url(urlStr);
//		requestBuilder.post(formEncodingBuilder.build());
//
//		Request request = requestBuilder.build();
//		// 访问返回
//		Response response = null;
//		Call call = null;
//		try {
//			call = client.newCall(request);
//
//			response = call.execute();
//			if (response.isSuccessful()) {
//				result = response.body().string();
//			} else {
//            	/*
//            	 * 我们服务器定义
//            	 *  1、404（404访问）2、NotLogin（用户没有登录，访问被拦截）3、ParamException（参数异常）4、PrimissionDenied（没有访问权限）5、Exception（其他异常）
//            	 */
//				Log.d(TAG, "server code:" + response.code() + "\n" + response.body().string());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (response != null) {
//				try {
//					response.body().close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return result;
//	}
//
//
//	/**
//	 * 使用post方法发送数据
//	 * @param urlStr
//	 * @param paramsMap
//	 * @return
//	 */
//	public Call postData(String urlStr, Map<String, String> paramsMap, Callback callback) {
//
//		if (urlStr == null || callback == null) {
//			return null;
//		}
////		Log.d(TAG, "url: " + urlStr);
//
////		OkHttpClient client = new OkHttpClient();
//		// 访问参数
//		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
//		if (paramsMap != null) {
//			for (Entry<String, String> entry : paramsMap.entrySet()) {
//				formEncodingBuilder.add(entry.getKey(), entry.getValue());
//			}
//		}
//		//
//		com.squareup.okhttp.Request.Builder requestBuilder = new Request.Builder();
//		requestBuilder.url(urlStr);
//		requestBuilder.post(formEncodingBuilder.build());
//
//		Request request = requestBuilder.build();
//		// 访问返回
//		Call call = client.newCall(request);
//		call.enqueue(callback);
//		return call;
//	}
//
//
//	/**
//	 * 上传文件
//	 * @param urlStr
//	 * @param paramsMap
//	 * @param filesMap
//	 * @param mediaType
//	 */
//	public ServerReturn postFile(String urlStr, Map<String, String> paramsMap, Map<String, File> filesMap, MediaType mediaType) {
//
//		ServerReturn serverReturn = null;
//
//		if (urlStr == null) {
//			return serverReturn;
//		}
//
//		MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
//
//		//遍历map中所有参数到builder
//		if (paramsMap != null) {
//			for (Entry<String, String> entry : paramsMap.entrySet()) {
////				Log.d(TAG, "key:" + entry.getKey() + ", value:" + entry.getValue());
//				multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
//			}
//		}
//
//		//遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
//		if (filesMap != null) {
//			for (Entry<String, File> entry : filesMap.entrySet()) {
////				Log.d(TAG, "key:" + entry.getKey());
//				multipartBuilder.addFormDataPart(entry.getKey(), entry.getKey(), RequestBody.create(mediaType, entry.getValue()));
//			}
//		}
//
//		//构建请求体
//		RequestBody requestBody = multipartBuilder.build();
//
//		//构建请求
//		com.squareup.okhttp.Request.Builder requestBuilder = new Request.Builder();
//		requestBuilder.url(urlStr);
//		requestBuilder.post(requestBody);
//
//		Request request = requestBuilder.build();
//
//		//发送异步请求
//		// 访问返回
//		Response response = null;
//		Call call = null;
//		try {
//			call = client.newCall(request);
//			response = call.execute();
//			if (response.isSuccessful()) {
//				String resultStr = response.body().string();
//				Log.d(TAG, "上传文件返回结果：" + resultStr);
////                serverReturn = JsonServerUtil.parseServerReturn(resultStr);
//
//			} else {
//            	/*
//            	 * 我们服务器定义
//            	 *  1、404（404访问）2、NotLogin（用户没有登录，访问被拦截）3、ParamException（参数异常）4、PrimissionDenied（没有访问权限）5、Exception（其他异常）
//            	 */
//				Log.d(TAG, "server code:" + response.code() + "\n" + response.body().string());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (response != null) {
//				try {
//					response.body().close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		return serverReturn;
//	}
//
//
//	/**
//	 * 上传文件
//	 * @param urlStr
//	 * @param paramsMap
//	 * @param filesMap
//	 * @param mediaType
//	 * @param callback
//	 */
//	public Call postFile(String urlStr, Map<String, String> paramsMap, Map<String, File> filesMap, MediaType mediaType, Callback callback) {
//
//		if (urlStr == null) {
//			return null;
//		}
//
//		MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
//
//		//遍历map中所有参数到builder
//		if (paramsMap != null) {
//			for (Entry<String, String> entry : paramsMap.entrySet()) {
//				multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
//			}
//		}
//
//		//遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
//		if (filesMap != null) {
//			for (Entry<String, File> entry : filesMap.entrySet()) {
//				multipartBuilder.addFormDataPart(entry.getKey(), entry.getKey(), RequestBody.create(mediaType, entry.getValue()));
//			}
//		}
//
//		//构建请求体
//		RequestBody requestBody = multipartBuilder.build();
//
//		//构建请求
//		com.squareup.okhttp.Request.Builder requestBuilder = new Request.Builder();
//		requestBuilder.url(urlStr);
//		requestBuilder.post(requestBody);
//
//		Request request = requestBuilder.build();
//
//		//发送异步请求
//		Call call = client.newCall(request);
//		call.enqueue(callback);
//
//		return call;
//	}
//
//
//	/**
//	 * 下载文件
//	 * @param urlStr
//	 * @param callback
//	 */
//	public Call downloadFile(String urlStr, Callback callback) {
//		if (urlStr == null || callback == null) {
//			return null;
//		}
//		Log.d(TAG, "url: " + urlStr);
//
//		//
//		com.squareup.okhttp.Request.Builder requestBuilder = new Request.Builder();
//		requestBuilder.url(urlStr);
//
//		Request request = requestBuilder.build();
//
//		// 访问返回
//		Call call = client.newCall(request);
//		call.enqueue(callback);
//
//		return call;
//	}
//
//	/**
//	 * 下载文件
//	 * @param path
//	 * @param fileName
//	 * @param downloadUrl
//	 * @return
//	 */
//	public boolean downloadFile(String path, String fileName, String downloadUrl) {
//		boolean isSuccess = false;
//
//		if (path == null || fileName == null || downloadUrl == null) {
//			return isSuccess;
//		}
//		Log.d(TAG, "url: " + downloadUrl);
//
//		//
//		com.squareup.okhttp.Request.Builder requestBuilder = new Request.Builder();
//		requestBuilder.url(downloadUrl);
//
//		Request request = requestBuilder.build();
//		// 访问返回
//		Response response = null;
//		Call call = null;
//		try {
//			call = client.newCall(request);
//
//			response = call.execute();
//			if (response.isSuccessful()) {
//				// 下载的流
//				InputStream is = response.body().byteStream();
//
////				FileUitls fileUitls = new FileUitls();
////				fileUitls.writeToSD(path, fileName, is);
//
//				isSuccess = true;
//			} else {
//            	/*
//            	 * 我们服务器定义
//            	 *  1、404（404访问）2、NotLogin（用户没有登录，访问被拦截）3、ParamException（参数异常）4、PrimissionDenied（没有访问权限）5、Exception（其他异常）
//            	 */
//				Log.d(TAG, "server code:" + response.code() + "\n" + response.body().string());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (response != null) {
//					response.body().close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return isSuccess;
//	}
}
