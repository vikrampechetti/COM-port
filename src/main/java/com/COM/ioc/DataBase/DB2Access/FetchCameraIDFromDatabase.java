package com.COM.ioc.DataBase.DB2Access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.aeonbits.owner.ConfigFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.COM.ioc.DataSourceActions.CreateDataSource.CreateDataSourceRecord;
import com.COM.ioc.DataSourceActions.UpdateDataSource.UpdateDataSourceRecord;
import com.COM.port.config.COMConfig;

/**
 * @author OohithVikramRao 19-May-2017
 *
 */
public class FetchCameraIDFromDatabase {
	static Logger logger = LoggerFactory.getLogger(FetchCameraIDFromDatabase.class);
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	static Connection con;

	/**
	 * Create/Update the DataSource Based on CameraID 
	 */
	public static void fetchCameraDataFromDB2(String dataSourcePayload, ResultSet resultset) {
		try {
			Boolean flag = true;
			JSONObject payloadjs = new JSONObject(dataSourcePayload);
			String cameraIDinCSV = payloadjs.getString("camera_ID").toString();
			while (resultset.next()) {
				String CameraIDinDB = resultset.getString("camera_ID");
				String ObjectID = resultset.getString("OBJECTID");
				
				if (cameraIDinCSV.equalsIgnoreCase(CameraIDinDB)) {
					new UpdateDataSourceRecord();
					logger.info(UpdateDataSourceRecord.updateDataSource(ObjectID, dataSourcePayload));
					flag = false;
				}
			}
			if (flag) {
				logger.info(new CreateDataSourceRecord().createDataSourceRecord(config.CCTVDataSourceID(),dataSourcePayload));
			}
		} catch (SQLException ex) {
			logger.error("Exception @ fetchCameraDataFromDB2 } SQLException information");
			while (ex != null) {
				logger.error("Error msg: " + ex.getMessage());
				logger.error("SQLSTATE: " + ex.getSQLState());
				logger.error("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}

		} catch (JSONException e) {

			logger.info("Exception @ fetchCameraDataFromDB2 }" + e);
		}

	}
}