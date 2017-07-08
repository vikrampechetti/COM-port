/**
 * @author Oohithvikramrao 16-Jun-2017
 */
package com.COM.ioc.DataBase.DB2Access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.COM.Common.Utilities;
import com.COM.port.config.COMConfig;

public class FetchDataFromDataBase {
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	static Logger logger = LoggerFactory.getLogger(FetchDataFromDataBase.class);

	public static void main(String[] args) throws SQLException {
//		fetchDataFromDatabase(config.CamerasDB2Table(), config.CamerasRequiredFields());//
//		fetchDataFromDatabase(config.AmbulancesDB2Table(), config.AmbulancesRequiredFields());//
//		fetchDataFromDatabase(config.EventsDB2Table(), config.EventsRequiredFields());
//		fetchDataFromDatabase(config.VehicleInfoDB2Table(), config.VehicleInfoRequiredFields());
//		fetchDataFromDatabase(config.FireEnginesDB2Table(), config.FireEnginesRequiredFields());
//		fetchDataFromDatabase(config.CrimeMappingDB2Table(), config.CrimeMappingRequiredFields());
//		fetchDataFromDatabase(config.JRMSDB2Table(), config.JRMSRequiredFields());
//		fetchDataFromDatabase(config.VehicleHotlistDB2Table(), config.VehicleHotListRequiredFields());
//		fetchDataFromDatabase(config.HospitalsDB2Table(), config.HospitalsRequiredFields());
	}

	public static JSONArray fetchDataFromDatabase(String DataSourceTableName, String requiredFields) {

		JSONArray SqlData = new JSONArray();
		ArrayList<String> keys = new ArrayList<String>();
		try {
			String selectQuery = "SELECT * FROM " + DataSourceTableName;
			PreparedStatement preparedStatement = Utilities.ExecuteSelectQuery(selectQuery);
			ResultSetMetaData resultSetMetaData = preparedStatement.getMetaData();
			ResultSet resultSet = preparedStatement.executeQuery();
			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
				keys.add(resultSetMetaData.getColumnLabel(i));
			}
			while (resultSet.next()) {
				ArrayList<String> values = new ArrayList<String>();
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
					values.add(resultSet.getString(i));
				}
				JSONObject jsonObject = new JSONObject(Utilities.createJson(keys, values));
				if (!(requiredFields.equals("") || requiredFields.equals(null))) {
					SqlData.put(filterJsonObject(jsonObject, requiredFields));
				}
			}
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		}
		System.out.println(SqlData+"\n"+SqlData.length());
		return SqlData;
	}

	private static JSONObject filterJsonObject(JSONObject jsonObject, String requiredFields) throws JSONException {
		JSONObject finalJsonString = new JSONObject();
		for (String key : requiredFields.split(",")) {
			finalJsonString.put(key, jsonObject.get(key));
		}
		return finalJsonString;
	}

}
