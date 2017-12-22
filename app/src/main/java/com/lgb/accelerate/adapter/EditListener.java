package com.lgb.accelerate.adapter;

import android.view.View;
import android.widget.EditText;

/**
 * Created by linguobiao on 16/8/25.
 */
public class EditListener implements View.OnFocusChangeListener {

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText _v=(EditText)v;
        if (!hasFocus) {// 失去焦点
            _v.setHint(_v.getTag().toString());
        } else {
            String hint=_v.getHint().toString();
            _v.setTag(hint);
            _v.setHint("");
        }
    }
}
