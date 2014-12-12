package com.sis.core.entity;

import java.util.ArrayList;

public class MonthObj {
	private String difference;
	private String percentage;
	private ArrayList<MonthParam> datas;

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public ArrayList<MonthParam> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<MonthParam> datas) {
		this.datas = datas;
	}

}
