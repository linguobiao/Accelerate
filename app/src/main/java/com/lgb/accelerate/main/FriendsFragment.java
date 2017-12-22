package com.lgb.accelerate.main;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.R;
import com.lgb.accelerate.adapter.DataListViewAdapter;
import com.lgb.accelerate.adapter.FriendsListViewAdapter;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.api.presenter.BasePresenterApi;
import com.lgb.accelerate.api.view.BaseViewApi;
import com.lgb.accelerate.bean.Friend;
import com.lgb.accelerate.bean.ResultFail;
import com.lgb.accelerate.imp.presenter.FriendsPresenter;
import com.lgb.accelerate.splash.LoginActivity;
import com.lgb.accelerate.utils.DialogUtils;
import com.lgb.accelerate.utils.SpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/15.
 */
public class FriendsFragment extends Fragment implements BaseViewApi {

    private BasePresenterApi presenter;
    private ProgressDialog dialog;

    private ListView lv_friends;

    private List<Friend> list_friends = new ArrayList<>();

    private FriendsListViewAdapter listViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        initUI(view);
        presenter = new FriendsPresenter(getActivity(), this);
        return view;
    }

    private void initUI(View view) {
        lv_friends = (ListView) view.findViewById(R.id.lv_friends);

        listViewAdapter = new FriendsListViewAdapter(getActivity(), list_friends);
        lv_friends.setAdapter(listViewAdapter);

    }

    private void startPost() {
        presenter.post(null);
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void postSuccess(Object object) {

        DialogUtils.hideDialog(dialog);

        list_friends.clear();

        list_friends.addAll((List<Friend>) object);
        Collections.sort(list_friends);
        listViewAdapter.notifymDataSetChanged(list_friends);


    }

    @Override
    public void postFail(ResultFail fail) {
        if (fail != null) {
            if (fail.getCode() == Constant.HTTP_CODE_2){
                SpUtils.getInstance().clear();  // 清除所有数据
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        }
    }

    @Override
    public void showDialog() {
        if (dialog == null) {
            dialog = DialogUtils.showProgressDialog(getActivity(), getString(R.string.public_loading));
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
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            startPost();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        DialogUtils.dismissDialog(dialog);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            startPost();
        }
    }
}
