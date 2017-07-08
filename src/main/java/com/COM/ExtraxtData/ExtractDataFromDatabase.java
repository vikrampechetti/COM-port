package com.COM.ExtraxtData;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.COM.Common.DataBaseAccess;
import com.COM.Common.Utilities;
import com.COM.ioc.DataSourceActions.CreateDataSource.CreateDataSourceRecord;
import com.COM.port.config.COMConfig;




/**
 * @Author OohithVikramRao 08-Jul-2017
 *
 */
public class ExtractDataFromDatabase {
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	/**
	 *
	 */
	public static void main(String[] args) {
		JSONArray jsonArray = DataBaseAccess.GetDataBaseData(DataBaseAccess.GetDB2Connection(),config.CCTVDB2Table(),config.CCTVDataSourceName());
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject EventObject = jsonArray.getJSONObject(i);
				String Payload = EventObject.toString();
				CreateDataSourceRecord createDataSourceRecord = new CreateDataSourceRecord();
//				String response = createDataSourceRecord.createDataSourceRecord(config.CCTVDataSourceID(), Payload);
//				System.out.println("Data Source : "+config.CCTVDataSourceName()+"\n"+Payload + "\n" + response);
				Utilities.WriteProcessedIDTotemp(jsonArray.getJSONObject(i).get("OBJECTID").toString(),config.CCTVDataSourceName());
				String response1 = createDataSourceRecord.createDataSourceRecord(config.CCTVCopyDataSourceID(), Payload);
				System.out.println("Data Source : "+config.CCTVCopyDataSourceName()+"\n"+Payload + "\n" + response1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
