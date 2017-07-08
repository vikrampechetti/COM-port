package com.COM.Common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
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

import com.COM.port.config.COMConfig;

/**
 * @Author OohithVikramRao 23-Jun-2017
 *
 */
public class DataBaseAccess {
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	static Logger logger = LoggerFactory.getLogger(DataBaseAccess.class);

	/**
	 * To get the Data from Datatable
	 */
	public static JSONArray GetDataBaseData(Connection connection, String DataBasetableName, String requiredFields,String DataSourceName) {
		ArrayList<String> keys = new ArrayList<String>();
		JSONArray DBData = new JSONArray();
		try {
			String IDofLastProcessed = new String(
					Files.readAllBytes(Paths.get(config.tempfilesLocations() + DataSourceName + ".txt")));
			String selectQuery = "";
			if (!(IDofLastProcessed.equals(null) || IDofLastProcessed.equals(""))) {
				
				selectQuery = "SELECT * FROM " + DataBasetableName + " WHERE OBJECTID >'" + IDofLastProcessed + "'";
			} else {
				selectQuery = "SELECT * FROM " + DataBasetableName;
			}
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			ResultSetMetaData resultSetMetaData= preparedStatement.getMetaData();
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
					DBData.put(Utilities.filterJsonObject(jsonObject, requiredFields));
				} else {
					DBData.put(jsonObject);
				}
				
			}
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DBData;
	}

	/**
	 * To get the Data from Datatable
	 */
	public static JSONArray GetDataBaseData(Connection connection, String DataBasetableName, String DataSourceName) {
		return GetDataBaseData(connection, DataBasetableName, "", DataSourceName);
	}

	/**
	 * To get IBM DB2 DataBase Connection
	 */
	public static Connection GetDB2Connection() {
		Connection con = null;
		try {

			Class.forName("com.ibm.db2.jcc.DB2Driver");
			con = DriverManager.getConnection(config.DB2ConnectionURL(), config.DB2Username(), config.DB2Usersecret());
			con.setAutoCommit(false);

		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Exception @ GetDataBaseConnection }" + e.toString());

		}
		return con;
	}

	/**
	 * To get SQLServer DataBase Connection
	 */
	public static Connection GetSQLServerConnection() {
		Connection con = null;
		try {

			con = DriverManager.getConnection(config.SQLConnectionURL(), config.SQLUsername(), config.SQLUserSecret());

		} catch (SQLException e) {
			logger.error("Exception @ GetDataBaseConnection }" + e.toString());

		}
		return con;
	}

}
