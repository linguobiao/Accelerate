package com.lgb.accelerate.utils.http;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class GsonUtil {
	
	private Gson gson = null;
	
	public GsonUtil() {
		gson = new Gson();
	}
	
	public <T> T parseBeen(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return t;
    }
	
	public <T> List<T> parseList(String jsonString, Type type) {
        List<T> list = new ArrayList<T>();
        try {
            list = gson.fromJson(jsonString, type);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }

}
