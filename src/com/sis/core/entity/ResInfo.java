package com.sis.core.entity;

import java.util.ArrayList;

public class ResInfo {
	private ArrayList<SYGP> sygps;
	private String errorCode;
	private String errorMSG;

	public ArrayList<SYGP> getSygps() {
		return sygps;
	}

	public void setSygps(ArrayList<SYGP> sygps) {
		this.sygps = sygps;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMSG() {
		return errorMSG;
	}

	public void setErrorMSG(String errorMSG) {
		this.errorMSG = errorMSG;
	}

}
