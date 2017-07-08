package com.COM.port.connect.read.data;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.COM.port.config.COMConfig;
import com.COM.port.connect.ConnectCOMPort;

public class ReadDatafromCOMPort {

	static COMConfig config = ConfigFactory.create(COMConfig.class);
	static Logger logger = LoggerFactory.getLogger(ConnectCOMPort.class);
	
	public ReadDatafromCOMPort() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		System.out.println("Reading Data From COM port");
	}
	public void readData(String data){
		try{
			logger.info("{	Data from COM port	}	"+data);
		}catch(Exception e){
			logger.error("{	Xception@readData	}	"+e);
			e.printStackTrace();
		}
	}
}
