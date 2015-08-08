package com.menkj.gettempip.client.init;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.menkj.gettempip.constant.Constant;
import com.menkj.utils.PropertiesUtil;

/**
 * 初始化配置参数
 * @author zen64
 * clientID = "clientid";
	public static String clientPass = "clientpass";
	public static String weekStart ="1" , weekEnd = "5" , 
			hourStart="8" , hourEnd="19" , minuteInterval ="3" , 
			serverURL = "http://IP:PORT/getipserver/s";
 */
public class InitParameter {
	public  final static String filename = "prop.properties";
	public static void init() {
		try {
			Properties prop = PropertiesUtil.getProperties(filename);
			Constant.clientPass = prop.getProperty("clientPass",Constant.clientPass);
			Constant.clientID = prop.getProperty("clientID",Constant.clientID);
			Constant.weekStart = prop.getProperty("weekStart",Constant.weekStart);
			Constant.weekEnd = prop.getProperty("weekEnd",Constant.weekEnd);
			Constant.hourStart = prop.getProperty("hourStart",Constant.hourStart);
			Constant.hourEnd = prop.getProperty("hourEnd",Constant.hourEnd);
			Constant.minuteInterval = prop.getProperty("minuteInterval",Constant.minuteInterval);
			Constant.serverURL = prop.getProperty("serverURL",Constant.serverURL);
		} catch (FileNotFoundException e) {
			Map<String , String> map = new HashMap<String,String>();
			map.put("clientPass",Constant.clientPass);
			map.put("clientID",Constant.clientID);
			map.put("weekStart",Constant.weekStart);
			map.put("weekEnd",Constant.weekEnd);
			map.put("hourStart",Constant.hourStart);
			map.put("hourEnd",Constant.hourEnd);
			map.put("minuteInterval",Constant.minuteInterval);
			map.put("serverURL",Constant.serverURL);
			PropertiesUtil.createProperties(filename, map);
		}
		
	}

}
