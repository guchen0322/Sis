package com.sis.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
	
	public static String getStartTime(int minutes) {
		long inteval = System.currentTimeMillis();
		long tempIntTime = inteval / (minutes * 60 * 1000);
		long endTime = tempIntTime * minutes * 60 * 1000;
		long startTime = endTime - 6 * minutes * 60 * 1000;// 6表示的是取几个时间段的值
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startTime);//"dd-MM月-yy hh.mm.00.000000 a"
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA).format(calendar.getTime()));

	}

	public static String getEndTime(int minutes) {
		long inteval = System.currentTimeMillis();
		long tempIntTime = inteval / (minutes * 60 * 1000);// 当前时间除以5分钟取整
		long endTime = tempIntTime * minutes * 60 * 1000;// 取整后乘以5分钟加一秒组成结束时间的时间戳
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(endTime);
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA).format(calendar.getTime()));

	}
	
	public static String formatTime(String time){
		String strDate = null;
		try {
			Date date = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(time);
			strDate = new SimpleDateFormat("HH:mm").format(date); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}
}
