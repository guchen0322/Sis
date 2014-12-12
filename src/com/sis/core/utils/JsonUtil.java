package com.sis.core.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sis.core.entity.MonthObj;
import com.sis.core.entity.MonthParam;
import com.sis.core.entity.ResInfo;
import com.sis.core.entity.SYGP;

public class JsonUtil {

	public static ResInfo getResInfo(String rawJsonData) {
		ResInfo resInfo;
		try {
			JSONObject resJO = new JSONObject(rawJsonData);
			String errorCode = resJO.getString("errorCode");
			String errorMSG = resJO.getString("errorMSG");

			resInfo = new ResInfo();
			resInfo.setErrorCode(errorCode);
			resInfo.setErrorMSG(errorMSG);

			ArrayList<SYGP> sygps = new ArrayList<SYGP>();
			JSONArray dateArray = resJO.getJSONArray("dateArray");
			for (int i = 0; i < dateArray.length(); i++) {
				JSONObject daJO = dateArray.getJSONObject(i);
				SYGP sygp = new SYGP();
				sygp.setPARAID(daJO.getString("PARAID"));
				sygp.setYVALUE(daJO.getString("VALUE"));
				sygps.add(sygp);
			}
			resInfo.setSygps(sygps);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return resInfo;
	}

	/**
	 * <pre>
	 * SC0001 #1机组负荷 
	 * SC0002 #1机组发电煤耗 
	 * SC0003 #1机组供电煤耗 
	 * SC0004 #1机组发电量表码 
	 * SC0005 #1机组发电量
	 * </pre>
	 * 
	 * @param rawJsonData
	 * @return
	 */
	public static ResInfo getResInfo(String rawJsonData, int machineType) {
		ResInfo resInfo = new ResInfo();
		try {
			ArrayList<SYGP> sygps = new ArrayList<SYGP>();
			JSONArray dateArray = new JSONArray(rawJsonData);
			for (int i = 0; i < dateArray.length(); i++) {
				JSONObject daJO = dateArray.getJSONObject(i);
				SYGP sygp = new SYGP();
				switch (machineType) {
				case 1:
					if (daJO.getString("PARAID").endsWith("SC0001")) {
						sygp.setPARAID(daJO.getString("PARAID"));
						sygp.setYVALUE(daJO.getString("VALUE"));
						sygp.setXVALUE(daJO.getString("STATTIMEBEGIN"));
						sygps.add(sygp);
					}
					break;
				case 2:
					if (daJO.getString("PARAID").endsWith("SC0002")) {
						sygp.setPARAID(daJO.getString("PARAID"));
						sygp.setYVALUE(daJO.getString("VALUE"));
						sygp.setXVALUE(daJO.getString("STATTIMEBEGIN"));
						sygps.add(sygp);
					}
					break;
				case 3:
					if (daJO.getString("PARAID").endsWith("SC0003")) {
						sygp.setPARAID(daJO.getString("PARAID"));
						sygp.setYVALUE(daJO.getString("VALUE"));
						sygp.setXVALUE(daJO.getString("STATTIMEBEGIN"));
						sygps.add(sygp);
					}
					break;
				case 4:
					if (daJO.getString("PARAID").endsWith("SC0004")) {
						sygp.setPARAID(daJO.getString("PARAID"));
						sygp.setYVALUE(daJO.getString("VALUE"));
						sygp.setXVALUE(daJO.getString("STATTIMEBEGIN"));
						sygps.add(sygp);
					}
					break;
				case 5:
					if (daJO.getString("PARAID").endsWith("SC0005")) {
						sygp.setPARAID(daJO.getString("PARAID"));
						sygp.setYVALUE(daJO.getString("VALUE"));
						sygp.setXVALUE(daJO.getString("STATTIMEBEGIN"));
						sygps.add(sygp);
					}
					break;
				default:
					break;
				}
			}
			resInfo.setSygps(sygps);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return resInfo;
	}

	public static MonthObj getMonthResInfo(String rawJsonData) {
		MonthObj monthObj;

		try {
			JSONObject monthJO = new JSONObject(rawJsonData);
			String difference = monthJO.getString("Difference");
			String percentage = monthJO.getString("Percentage");
			JSONArray monthJA = monthJO.getJSONArray("MonthDataExpends");

			ArrayList<MonthParam> datas = new ArrayList<MonthParam>();
			for (int i = 0; i < monthJA.length(); i++) {
				MonthParam monthParam = new MonthParam();
				JSONObject currJO = monthJA.getJSONObject(i);
				monthParam.setValue(currJO.getString("Value"));
				monthParam.setDate(currJO.getString("Date"));
				datas.add(monthParam);
			}

			monthObj = new MonthObj();
			monthObj.setDifference(difference);
			monthObj.setPercentage(percentage);
			monthObj.setDatas(datas);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return monthObj;
	}

}