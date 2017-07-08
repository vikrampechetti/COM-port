package com.COM.port;

import org.aeonbits.owner.ConfigFactory;

import com.COM.port.config.COMConfig;

/**
 * Hello world!
 *
 */
public class App 
{
	static COMConfig config = ConfigFactory.create(COMConfig.class);
    public static void main( String[] args )
    {
        System.out.println(config.comPortName()+config.comSerialPort()+config.comSerialPortName() );
    }
}
