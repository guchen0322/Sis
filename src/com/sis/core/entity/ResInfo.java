package com.sis.core.entity;

import java.util.ArrayList;

public class ResInfo {
	private String Value;
	private String Difference;
	private String Percentage;
	private ArrayList<SYGP> sygps;
	private ArrayList<SYGP> dbsygps;

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getDifference() {
		return Difference;
	}

	public void setDifference(String difference) {
		Difference = difference;
	}

	public String getPercentage() {
		return Percentage;
	}

	public void setPercentage(String percentage) {
		Percentage = percentage;
	}

	public ArrayList<SYGP> getSygps() {
		return sygps;
	}

	public void setSygps(ArrayList<SYGP> sygps) {
		this.sygps = sygps;
	}

	public ArrayList<SYGP> getDbsygps() {
		return dbsygps;
	}

	public void setDbsygps(ArrayList<SYGP> dbsygps) {
		this.dbsygps = dbsygps;
	}

}
