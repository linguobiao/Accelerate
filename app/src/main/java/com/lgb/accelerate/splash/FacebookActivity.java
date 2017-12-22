package com.lgb.accelerate.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.FacebookFriend;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.SetFacebookPresenter;
import com.lgb.accelerate.main.MainActivity;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.SpUtils;
import com.lgb.accelerate.utils.http.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/16.
 */
public class FacebookActivity extends Activity implements BaseViewApi, View.OnClickListener{

    private BasePresenterApi presenter;
    private ProgressDialog dialog;

    CallbackManager callbackManager;
    private TextView txt_approve;
    AccessToken access_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        initUI();
        presenter = new SetFacebookPresenter(this, this);
    }

    private void initUI() {
        findViewById(R.id.txt_decline).setOnClickListener(this);
        txt_approve = (TextView) findViewById(R.id.txt_approve);
        txt_approve.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("testFacebook", "onSuccess");
                //593659967478776
                Log.i("testFacebook", loginResult.getAccessToken().getUserId());
                for (String str : loginResult.getAccessToken().getPermissions()) {
                    Log.i("testFacebook", "permission = " + str);

                }
                access_token = loginResult.getAccessToken();
                getInfo();

            }

            @Override
            public void onCancel() {
                Log.i("testFacebook", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("testFacebook", "onError");
            }
        });

//        findViewById(R.id.txt_sign).setOnClickListener(this);
    }

    private void getInfo() {
        GraphRequestBatch batch = new GraphRequestBatch(
                GraphRequest.newMeRequest(
                        access_token,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject jsonObject,
                                    GraphResponse response) {
                                if (response == null || response.getJSONObject() == null) return;
                                Log.i("testFacebook", "me = " + response.getJSONObject().toString());
                                // Application code for user
//                                me = {"name":"Guobiao Lin","id":"593693334142106"}
//                                me = {"name":"Guobiao Lin","id":"834069293437841"}
                                GsonUtil gsonUtil = new GsonUtil();
                                FacebookFriend me = gsonUtil.parseBeen(response.getJSONObject().toString(), FacebookFriend.class);
                                SpUtils.getInstance().putString(Constant.KEY_FACEBOOK_NAME, me.getName());
                                SpUtils.getInstance().putString(Constant.KEY_FACEBOOK_ID, me.getId());

                                Map<String, Object> map = new HashMap<>();
                                map.put(Constant.KEY_ID, SpUtils.getInstance().getInt(Constant.KEY_ID, Global.DEF_ID));
                                map.put(Constant.KEY_FACEBOOK_ID, me.getId());
                                presenter.post(map);

                            }
                        }),
                GraphRequest.newMyFriendsRequest(
                        access_token,
                        new GraphRequest.GraphJSONArrayCallback() {
                            @Override
                            public void onCompleted(
                                    JSONArray jsonArray,
                                    GraphResponse response) {
                                if (response == null || response.getJSONObject() == null) return;
                                Log.i("testFacebook", "friends = " + response.getJSONObject().toString());
                                // Application code for users friends
//                                friends = {"data":[{"name":"张辉","id":"118520625266397"}],"paging":{"cursors":{"before":"QVFIUmtkay1UM3EwU2V0am56UTQyMnlpMlFtdDRZAd1c1Q3o0cVRvWi1UdXJzUktrZA1h0ZAW1lWFVlWkNmT3NyMk5fTUNrUjdpZATlwXzlmVF9iTlpwV05Qd0Vn","after":"QVFIUmtkay1UM3EwU2V0am56UTQyMnlpMlFtdDRZAd1c1Q3o0cVRvWi1UdXJzUktrZA1h0ZAW1lWFVlWkNmT3NyMk5fTUNrUjdpZATlwXzlmVF9iTlpwV05Qd0Vn"}},"summary":{"total_count":3}}
//                                friends = {"data":[],"summary":{"total_count":5}}
                                JSONObject object = response.getJSONObject();
                                if (object != null) {
                                    if (object.has("data")) {
                                        try {
                                            String friends = object.getString("data");
                                            SpUtils.getInstance().putString(Constant.KEY_FACEBOOK_IDS, friends);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            }
                        })
        );
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                Log.i("testFacebook", "onBatchCompleted");
                // Application code for when the batch finishes
            }
        });
        batch.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.i("testFacebook", "onActivityResult   " + requestCode + ",  " + resultCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_approve:

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                LoginManager.getInstance().logInWithReadPermissions(FacebookActivity.this, Arrays.asList("public_profile", "user_friends"));

                    }
                });
                break;
            case R.id.txt_decline:

                SpUtils.getInstance().putInt(Constant.KEY_TYPE_INTO, Constant.TYPE_INTO_APP);
                startActivity(new Intent(FacebookActivity.this, MainActivity.class));
                FacebookActivity.this.finish();
                break;
        }
    }
    @Override
    public void showToast(String msg) {
        DialogUtils.hideDialog(dialog);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.i("test", "toast = " + msg);
    }

    @Override
    public void postSuccess(Object object) {
        DialogUtils.hideDialog(dialog);

        SpUtils.getInstance().putInt(Constant.KEY_TYPE_INTO, Constant.TYPE_INTO_FACEBOOK);
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void postFail(ResultFail fail) {

    }

    @Override
    public void showDialog() {
        if (dialog == null) {
            dialog = DialogUtils.showProgressDialog(this, getString(R.string.public_loading));
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    presenter.stopLoad();
                }
            });
        }
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
    }
}
