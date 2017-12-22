package com.lgb.accelerate.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.adapter.EditListener;
import com.lgb.accelerate.api.presenter.LoginPresenterApi;
import com.lgb.accelerate.api.view.LoginActivityApi;
import com.lgb.accelerate.bean.ResultLogin;
import com.lgb.accelerate.imp.presenter.LoginPresenter;
import com.lgb.accelerate.main.MainActivity;
import com.lgb.accelerate.utils.DialogUtils;

/**
 * Created by linguobiao on 16/8/16.
 */
public class LoginActivity extends Activity implements LoginActivityApi, View.OnClickListener {

    private LoginPresenterApi presenter;
    private EditText edit_account, edit_password;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initReceiver();
        presenter = new LoginPresenter(this, this);
    }

    private void initUI() {
        findViewById(R.id.txt_login).setOnClickListener(this);
        findViewById(R.id.txt_forget).setOnClickListener(this);
        findViewById(R.id.txt_back).setOnClickListener(this);
        edit_account = (EditText) findViewById(R.id.edit_account);
        edit_password = (EditText) findViewById(R.id.edit_password);
//        edit_account.setOnFocusChangeListener(new EditListener());
//        edit_password.setOnFocusChangeListener(new EditListener());

    }

    private void initReceiver() {
        IntentFilter f = new IntentFilter();
        f.addAction(Constant.INTENT_SEND_EMAIL_SUCCESS);
        registerReceiver(receiver, f);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.INTENT_SEND_EMAIL_SUCCESS)) {
                LoginActivity.this.finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                LoginActivity.this.finish();
                break;
            case R.id.txt_login:
                presenter.login(edit_account.getText().toString(), edit_password.getText().toString());
                break;
            case R.id.txt_forget:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
        }
    }


    @Override
    public void showToast(String msg) {
        DialogUtils.hideDialog(dialog);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        sendBroadcast(new Intent(Constant.INTENT_FINISH_WELCOME_ACTIVITY));
        Intent intent = new Intent(this, FacebookActivity.class);
        startActivity(intent);
        this.finish();
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
}
