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
				sygp.setVALUE(daJO.getString("VALUE"));
				sygps.add(sygp);
			}
			resInfo.setSygps(sygps);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return resInfo;
	}

}