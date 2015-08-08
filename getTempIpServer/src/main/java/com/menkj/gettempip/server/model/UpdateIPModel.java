package com.menkj.gettempip.server.model;

public class UpdateIPModel {
	
	private String UserId;
	private String UserPass;
	private String timestamp;
	private String publicIP;
		
	public String getPublicIP() {
		return publicIP;
	}
	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserPass() {
		return UserPass;
	}
	public void setUserPass(String userPass) {
		UserPass = userPass;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "UpdateIPModel [UserId=" + UserId + ", UserPass=" + UserPass
				+ ", timestamp=" + timestamp + ", publicIP=" + publicIP + "]";
	}
	
	
}
