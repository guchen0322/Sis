package com.sis.core.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sis.core.entity.ResInfo;
import com.sis.core.entity.SYGP;

public class JsonUtil {

	public static ResInfo getResInfo(String rawJsonData) {
		ResInfo resInfo;
		try {
			JSONObject resJO = new JSONObject(rawJsonData);
			String value = resJO.getString("Value");
			String date = resJO.getString("Date");
			String difference = resJO.getString("Difference");
			String percentage = resJO.getString("Percentage");
			JSONArray MonthDataExpends = resJO.getJSONArray("MonthDataExpends");
			JSONArray NextMonthDataExpends = resJO.getJSONArray("NextMonthDataExpends");

			ArrayList<SYGP> sygps = new ArrayList<SYGP>();
			for (int i = 0; i < MonthDataExpends.length(); i++) {
				SYGP sygp = new SYGP();
				JSONObject currJO = MonthDataExpends.getJSONObject(i);
				sygp.setValue(currJO.getString("Value"));
				sygp.setDate(currJO.getString("Date"));
				sygp.setPARAID(currJO.getString("PARAID"));
				sygps.add(sygp);
			}

			ArrayList<SYGP> dbsygps = new ArrayList<SYGP>();
			if (NextMonthDataExpends.length() > 0) {
				for (int i = 0; i < NextMonthDataExpends.length(); i++) {
					SYGP dbsygp = new SYGP();
					JSONObject currJO = NextMonthDataExpends.getJSONObject(i);
					dbsygp.setValue(currJO.getString("Value"));
					dbsygp.setDate(currJO.getString("Date"));
					dbsygp.setPARAID(currJO.getString("PARAID"));
					dbsygps.add(dbsygp);
				}
			}

			resInfo = new ResInfo();
			resInfo.setValue(value);
			resInfo.setDate(date);
			resInfo.setDifference(difference);
			resInfo.setPercentage(percentage);
			resInfo.setSygps(sygps);
			resInfo.setDbsygps(dbsygps);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return resInfo;
	}

}