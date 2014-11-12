package com.sis.core.ui;

import com.sis.core.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MultipleTopBar extends RelativeLayout {
	private Button btn_left;
	private Button btn_right;
	private TextView tv_title;

	private TopBarClickListener topBarClickListener;
	private String str_title;

	private RelativeLayout.LayoutParams leftButtonLayoutParams;
	private RelativeLayout.LayoutParams rightButtonLayoutParams;
	private RelativeLayout.LayoutParams tvTitleLayoutParams;

	private static int leftBtnId = 1;
	private static int titleTvId = 2;
	private static int rightBtnId = 3;

	private Drawable leftBtnBackground;
	private Drawable rightBtnBackground;

	private String str_LeftBtn;
	private String str_RightBtn;
	private int leftBtnColor;
	private int rightBtnColor;
	private int titleTvColor;

	private float titleTextSize;

	public MultipleTopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// 从参数列表中获取参数
		// TypedArray实例是个属性的容器，context.obtainStyledAttributes()方法返回得到。AttributeSet是节点的属性集合
		// 第二个参数为 为获取到值时的默认值
		TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.TopBar);
		this.str_title = ta.getString(R.styleable.TopBar_title);
		this.leftBtnBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
		this.rightBtnBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
		this.str_LeftBtn = ta.getString(R.styleable.TopBar_leftText);
		this.str_RightBtn = ta.getString(R.styleable.TopBar_rightText);
		this.leftBtnColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
		this.rightBtnColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
		this.titleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize,14);
		this.titleTvColor = ta.getColor(R.styleable.TopBar_titleTextColor, 0);

		ta.recycle();

		btn_left = new Button(context);
		btn_right = new Button(context);
		tv_title = new TextView(context);

		btn_left.setId(leftBtnId);
		btn_right.setId(rightBtnId);
		tv_title.setId(titleTvId);

		// 为组件配置布局参数
		leftButtonLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rightButtonLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		tvTitleLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		leftButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
				RelativeLayout.TRUE);
		leftButtonLayoutParams.setMargins(12, 0, 0, 0);// 左上右下
		leftButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);

		rightButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		rightButtonLayoutParams.setMargins(0, 0, 12, 0);// 左上右下
		rightButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);

		tvTitleLayoutParams.setMargins(12, 0, 12, 0);// 左上右下
		tvTitleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		tvTitleLayoutParams.addRule(RelativeLayout.LEFT_OF, rightBtnId);
		tvTitleLayoutParams.addRule(RelativeLayout.RIGHT_OF, leftBtnId);
		// tvTitleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,
		// RelativeLayout.TRUE);
		tv_title.setGravity(Gravity.CENTER);
		tv_title.setBackgroundColor(leftBtnColor);

		addView(btn_left, leftButtonLayoutParams);
		addView(btn_right, rightButtonLayoutParams);
		addView(tv_title, tvTitleLayoutParams);

		// btn_left.setBackgroundDrawable(leftBtnBackground);
		btn_left.setText(str_LeftBtn);
		btn_left.setTextColor(leftBtnColor);
		// btn_right.setBackgroundDrawable(rightBtnBackground);
		btn_right.setText(str_RightBtn);
		btn_right.setTextColor(rightBtnColor);

		tv_title.setTextSize(22.0f);
		tv_title.setTextColor(Color.BLUE);
		tv_title.setEllipsize(TruncateAt.MIDDLE);
		tv_title.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		tv_title.setSingleLine(true);
		tv_title.setText(str_title);
		tv_title.setTextSize(titleTextSize);
		tv_title.setTextColor(titleTvColor);

		btn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (topBarClickListener != null) {
					topBarClickListener.leftBtnClick();
				}
			}
		});

		btn_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (topBarClickListener != null) {
					topBarClickListener.rightBtnClick();
				}
			}
		});

	}

	/*
	 * 单击监听事件
	 */
	public void setTopBarClickListener(TopBarClickListener topBarClickListener) {
		this.topBarClickListener = topBarClickListener;
	}
}
