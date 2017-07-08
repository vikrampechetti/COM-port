package com.COM.ioc.DataSourceActions.GetDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.COM.Common.Utilities;



public class GetDataSourceData {

	public static void main(String[] args) {
		run();

	}

	public static String run() {
		String Headers = "";
String payloads="";
		String SelectQuery = "SELECT * FROM IOC.TARGET_VIEW_HCS_CAMERA";
		new Utilities();
		PreparedStatement preparedStatement = Utilities.ExecuteSelectQuery(SelectQuery);

		try {
			ResultSetMetaData result = preparedStatement.getMetaData();
			int count = result.getColumnCount();
			for (int i = 1; i <= count; i++) {
				Headers += result.getColumnLabel(i) + ",";
			}
			Headers = Headers.substring(0, Headers.length() - 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String values = "";
				for (int i = 1; i <= count; i++) {
					values += resultSet.getString(i) + ",";
				}
				values=values.substring(0, values.length()-1);
				new Utilities();
				String payload=Utilities.createJson(Headers.split(","), values.split(","));
				JSONObject jsonObject=new JSONObject(payload);
				String ignoreableJsonObjects[]="STARTYEAR,STARTDATE,ENDTIME,ENDMONTH,ENDDAY,DELETEFLAG,STARTHOUR,DATASOURCEID,STARTDAYOFWEEK,ENDHOUR,STARTDAY,ENDDAYOFWEEK,ANNOTATIONID,ENDDATE,STARTTIME,STARTMONTH,ENDYEAR".split(",");
				for (String ignoringobjectString : ignoreableJsonObjects) {
					jsonObject.remove(ignoringobjectString);	
				}
				payloads+=jsonObject.toString();
				System.out.println(jsonObject.toString());
			}
			return payloads;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
return payloads;
	}

}
