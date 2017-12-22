package com.lgb.accelerate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.bean.Friend;
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.UnitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by linguobiao on 16/8/20.
 */
public class FriendsListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Friend> lists;

    public FriendsListViewAdapter(Context context, List<Friend> lists) {
        if (lists != null) {
            this.lists = lists;
        } else {
            this.lists = new ArrayList<>();
        }
        this.context = context;
    }

    public void notifymDataSetChanged(List<Friend> lists){
        if(lists!=null){
            this.lists = lists;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.view_item_lv_friends, null);
            holder.txt_value = (TextView) convertView.findViewById(R.id.txt_value);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Friend friend = lists.get(i);
        if (friend != null) {
            holder.txt_value.setText(String.valueOf(friend.getSteps()));

            holder.txt_name.setText(friend.getName());

        }
        return convertView;
    }

    public class Holder {
        public TextView txt_value;
        public TextView txt_name;
    }
}
