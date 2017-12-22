package com.lgb.accelerate.utils.http;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.bean.DataBase;
import com.lgb.accelerate.bean.DataCalories;
import com.lgb.accelerate.bean.DataDistance;
import com.lgb.accelerate.bean.DataSteps;
import com.lgb.accelerate.bean.FacebookFriend;
import com.lgb.accelerate.bean.Friend;
import com.lgb.accelerate.bean.Profile;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.bean.Track;
import com.lgb.accelerate.utils.CalculateUtils;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.UnitUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/24.
 */
public class ResultUtil {

    /**
     * 解析服务器返回的数据
     * @param resultStr
     * @return
     */
    public static boolean isResultSuccess(String resultStr) {

        if (resultStr == null || resultStr.isEmpty()) {
            return false;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {
                if (all.has(Constant.KEY_RESULT)) {
                    String result = (all.getString(Constant.KEY_RESULT));
                    if (result.equals("y")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
        return false;
    }

    public static void parseLogin(String resultStr) {

        if (resultStr == null || resultStr.isEmpty()) {
            return;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {
                if (all.has(Constant.KEY_SESSION_ID)) {
                }
                if (all.has(Constant.KEY_STEPS)) {

                }
                if (all.has(Constant.KEY_DISTANCES)) {

                }
                if (all.has(Constant.KEY_CALORIES)) {

                }
                if (all.has(Constant.KEY_USER)) {
                    String userStr = all.getString(Constant.KEY_USER);
                    GsonUtil gsonUtil = new GsonUtil();
                    Profile profile = gsonUtil.parseBeen(userStr, Profile.class);
                    Log.i("test", "profile = " + profile.toString());
                    SpUtils.getInstance().putString(Constant.KEY_NAME, profile.getName());
                    SpUtils.getInstance().putString(Constant.KEY_EMAIL, profile.getEmail());
                    SpUtils.getInstance().putString(Constant.KEY_CREATE_DATE, profile.getCreateDate());
                    SpUtils.getInstance().putString(Constant.KEY_UPDATE_DATE, profile.getUpdateDate());
                    SpUtils.getInstance().putInt(Constant.KEY_ID, profile.getId());
                    SpUtils.getInstance().putInt(Constant.KEY_GENDER, profile.getGender());
                    SpUtils.getInstance().putInt(Constant.KEY_UNITS, profile.getUnits());
                    SpUtils.getInstance().putDouble(Constant.KEY_HEIGHT, profile.getHeight());
                    SpUtils.getInstance().putDouble(Constant.KEY_WEIGHT, profile.getWeight());
                    SpUtils.getInstance().putDouble(Constant.KEY_STRIDE, profile.getStride());

                }
            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
    }

    public static void parseUpdateUser(String resultStr) {

        if (resultStr == null || resultStr.isEmpty()) {
            return;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {
                if (all.has(Constant.KEY_SESSION_ID)) {
                }
                if (all.has(Constant.KEY_STEPS)) {

                }
                if (all.has(Constant.KEY_DISTANCES)) {

                }
                if (all.has(Constant.KEY_CALORIES)) {

                }
                if (all.has(Constant.KEY_USER)) {
                    String userStr = all.getString(Constant.KEY_USER);
                    GsonUtil gsonUtil = new GsonUtil();
                    Profile profile = gsonUtil.parseBeen(userStr, Profile.class);
                    SpUtils.getInstance().putString(Constant.KEY_NAME, profile.getName());
                    SpUtils.getInstance().putString(Constant.KEY_UPDATE_DATE, profile.getUpdateDate());
                    SpUtils.getInstance().putInt(Constant.KEY_GENDER, profile.getGender());
                    SpUtils.getInstance().putDouble(Constant.KEY_HEIGHT, profile.getHeight());
                    SpUtils.getInstance().putDouble(Constant.KEY_WEIGHT, profile.getWeight());
                    SpUtils.getInstance().putDouble(Constant.KEY_STRIDE, profile.getStride());

                }
            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
    }


    public static Map<String, String> parseGetTrack(String resultStr) {

        Map<String, String> map = FormatHelper.getDataBaseMap();

        if (resultStr == null || resultStr.isEmpty()) {
            return map;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {

                if (all.has(Constant.KEY_TRACK)) {
                    String userStr = all.getString(Constant.KEY_TRACK);
                    GsonUtil gsonUtil = new GsonUtil();
                    Track track = gsonUtil.parseBeen(userStr, Track.class);
                    if (track != null) {
                        String distance = CalculateUtils.calculateDistance(track.getSteps(), track.getStride());
                        String calories = CalculateUtils.calculateCalories(track.getSteps(), track.getWeight());
                        map.put(Constant.KEY_STEPS, String.valueOf(track.getSteps()));
                        map.put(Constant.KEY_DISTANCES, distance);
                        map.put(Constant.KEY_CALORIES, calories);

                        return map;
                    }
                }
            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
        return map;
    }

    public static boolean isWeelGoalBool(String resultStr) {
        if (resultStr == null || resultStr.isEmpty()) {
            return false;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {

                if (all.has(Constant.KEY_WEEKLY_GOAL_BOOL)) {
                    String str = all.getString(Constant.KEY_WEEKLY_GOAL_BOOL);
                    if (str.equalsIgnoreCase("true")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
        return false;
    }

    public static String parseGetDailyGoal(String resultStr) {

        if (resultStr == null || resultStr.isEmpty()) {
            return null;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {

                if (all.has(Constant.KEY_STEPS)) {
                    return all.getString(Constant.KEY_STEPS);
                }
            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> parseDataDay(String resultStr) {
        Map<String, String> map = FormatHelper.getDataBaseMap();
        if (resultStr == null || resultStr.isEmpty()) {
            return map;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {

                GsonUtil gsonUtil = new GsonUtil();
                if (all.has(Constant.KEY_STEPS)) {
                    String steps =  all.getString(Constant.KEY_STEPS);
                    steps = steps.replace("[", "").replace("]", "");
                    DataSteps dataSteps = gsonUtil.parseBeen(steps, DataSteps.class);
                    if (dataSteps == null) {
                        map.put(Constant.KEY_STEPS, "0");
                    } else {
                        map.put(Constant.KEY_STEPS, String.valueOf((int)dataSteps.getValue()));
                    }
                }
                if (all.has(Constant.KEY_DISTANCES)) {
                    String distance =  all.getString(Constant.KEY_DISTANCES);
                    distance = distance.replace("[", "").replace("]", "");
                    DataDistance dataDistance = gsonUtil.parseBeen(distance, DataDistance.class);
                    if (dataDistance == null) {
                        map.put(Constant.KEY_DISTANCES, "0.00");
                    } else {
                        if (UnitUtils.isMetric(Constant.KEY_UNIT_DISTANCE)) {
                            map.put(Constant.KEY_DISTANCES, FormatHelper.df_0_00().format(dataDistance.getValue()));
                        } else {
                            double dis = UnitUtils.kmToMile(dataDistance.getValue());
                            map.put(Constant.KEY_DISTANCES, FormatHelper.df_0_0().format(dis));
                        }
                    }
                }
                if (all.has(Constant.KEY_CALORIES)) {
                    String cal = all.getString(Constant.KEY_CALORIES);
                    cal = cal.replace("[", "").replace("]", "");
                    DataCalories dataCalories = gsonUtil.parseBeen(cal, DataCalories.class);
                    if (dataCalories == null) {
                        map.put(Constant.KEY_CALORIES, "0.0");
                    } else {
                        map.put(Constant.KEY_CALORIES, FormatHelper.df_0_0().format(dataCalories.getValue()));
                    }
                }


            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
        return map;
    }


    public static List<Data> parseData(String resultStr) {
        List<Data> dataList = new ArrayList<>();
        if (resultStr == null || resultStr.isEmpty()) {
            return dataList;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {

                GsonUtil gsonUtil = new GsonUtil();
                if (Global.type_data == Constant.TYPE_STEPS && all.has(Constant.KEY_STEPS)) {
                    String steps =  all.getString(Constant.KEY_STEPS);

                    dataList = gsonUtil.parseList(steps, new TypeToken<List<DataSteps>>(){}.getType());
                } else if (Global.type_data == Constant.TYPE_DISTANCE && all.has(Constant.KEY_DISTANCES)) {
                    String distance =  all.getString(Constant.KEY_DISTANCES);

                    List<Data> distanceList = gsonUtil.parseList(distance, new TypeToken<List<DataDistance>>(){}.getType());
                    if (UnitUtils.isMetric(Constant.KEY_UNIT_DISTANCE)) {
                        dataList = distanceList;
                    } else {
                        dataList = UnitUtils.kmToMileForData(distanceList);
                    }
                } else if (Global.type_data == Constant.TYPE_CALORIES && all.has(Constant.KEY_CALORIES)) {
                    String distance = all.getString(Constant.KEY_CALORIES);

                    dataList = gsonUtil.parseList(distance, new TypeToken<List<DataCalories>>(){}.getType());
                }


            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
        return dataList;
    }

    public static List<Friend> parseFriends(String resultStr, Map<String, String> friendMap) {

        List<Friend> friendList = new ArrayList<>();
        if (resultStr == null || resultStr.isEmpty()) {
            return friendList;
        }

        try {
            JSONObject all = new JSONObject(resultStr);
            if (all != null) {

                if (all.has("datas")) {
                    JSONObject datas = new JSONObject(all.getString("datas"));

                    for (Map.Entry<String, String> entry : friendMap.entrySet()) {

                        if (datas.has(entry.getKey())) {
                            JSONObject str = new JSONObject(datas.getString(entry.getKey()));
                            if (str.has("total_steps")) {
                                int steps = str.getInt("total_steps");
                                Friend friend = new Friend();
                                friend.setName(entry.getValue());
                                friend.setSteps(steps);
                                friendList.add(friend);
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            Log.d("ResultUtils", resultStr);
            e.printStackTrace();
        }
        return friendList;
    }


    public static ResultFail parseFailResult(String resultStr) {
        GsonUtil gsonUtil = new GsonUtil();
        ResultFail fail = gsonUtil.parseBeen(resultStr, ResultFail.class);
        return fail;
    }
}
