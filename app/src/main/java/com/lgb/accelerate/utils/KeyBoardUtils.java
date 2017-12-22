package com.lgb.accelerate.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyBoardUtils {
	public static void hideKeyboard(Activity activity) {
		if (activity != null) {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null) {
				View focus = activity.getCurrentFocus();
				if (focus != null) {
					IBinder ib = focus.getWindowToken();
					if (ib != null) {
						imm.hideSoftInputFromWindow(ib, 0);
					}
				}
			}
		}
		
	}
	
	public static void hideKeyboard3(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			if (view != null) {
				IBinder ib = view.getWindowToken();
				if (ib != null) {
					imm.hideSoftInputFromWindow(ib, 0); 
				}
			}
		}	
	}

	public static void setEditTextCount(final EditText editText, final int min, final int max) {
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.i("test", "s = " + s + "   start = " + start + "   before = " + before + "   count = " + count);
				if (start >= 0) {
					if (!s.toString().equalsIgnoreCase("")) {
						int num = Integer.parseInt(s.toString());
						if (num > max) {
							editText.setText(String.valueOf(max));
						} else if (num < min) {
							editText.setText(String.valueOf(min));
						}
					}
					return;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				editText.setSelection(editText.length());
			}

			@Override
			public void afterTextChanged(Editable s) {
				editText.setSelection(editText.length());
			}
		});
	}
}
