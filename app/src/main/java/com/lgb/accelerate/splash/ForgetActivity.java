package com.lgb.accelerate.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.ForgetPresenter;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.KeyBoardUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class ForgetActivity extends Activity implements BaseViewApi, View.OnClickListener{

    private BasePresenterApi presenter;
    private ProgressDialog dialog;
    private EditText edit_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initUI();
        presenter = new ForgetPresenter(this, this);
    }

    private void initUI() {
        findViewById(R.id.txt_back).setOnClickListener(this);
        findViewById(R.id.txt_submit).setOnClickListener(this);
        edit_email = (EditText) findViewById(R.id.edit_email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                ForgetActivity.this.finish();
                break;
            case R.id.txt_submit:
                Map<String, Object> map = new HashMap<>();
                map.put(Constant.KEY_EMAIL, edit_email.getText().toString());
                presenter.post(map);
                break;
        }
    }
    @Override
    public void showToast(String msg) {
        DialogUtils.hideDialog(dialog);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postSuccess(Object object) {
        DialogUtils.hideDialog(dialog);
        sendBroadcast(new Intent(Constant.INTENT_SEND_EMAIL_SUCCESS));
        startActivity(new Intent(this, WelcomeActivity.class));
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
    public void onStop() {
        super.onStop();
        KeyBoardUtils.hideKeyboard3(this, edit_email);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
    }

}
