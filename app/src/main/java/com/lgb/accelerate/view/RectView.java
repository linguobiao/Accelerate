package com.lgb.accelerate.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 图表方框的绘制
 * @author Lingb
 *
 */
public class RectView extends View {

	private final String TAG = "RectView";
	private int[] ltrb = new int[4];
	private float rect_width;	// 条目宽度
	private int width = this.getWidth();	// 绘图区宽度
	private int height = this.getHeight();	// 绘图区高度
	private float x = 0;	// 从x轴的哪里开始绘制
	private float interval = 0;		// 间隔
	private int size = 1;	// 条目总数
	private int size_value = 0;		// 需要显示的条目总数
	private  float x_click;		// 点中条目的x坐标
	private boolean isClick = false;	// 是否点中

	public RectView(Context context, AttributeSet attrs) {
		super(context, attrs);

//		ltrb[0] = DensityUtil.dip2px(context, 25); //left
//		ltrb[1] = DensityUtil.dip2px(context, 20); //top
//		ltrb[2] = DensityUtil.dip2px(context, 10); //right
//		ltrb[3] = DensityUtil.dip2px(context, 100); //bottom
	}

	public void setDrawData(float x, float interval, int size, int size_value, int[] ltrb) {
		this.x = x;
		this.interval = interval;
		this.size = size + 1;
		this.isClick = false;
		if (size_value > size) {
			size_value = size;
		}
		this.size_value = size_value + 1;
		this.ltrb = ltrb;

		invalidate();
	}

	public int setItemClickBackground(boolean isClick, float x, float y) {
		int index = -1;		// 点击条目
		this.x_click = x;
		this.isClick = isClick;

		x_click = x_click -ltrb[0];
		this.x = interval + rect_width / 2;
		Log.i(TAG, this.x + ",   " + interval + ",   " + rect_width);

		for(int i = 0; i < size_value - 1; i ++) {

			if (x_click > (this.x + i * rect_width + (i-1) * interval + interval/2) && x_click <= (this.x + i * rect_width + i * interval/2 + rect_width)) {
				index = i;
			}

		}
		invalidate();
		return index;
	}
	

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		width = this.getWidth();
		height = this.getHeight();
		rect_width = (width - interval * (size)) / size;

		// 方框
		Paint paint_rect = new Paint();
		paint_rect.setAntiAlias(true);
		paint_rect.setStyle(Style.STROKE);
		paint_rect.setColor(Color.GRAY);

		x = interval;
		for (int i = 0; i < size_value - 1; i ++) {

			canvas.drawRect(x + i * rect_width + i * interval + rect_width / 2, 20, x + i * rect_width + i * interval + rect_width / 2 + rect_width, height - 20, paint_rect);
		}
		Log.i(TAG, this.x + ",   " + interval + ",   " + rect_width);
		if (isClick) {
			// 点击条目背景
			Paint paint_click = new Paint();
			paint_click.setAntiAlias(true);
			paint_click.setStyle(Style.FILL_AND_STROKE);
			paint_click.setColor(Color.parseColor("#33000000"));

			for(int i = 0; i < size_value - 1; i ++) {
				if (x_click > (x + i * rect_width + i * interval) && x_click < (x + i * rect_width + i * interval + rect_width)) {
					canvas.drawRect(x + i * rect_width + i * interval + rect_width / 2, 20, x + i * rect_width + i * interval + rect_width / 2 + rect_width, height - 20, paint_click);
				}
			}
		}

	}

}
