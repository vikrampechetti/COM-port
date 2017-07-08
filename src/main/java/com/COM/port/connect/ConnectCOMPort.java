package com.COM.port.connect;

import java.io.*;
import java.util.*;

import javax.comm.*;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.COM.port.config.COMConfig;
import com.COM.port.connect.read.data.ReadDatafromCOMPort;

public class ConnectCOMPort implements Runnable, SerialPortEventListener {
	
	static COMConfig config = ConfigFactory.create(COMConfig.class);
	static Logger logger = LoggerFactory.getLogger(ConnectCOMPort.class);
	
    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;

    public static void main(String[] args) {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (portId.getName().equals(config.comPortName())) {
			//                if (portId.getName().equals("/dev/term/a")) {
                    ConnectCOMPort reader = new ConnectCOMPort();
                }
            }
        }
    }

    public ConnectCOMPort() {
        try {
            serialPort = (SerialPort) portId.open(config.comSerialPortName(), 2000);
        } catch (PortInUseException e) {
        	System.out.println(e);
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
        	System.out.println(e);
        	logger.error("{	Xcdeption@inputStream	}	"+e);
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
        	System.out.println(e);
        	logger.error("{	Xcdeption@inputStream	}	"+e);
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
        	logger.error("{	Xception@ConnectCOMPort	}	"+e);
        }
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {System.out.println(e);}
    }

    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[20];

            try {
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }
                
                String data = new String(readBuffer);
                System.out.print("Data from COM port "+data);
                
                ReadDatafromCOMPort readDatafromCOMPort = new ReadDatafromCOMPort();
                readDatafromCOMPort.readData(data);
            } catch (IOException e) {
            	System.out.println(e);
            }
            break;
        }
    }
}

