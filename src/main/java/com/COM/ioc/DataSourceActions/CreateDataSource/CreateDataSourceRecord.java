package com.COM.ioc.DataSourceActions.CreateDataSource;

import java.net.URI;

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
public class CreateDataSourceRecord {
	static Logger logger = LoggerFactory.getLogger(CreateDataSourceRecord.class);
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	
	URI uri;
	URI uri_1;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	String output = null;
	String host = null;
	
	/**
	 * To Create a Datasource Record
	 */
	public String createDataSourceRecord(String dataSourceID,String payload){
		try{
			//getConnectionStatus();
			logger.info("Creating DataSource...");
			uri = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/data-injection-service/datablocks/"+dataSourceID+"/dataitems");
			webResource = client.resource(uri);
			Authentication.getAuthentication();

			response = webResource
					.header(config.sessionName(), config.sessionValue())
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class,payload);
		}catch(Exception e){
			logger.error("EXCEPTION  @ CreateDataSourceRecord } "+e);
			e.printStackTrace();
		}
		return response.toString();
	}
	
	/**
	 * To Get the Connection Status of servers(*Multi servers)
	 */
	public boolean getConnectionStatus(){
		try{
			uri = new URI("https://"+config.host_1()+":"+config.port()+"/ibm/ioc/api/datasource-service/tabs");
			webResource = client.resource(uri);
			response = webResource.header(config.sessionName(), config.sessionValue())
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			if(response.toString().contains("200")){
				host = config.host_1();
				logger.info("[	Able to connect host@ ]"+config.host_1());
			}						
		}catch(Exception e1){
			logger.error("[	Exception@@ CreateDataSourceRecord	]	",e1);
			try{
				uri_1 = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/datasource-service/tabs");
				webResource = client.resource(uri_1);
				response = webResource.header(config.sessionName(), config.sessionValue())
						.accept(MediaType.APPLICATION_JSON)
		                .type(MediaType.APPLICATION_JSON)
						.get(ClientResponse.class);
				if(response.toString().contains("200")){
					host = config.host();
					logger.info("[	Able to connect host@ ]"+config.host());
				}
			}catch(Exception e){
				logger.error("[	Exception@ conneting host()	]	",e);
			}
		}
		return true;
	}
}

