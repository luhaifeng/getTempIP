package com.menkj.gettempip.server.model;

import java.io.Serializable;

/**
 * 合法的设备信息
 * @author zen64
 *
 */
public class DeviceModel implements Serializable {
	private String deviceID;
	private String devicePass;
	private String deviceName;
	private String remoteIP;
	private String timestamp;
	
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getDevicePass() {
		return devicePass;
	}
	public void setDevicePass(String devicePass) {
		this.devicePass = devicePass;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	@Override
	public String toString() {
		return "DeviceModel [DeviceID=" + deviceID + ",  DeviceName=" + deviceName + ", remoteIP="
				+ remoteIP + ", timestamp=" + timestamp + "]";
	}
	
	
}
