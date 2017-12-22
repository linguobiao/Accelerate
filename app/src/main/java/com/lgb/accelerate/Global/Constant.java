package com.lgb.accelerate.Global;

/**
 * Created by linguobiao on 16/8/19.
 */
public class Constant {

//    ip:47.88.100.125 端口：9980
    public static final String URL_BASE = "http://47.88.100.125:9980/ActionTracker";

    public static final String URL_SIGNUP = URL_BASE + "/front/user/signup.do?";
    public static final String URL_LOGIN = URL_BASE + "/front/user/login.do?";
    public static final String URL_UPDATE_USER = URL_BASE + "/front/user/updateUser.do?";
    public static final String URL_RESET_USER = URL_BASE + "/front/user/reset_user.do?";
    public static final String URL_GET_BACK_PASSWORD = URL_BASE + "/front/user/get_back_password.do?";
    public static final String URL_LOGOUT = URL_BASE + "/front/user/logout.do?";
    public static final String URL_GET_DATA = URL_BASE + "/front/data/get_steps_distances_calories.do?";
    public static final String URL_GET_TRACK = URL_BASE + "/front/data/get_track.do?";
    public static final String URL_SUBMIT_TRACK = URL_BASE + "/front/data/submit_track.do?";
    public static final String URL_GET_GOAL = URL_BASE + "/front/data/get_goal.do?";
    public static final String URL_SUBMIT_GOAL = URL_BASE + "/front/data/submit_goal.do?";
    public static final String URL_GET_WEEKLY_GOAL_BOOL = URL_BASE + "/front/data/get_weekly_goal_bool.do?";
    public static final String URL_SET_FACEBOOK = URL_BASE + "/front/user/set_facebook.do?";
    public static final String URL_GET_FACEBOOK_FRIEND_DATA = URL_BASE + "/front/data/get_facebook_friend_data.do?";



    public static final String KEY_HANDLER = "KEY_HANDLER";
    public static final String KEY_USER = "user";
    public static final String KEY_ID = "id";
    public static final String KEY_UID = "uid";
    public static final String KEY_SESSION_ID = "session_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PASSWORD_CONFIRM = "password_confirm";
    public static final String KEY_STEPS = "steps";
    public static final String KEY_DISTANCES = "distances";
    public static final String KEY_CALORIES = "calories";
    public static final String KEY_REASON = "reason";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_HEIGHT_CM = "height_cm";
    public static final String KEY_HEIGHT_FT = "height_ft";
    public static final String KEY_HEIGHT_IN = "height_in";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_STRIDE = "stride";
    public static final String KEY_UNITS = "units";
    public static final String KEY_CREATE_DATE = "createDate";
    public static final String KEY_UPDATE_DATE = "update_date";
    public static final String KEY_TRACK = "track";
    public static final String KEY_FROM_DATE = "from_date";
    public static final String KEY_TO_DATE = "to_date";
    public static final String KEY_DOB = "dob";
    public static final String KEY_WEEKLY_GOAL_BOOL = "weekly_goal_bool";
    public static final String KEY_GOAL_BOOL = "goal_bool";
    public static final String KEY_FACEBOOK_ID = "facebookId";
    public static final String KEY_FACEBOOK_IDS = "facebookIds";


    public static final String KEY_RESULT = "result";


    public static final String KEY_UNIT_DISTANCE = "KEY_UNIT_DISTANCE";
    public static final String KEY_UNIT_HEIGHT = "KEY_UNIT_HEIGHT";
    public static final String KEY_UNIT_WEIGHT = "KEY_UNIT_WEIGHT";
    public static final String KEY_UNIT_STRIDE = "KEY_UNIT_STRIDE";

    public static final String KEY_TYPE_POST = "KEY_STATE_POST";
    public static final String KEY_DATE_POST = "KEY_DATE_POST";

    public static final String KEY_FACEBOOK_NAME = "KEY_FB_NAME";
    public static final String KEY_TYPE_INTO = "KEY_TYPE_INTO";

    public static final int TYPE_INTO_NULL = 0;
    public static final int TYPE_INTO_APP = 1;
    public static final int TYPE_INTO_FACEBOOK = 2;

    public static final int UNIT_IMPERIAL = 0;
    public static final int UNIT_METRIC = 1;

    public static final int GENDER_M = 1;
    public static final int GENDER_F = 0;

    public static final int TYPE_GET = 1;
    public static final int TYPE_ADD = 0;

    public static final int TYPE_STEPS = 0;
    public static final int TYPE_DISTANCE = 1;
    public static final int TYPE_CALORIES = 2;

    public static final int TYPE_DAY = 0;
    public static final int TYPE_WEEK = 1;
    public static final int TYPE_MONTH = 2;

    public static final String FALSE = "false";


//    code=0;发送请求的参数不正确，2表示用户没登录，3账号或密码不正确，4获取数据的uid跟登录账号的id不一样

    public static final int HTTP_CODE_0 = 0;
    public static final int HTTP_CODE_2 = 2;
    public static final int HTTP_CODE_3 = 3;
    public static final int HTTP_CODE_4 = 4;



    public static final int HANDLER_LOGIN_RESULT = 11111;
    public static final int HANDLER_SIGN_UP_RESULT = 11112;
    public static final int HANDLER_GET_TRACK_RESULT = 11113;
    public static final int HANDLER_ADD_TRACK_RESULT = 11114;
    public static final int HANDLER_ADD_DAILY_GOAL_RESULT = 11115;
    public static final int HANDLER_GET_DAILY_GOAL_RESULT = 11116;
    public static final int HANDLER_GET_DATA_TODAY_RESULT = 11117;
    public static final int HANDLER_GET_DATA_WEEK_RESULT = 11118;
    public static final int HANDLER_GET_DATA_MONTH_RESULT = 11119;
    public static final int HANDLER_UPDATE_USER_RESULT = 11120;
    public static final int HANDLER_GET_GAOL_BOOL_RESULT = 11121;
    public static final int HANDLER_RESET_USER_RESULT = 11122;
    public static final int HANDLER_SET_FACEBOOK_RESULT = 11123;
    public static final int HANDLER_GET_FACEBOOK_FRIENDS_RESULT = 11124;
    public static final int HANDLER_SEND_EMAIL_RESULT = 11125;



    public static final String INTENT_FINISH_WELCOME_ACTIVITY = "INTENT_FINISH_WELCOME_ACTIVITY";
    public static final String INTENT_SEND_EMAIL_SUCCESS = "INTENT_SEND_EMAIL_SUCCESS";


}
