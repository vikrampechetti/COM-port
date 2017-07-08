package com.COM.Common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.COM.port.config.COMConfig;
import com.google.gson.JsonObject;

/**
 * @author OohithVikramRao 23-May-2017
 *
 */
public class Utilities {
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	static Logger logger = LoggerFactory.getLogger(Utilities.class);
 
	/**
	 * To create Json object for given keys[] and values[]
	 */
	public static String createJson(String[] jsonKey, String[] jsonValues) {
		JsonObject obj = new JsonObject();
		try {
			for (int i = 0; i < jsonValues.length; i++) {
				for (int j = 0; j < jsonKey.length; j++) {
					obj.addProperty(jsonKey[i].trim(), jsonValues[i]);
				}
			}
		} catch (Exception e) {
			logger.error("Exception @ CreateJson }   " + e);
		}
		return obj.toString();
	}
	/**
	 *To create Json object for given keys Arraylist and values Arraylist
	 */
	public static String createJson(ArrayList<String> keys, ArrayList<String> values) {
		JsonObject obj = new JsonObject();
		try {
			for (int i = 0; i < values.size(); i++) {
				for (int j = 0; j < keys.size(); j++) {
					obj.addProperty(keys.get(i), values.get(i));
				}
			}
		} catch (Exception e) {
			logger.error("Exception @ CreateJson }   " + e);
		}
		return obj.toString();
	}
	/**
	 * To get current DATETIME in yyyy-MM-dd HH:mm:ss format
	 */
	public static String currentSysDateTimeForDB() {
		String curDateTime = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			curDateTime = simpleDateFormat.format(curDate);
		} catch (Exception e) {
			logger.error("Exception @ currentsysDateTime }	" + e);
		}
		return curDateTime;
	}


	/**
	 * To Remove unwanted Keys in a JSONobject
	 */
	public static JSONObject filterJsonObject(JSONObject jsonObject, String requiredFields) {
		JSONObject finalJsonString = new JSONObject();
		for (String key : requiredFields.split(",")) {
			try {
				finalJsonString.put(key, jsonObject.get(key));
			} catch (JSONException e) {
				logger.error("Exception @ filterJsonObject }" + e);
			}
		}
		return finalJsonString;
	}

	/**
	 * To Write Text
	 */
	public static void WriteProcessedIDTotemp(String ID, String DataSourceName) {

		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			String IDofLastProcessed = new String(Files.readAllBytes(Paths.get(config.tempfilesLocations() + DataSourceName + ".txt")));
			try {
				if ((Integer.parseInt(IDofLastProcessed) < Integer.parseInt(ID))) {
					fw = new FileWriter(config.tempfilesLocations() + DataSourceName + ".txt");
					bw = new BufferedWriter(fw);
					bw.write(ID.toString());
				}
			} catch (Exception e) {
				fw = new FileWriter(config.tempfilesLocations() + DataSourceName + ".txt");
				bw = new BufferedWriter(fw);
				bw.write("0");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
