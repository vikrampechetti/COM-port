package com.COM.port.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"file:/usr/local/etc/imap.properties",
"file:C:\\COM\\config\\COMConfig.properties"})

public interface COMConfig extends Config {

}
