package com.COM.ioc.DataSourceActions.UpdateDataSource;

import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.core.MediaType;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.COM.ioc.authentication.Authentication;
import com.COM.port.config.COMConfig;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author OohithVikramRao 19-May-2017
 *
 */
public class UpdateDataSourceRecord {
	
	static Logger logger = LoggerFactory.getLogger(UpdateDataSourceRecord.class);
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	
	static URI uri;
	static Client client = new Client();
	static WebResource webResource;
	static ClientResponse response;
	
	/**
	 * To Update a Datasource Record 
	 */
	public static String updateDataSource(String ObjectID, String payload) {
		logger.info("Updating DataSource...");
		try {
			uri = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/data-injection-service/datablocks/"+config.CCTVDataSourceID()+"/dataitems/"+ObjectID);
			webResource = client.resource(uri);
			Authentication.getAuthentication();
			
			response=webResource
					.header(config.sessionName(), config.sessionValue())
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
	                .put(ClientResponse.class, payload);
			
			
		} catch (URISyntaxException e) {
			logger.error("EXCEPTION @ updateDataSource }  "+e);
			e.printStackTrace();
		}
		return response.toString();
	}

}
