package com.menkj.gettempip.server.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

import com.menkj.gettempip.server.model.DeviceModel;

public class InitParam {
	private static String  devicesName = "devices.csv";
	public static ConcurrentHashMap<String , DeviceModel> deviceMap = new ConcurrentHashMap<String , DeviceModel>();
	static{
		
        try {
        	InputStream in = InitParam.class.getClassLoader().getResourceAsStream(devicesName);
    		InputStreamReader isr = new InputStreamReader(in,"utf-8");
    	    BufferedReader br = new BufferedReader(isr);
            String data = null;
			while((data = br.readLine())!=null){  
			    String devices[] = data.split(",");
			    if(devices.length==3){
				    DeviceModel dm = new DeviceModel();
				    dm.setDeviceID(devices[1]);
				    dm.setDeviceName(devices[0]);
				    dm.setDevicePass(devices[2]);
				    deviceMap.put(dm.getDeviceID(), dm);
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}	
}
