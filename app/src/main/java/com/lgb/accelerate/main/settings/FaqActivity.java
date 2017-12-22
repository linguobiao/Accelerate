package com.lgb.accelerate.main.settings;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.lgb.accelerate.R;

/**
 * Created by linguobiao on 16/8/16.
 */
public class FaqActivity extends Activity {

    private WebView wv_html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        initUI();
    }

    private void initUI() {
        wv_html = (WebView) findViewById(R.id.wv_html);
        //允许JavaScript执行
        wv_html.getSettings().setJavaScriptEnabled(true);
        //找到Html文件，也可以用网络上的文件
        wv_html.loadUrl("file:///android_asset/fqa.html");
    }
}
