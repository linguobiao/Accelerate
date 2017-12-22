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
import com.lgb.accelerate.utils.FormatHelper;
import com.lgb.accelerate.utils.UnitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linguobiao on 16/8/20.
 */
public class DataListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Data> lists;
    private String[] weeks;

    public DataListViewAdapter(Context context, List<Data> lists) {
        if (lists != null) {
            this.lists = lists;
        } else {
            this.lists = new ArrayList<Data>();
        }
        this.context = context;
        weeks = context.getResources().getStringArray(R.array.week2);
    }

    public void notifymDataSetChanged(List<Data> lists){
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
            convertView = inflater.inflate(R.layout.view_item_lv_data, null);
            holder.txt_value = (TextView) convertView.findViewById(R.id.txt_value);
            holder.txt_week = (TextView) convertView.findViewById(R.id.txt_week);
            holder.txt_label = (TextView) convertView.findViewById(R.id.txt_label);
            holder.ly_item = (RelativeLayout) convertView.findViewById(R.id.ly_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Data Data = lists.get(i);
        if (Data != null) {
            if (Global.type_data == Constant.TYPE_STEPS) {
                holder.txt_value.setText(String.valueOf((int)Data.getValue()));

            } else {
                holder.txt_value.setText(FormatHelper.df_0_00().format(Data.getValue()));

            }
            holder.txt_week.setText(weeks[i]);
            if (Global.type_data == Constant.TYPE_STEPS) {
                holder.txt_label.setText(context.getString(R.string.public_steps));
            } else if (Global.type_data == Constant.TYPE_DISTANCE) {
                UnitUtils.initUnit(context, holder.txt_label, Constant.KEY_UNIT_DISTANCE);
            } else if (Global.type_data == Constant.TYPE_CALORIES) {
                holder.txt_label.setText(context.getString(R.string.public_calories_burned));
            }
            if (i == Data.getWeek() - 1) {
                holder.ly_item.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            } else {
                holder.ly_item.setBackgroundColor(context.getResources().getColor(R.color.click));

            }
        }
        return convertView;
    }

    public class Holder {
        public TextView txt_value;
        public TextView txt_week;
        public TextView txt_label;
        public RelativeLayout ly_item;
    }
}
