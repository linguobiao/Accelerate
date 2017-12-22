package com.lgb.accelerate.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.lgb.accelerate.Global.Constant;
import com.lgb.accelerate.Global.Global;
import com.lgb.accelerate.R;
import com.lgb.accelerate.api.other.Data;
import com.lgb.accelerate.bean.HistoryDay;
import com.lgb.accelerate.utils.CalendarHelper;
import com.lgb.accelerate.utils.FormatHelper;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ChartBarView extends DemoView {

	private String TAG = "StackBarChart01View";

	private Context context;

	private BarChart chart = new BarChart();

	List<String> chartLabels = new LinkedList<>();
	List<BarData> BarDataSet = new LinkedList<>();

	List<Data> list = new ArrayList<>();
	public List<Data> getList() {
		return list;
	}
	public void setList(List<Data> list) {
		this.list = list;
	}

	Calendar cal_current;
	public void setCurrentDate(Calendar cal_current) {
		this.cal_current = cal_current;
	}

	private List<CustomLineData> mCustomLineDataset = new LinkedList<>();

	public ChartBarView(Context context) {
		super(context);
		this.context = context;
		 initView();
	}

	public ChartBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		 initView();
	}

	public ChartBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		 initView();
	}

	public void initView() {
		chartLabels.clear();
		BarDataSet.clear();
		chartLabels();
		chartDataSet(list);
		chartRender();
		postInvalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 图所占范围大小
		chart.setChartRange(w, h);
	}

	private void chartRender() {
		try {

			// 设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
			int[] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			// 禁止平移
			chart.disablePanMode();

			// 显示边框
			// chart.showRoundBorder();

			chart.setChartDirection(XEnum.Direction.VERTICAL);
			// 数据源
			chart.setCategories(chartLabels);
			chart.setDataSource(BarDataSet);
			chart.setCustomLines(mCustomLineDataset);

			// Y轴
			chart.getDataAxis().setAxisMin(0);
//			chart.getDataAxis().setAxisSteps(5);
			chart.getDataAxis().getTickLabelPaint().setTextSize(DensityUtil.dip2px(getContext(), 7));
			chart.getDataAxis().setHorizontalTickAlign(Align.RIGHT);
			chart.setDataAxisLocation(XEnum.AxisLocation.RIGHT);
			chart.getDataAxis().hideAxisLine();
			chart.getDataAxis().hideTickMarks();
			chart.getDataAxis().setTickLabelMargin(DensityUtil.dip2px(getContext(), 20));
			chart.getDataAxis().getTickLabelPaint().setColor(Color.WHITE);

			// X轴
			chart.setCategoryAxisLocation(XEnum.AxisLocation.TOP);
			chart.getCategoryAxis().getAxisPaint().setColor(Color.WHITE);
			chart.getCategoryAxis().hideTickMarks();
			chart.getCategoryAxis().getTickMarksPaint().setAlpha(0);
			chart.getCategoryAxis().setTickLabelMargin(DensityUtil.dip2px(getContext(), -10));
			chart.getCategoryAxis().getTickLabelPaint().setTextSize(DensityUtil.dip2px(getContext(), 8));
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.WHITE);

			// 定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

				@Override
				public String textFormatter(String value) {
					DecimalFormat df = new DecimalFormat("#0");
					Double tmp = Double.parseDouble(value);
					String label = df.format(tmp).toString();
					return label;
				}

			});

			// 定义标签轴标签显示格式
			chart.getCategoryAxis().setLabelFormatter(new IFormatterTextCallBack() {

				@Override
				public String textFormatter(String value) {
					String label = value;
					return label;
				}

			});

			// 定义柱形上标签显示颜色
			chart.getBar().getItemLabelPaint().setColor(Color.rgb(77, 184, 73));
			chart.getBar().getItemLabelPaint().setTypeface(Typeface.DEFAULT_BOLD);

			chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	private void chartDataSet(List<Data> list) {

		List<Double> dataSeries = new LinkedList<Double>();

		Calendar cal;
		int len;

		if (Global.type_data_time == Constant.TYPE_MONTH) {
			cal = CalendarHelper.getMonthBegin(cal_current);
			Calendar cal_end = CalendarHelper.getMonthEnd(cal_current);
			len = cal_end.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH);
		} else {
			cal = CalendarHelper.getDateOfSunday();
			len = 7;

		}

		double steps = 0;
		double max = 0;
		for (int i = 0; i < len; i ++) {
			String date = FormatHelper.sdf_yyyy_MM_dd_HH_mm_ss.format(cal.getTime());
			for (Data data : list) {
				if (date.equalsIgnoreCase(data.getDate())) {
					steps = data.getValue();
					if (steps > max) {
						max = steps;
					}
				}

			}

			dataSeries.add(steps);
			steps = 0;
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);

		}

		max = max + 0.5;
		int _max = Integer.parseInt(FormatHelper.df_0().format(max));
		if (_max % 2 != 0) {
			_max = _max + 1;
		}

		chartDesireLines((_max) / 2d);
		chart.getDataAxis().setAxisMax(_max);
		BarDataSet.add(new BarData("", dataSeries, getResources().getColor(R.color.yellow)));
		chart.getDataAxis().setAxisSteps((_max) / 2d);
	}

	private void chartLabels() {

		if (Global.type_data_time == Constant.TYPE_MONTH) {
			Calendar cal_begin = CalendarHelper.getMonthBegin(cal_current);
			Calendar cal_end = CalendarHelper.getMonthEnd(cal_current);
			int len = cal_end.get(Calendar.DAY_OF_MONTH) - cal_begin.get(Calendar.DAY_OF_MONTH);
			for (int i = 0; i < len; i++) {
				if (i % 3 == 0) {
					chartLabels.add(i + "");
				} else {
					chartLabels.add("");
				}
			}

		} else {
			String[] weeks = context.getResources().getStringArray(R.array.week1);
			int len = weeks.length;
			for (int i = 0; i < len; i++) {
				chartLabels.add(weeks[i]);
			}

		}

	}

	/**
	 * 期望线/分界线
	 */
	private void chartDesireLines(double middle)
	{
		CustomLineData s = new CustomLineData("",0d,Color.WHITE,DensityUtil.dip2px(getContext(), 2));
		mCustomLineDataset.add(s);
		mCustomLineDataset.add(new CustomLineData("",middle,Color.WHITE,DensityUtil.dip2px(getContext(), 2)));

	}

	@Override
	public void render(Canvas canvas) {
		try {

			// chart.setChartRange(this.getMeasuredWidth(), this.getMeasuredHeight());
			chart.render(canvas);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

}
