package com.lgb.accelerate.main;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.lgb.accelerate.R;
import com.lgb.accelerate.utils.FragmentUtils;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener{

    private RadioButton radio_dash, radio_tracks, radio_goals, radio_friends, radio_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentUtils.getInstance().setfMgr(getFragmentManager());

        initUI();

        radio_dash.setChecked(true);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.radio_dashboard:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(DashboardFragment.class, FragmentUtils.FRAGMENT_DASHBOARD);
                    radio_dash.setTextColor(getResources().getColor(R.color.click));
                } else {
                    radio_dash.setTextColor(getResources().getColor(R.color.color_ffffff));
                    FragmentUtils.getInstance().removeSonFragment(DashboardFragment.class, FragmentUtils.FRAGMENT_DASHBOARD);
                }
                break;
            case R.id.radio_tracks:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(TracksFragment.class, FragmentUtils.FRAGMENT_TRACKS);
                    radio_tracks.setTextColor(getResources().getColor(R.color.click));
                } else {
                    radio_tracks.setTextColor(getResources().getColor(R.color.color_ffffff));
                    FragmentUtils.getInstance().removeSonFragment(TracksFragment.class, FragmentUtils.FRAGMENT_TRACKS);
                }
                break;
            case R.id.radio_goals:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(GoalsFragment.class, FragmentUtils.FRAGMENT_GOALS);
                    radio_goals.setTextColor(getResources().getColor(R.color.click));
                } else {
                    radio_goals.setTextColor(getResources().getColor(R.color.color_ffffff));
                    FragmentUtils.getInstance().removeSonFragment(GoalsFragment.class, FragmentUtils.FRAGMENT_GOALS);
                }
                break;
            case R.id.radio_friends:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(FriendsFragment.class, FragmentUtils.FRAGMENT_FRIENDS);
                    radio_friends.setTextColor(getResources().getColor(R.color.click));
                } else {
                    radio_friends.setTextColor(getResources().getColor(R.color.color_ffffff));
                    FragmentUtils.getInstance().removeSonFragment(FriendsFragment.class, FragmentUtils.FRAGMENT_FRIENDS);
                }
                break;
            case R.id.radio_settings:
                if (isChecked) {
                    FragmentUtils.getInstance().showMainFragment(SettingsFragment.class, FragmentUtils.FRAGMENT_SETTINGS);
                    radio_settings.setTextColor(getResources().getColor(R.color.click));
                } else {
                    radio_settings.setTextColor(getResources().getColor(R.color.color_ffffff));
                    FragmentUtils.getInstance().removeSonFragment(SettingsFragment.class, FragmentUtils.FRAGMENT_SETTINGS);
                }
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(radio_dash.isChecked()) {
                if (!FragmentUtils.getInstance().isVisible(FragmentUtils.FRAGMENT_DASHBOARD)) {
                    FragmentUtils.getInstance().returnMainFragment(DashboardFragment.class, FragmentUtils.FRAGMENT_DASHBOARD);
                    return true;
                }
            } else if(radio_tracks.isChecked()) {
                if (!FragmentUtils.getInstance().isVisible(FragmentUtils.FRAGMENT_TRACKS)) {
                    FragmentUtils.getInstance().returnMainFragment(TracksFragment.class, FragmentUtils.FRAGMENT_TRACKS);
                    return true;
                }
            } else if(radio_goals.isChecked()) {
                if (!FragmentUtils.getInstance().isVisible(FragmentUtils.FRAGMENT_GOALS)) {
                    FragmentUtils.getInstance().returnMainFragment(GoalsFragment.class, FragmentUtils.FRAGMENT_GOALS);
                    return true;
                }
            } else if(radio_friends.isChecked()) {
                if (!FragmentUtils.getInstance().isVisible(FragmentUtils.FRAGMENT_FRIENDS)) {
                    FragmentUtils.getInstance().returnMainFragment(FriendsFragment.class, FragmentUtils.FRAGMENT_FRIENDS);
                    return true;
                }
            } else if(radio_settings.isChecked()) {
                if (!FragmentUtils.getInstance().isVisible(FragmentUtils.FRAGMENT_SETTINGS)) {
                    FragmentUtils.getInstance().returnMainFragment(SettingsFragment.class, FragmentUtils.FRAGMENT_SETTINGS);
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initUI() {
        radio_dash = (RadioButton) findViewById(R.id.radio_dashboard);
        radio_tracks = (RadioButton) findViewById(R.id.radio_tracks);
        radio_goals = (RadioButton) findViewById(R.id.radio_goals);
        radio_friends = (RadioButton) findViewById(R.id.radio_friends);
        radio_settings = (RadioButton) findViewById(R.id.radio_settings);
        radio_dash.setOnCheckedChangeListener(this);
        radio_tracks.setOnCheckedChangeListener(this);
        radio_goals.setOnCheckedChangeListener(this);
        radio_friends.setOnCheckedChangeListener(this);
        radio_settings.setOnCheckedChangeListener(this);

    }
}
