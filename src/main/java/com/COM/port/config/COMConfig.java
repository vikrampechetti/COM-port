package com.COM.port.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"file:/usr/local/etc/imap.properties",
"file:C:\\COM\\config\\COMConfig.properties"})

public interface COMConfig extends Config {
	String comPortName();
	
	String comSerialPortName();
	
	int comSerialPort();
	/****************************************
	 * IBM IOC access Credentials
	 ****************************************/
	String host();

	String host_1();

	String port();

	/****************************************
	 * IBM-Session-ID, Value
	 ****************************************/
	String sessionName();

	String sessionValue();

	/****************************************
	 * DB2 Credentials
	 ****************************************/
	String DB2Username();

	String DB2Usersecret();

	String DB2ConnectionURL();

	/*****************************************
	 * IBM IOC Console
	 *****************************************/
	String username();

	String userSecret();
	/*****************************************
	 * CCTV configurations
	 ****************************************/
	String CCTVDataSourceID();

	String CCTVDataSourceName();

	String CCTVDB2Table();

	String CCTVRequiredFields();
}
