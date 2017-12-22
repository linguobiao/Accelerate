package com.lgb.accelerate.imp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.model.BaseModelApi;
import com.lgb.accelerate.api.model.FriendsModelApi;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.DataSteps;
import com.lgb.accelerate.bean.FacebookFriend;
import com.lgb.accelerate.bean.Friend;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.model.AddTracksModel;
import com.lgb.accelerate.imp.model.FriendsModel;
import com.lgb.accelerate.utils.CheckUtils;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.http.GsonUtil;
import com.lgb.accelerate.utils.http.InternetUtil;
import com.lgb.accelerate.utils.http.ResultUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class FriendsPresenter implements BasePresenterApi {

    private Context context;
    private BaseViewApi view;
    private FriendsModelApi model;

    private List<Friend> lists;
    private Map<String, String> friendMap;

    public FriendsPresenter(Context context, BaseViewApi view) {
        this.context = context;
        this.view = view;
        model = new FriendsModel();
        lists = new ArrayList<>();
        friendMap = new HashMap<>();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 获取自己的步数结果
                case Constant.HANDLER_GET_DATA_TODAY_RESULT:
                    String resultStr_data = (String) msg.obj;
                    boolean isResultSuccess_data = ResultUtil.isResultSuccess(resultStr_data);
                    // 成功
                    if (isResultSuccess_data) {
                        Map<String, String> map_data = ResultUtil.parseDataDay(resultStr_data);
                        int steps = Integer.parseInt(map_data.get(Constant.KEY_STEPS));
                        // 把自己当天的步数放到list里面
                        Friend friend = new Friend();
                        friend.setName(SpUtils.getInstance().getString(Constant.KEY_NAME, Global.DEF_NAME));
                        friend.setSteps(steps);
                        lists.add(friend);


                        int type = SpUtils.getInstance().getInt(Constant.KEY_TYPE_INTO, Constant.TYPE_INTO_NULL);
                        // 如果没有关联Facebook， 就只返回自己的
                        if (type != Constant.TYPE_INTO_FACEBOOK) {
                            view.postSuccess(lists);
                            break;
                        }
                        String facebookFriends = SpUtils.getInstance().getString(Constant.KEY_FACEBOOK_IDS, null);
                        // 没有facebook好友，返回自己的
                        if (facebookFriends == null) {
                            view.postSuccess(lists);
                            break;
                        }
                        GsonUtil gsonUtil = new GsonUtil();
                        List<FacebookFriend> listFb = gsonUtil.parseList(facebookFriends, new TypeToken<List<FacebookFriend>>(){}.getType());
                        // 没有facebook好友，返回自己的
                        if (listFb == null || listFb.size() == 0) {
                            view.postSuccess(lists);
                            break;
                        }
                        // 开始获取好友的步数
                        String facebookIds = "";
                        //将所有好友的id和名字放到map中，将所有id组成字符串，逗号分开
                        for (FacebookFriend facebookFriend : listFb) {
                            friendMap.put(facebookFriend.getId(), facebookFriend.getName());
                            facebookIds = facebookIds + facebookFriend.getId() + ",";
                        }
                        // 没有facebook好友，返回自己的
                        if (facebookIds.length() <= 1) {
                            view.postSuccess(lists);
                            break;
                        }
                        facebookIds = facebookIds.substring(0, facebookIds.length() - 1);

                        Map<String, Object> map_friends = new HashMap<>();
                        map_friends.put(Constant.KEY_FACEBOOK_IDS, facebookIds);
                        model.postFriends(map_friends, handler);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr_data);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.public_Failure));
                    }

                    break;
                // 获取facebook好友结果返回
                case Constant.HANDLER_GET_FACEBOOK_FRIENDS_RESULT:
                    String resultStr = (String) msg.obj;
                    boolean isResultSuccess = ResultUtil.isResultSuccess(resultStr);
                    // 成功
                    if (isResultSuccess) {
                        List<Friend> friendList = ResultUtil.parseFriends(resultStr, friendMap);
                        // 把获取回来的所有好友Friend放到list
                        for (Friend friend : friendList) {
                            lists.add(friend);
                        }

                        view.postSuccess(lists);
                    }
                    // 失败
                    else {
                        ResultFail fail = ResultUtil.parseFailResult(resultStr);
                        view.postFail(fail);
                        view.showToast(context.getString(R.string.public_Failure));
                    }
                    break;
            }
        }
    };

    @Override
    public void post(Map<String, Object> map) {
        if (!InternetUtil.isConnectingToInternet(context)) {
            view.showToast(context.getString(R.string.public_No_network));
            return;
        }

        Global.type_data_time = Constant.TYPE_DAY;
        map = new HashMap<>();
        map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, 0));
        Calendar cal_from = Calendar.getInstance();
        Calendar cal_to = Calendar.getInstance();
        map.put(Constant.KEY_FROM_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_from.getTime()));
        map.put(Constant.KEY_TO_DATE, FormatHelper.sdf_yyyy_MM_dd.format(cal_to.getTime()));

        lists.clear();
        friendMap.clear();
        view.showDialog();
        model.postData(map, handler);
    }

    @Override
    public void stopLoad() {
        model.stopLoad();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
