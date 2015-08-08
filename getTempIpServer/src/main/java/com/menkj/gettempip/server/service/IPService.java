package com.menkj.gettempip.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.menkj.cache.CacheManager;
import com.menkj.gettempip.contant.Constant;
import com.menkj.gettempip.server.init.InitParam;
import com.menkj.gettempip.server.model.DeviceModel;
import com.menkj.gettempip.server.model.UpdateIPModel;
import com.menkj.utils.UtilMD5;

@Service("ipService")
public class IPService {
	private static final Logger log = LoggerFactory.getLogger(IPService.class);
	/**
	 * 更新获取的临时公网IP
	 * @param uipModel
	 */
	public String updateIP(UpdateIPModel uipModel) {
		 DeviceModel dm = InitParam.deviceMap.get(uipModel.getUserId());
		 if(dm == null ){
			 log.info("不合法的设备访问："+uipModel.toString());
			 return "该设备不合法，本机IP提交失败";
		 }
		 if( uipModel.getUserId()==null || uipModel.getUserId().compareTo("")==0 ||
				 uipModel.getUserPass() == null || uipModel.getUserPass().compareTo("") == 0 ){
			 log.info("不合法的设备访问："+uipModel.toString());
			 return "提交的信息有误，本机IP提交失败";
		 }
		 String pass = UtilMD5.md5_32(dm.getDeviceID()+dm.getDevicePass()+uipModel.getTimestamp());
		 if( pass.compareTo(uipModel.getUserPass())==0 ){//合法，进行登记
			 // 记录信息
			 dm.setRemoteIP(uipModel.getPublicIP());
			 dm.setTimestamp(com.menkj.utils.TimeUtil.nowDateNOYearTime());
			 CacheManager.set(Constant.IPPOOL, uipModel.getUserId(), dm);
			 log.info("成功登记设备信息："+uipModel.toString());
			 return "提交临时外网IP成功，IP地址为："+uipModel.getPublicIP();
		 }
		 log.info("不合法的设备访问："+uipModel.toString());
		 return "提交本机IP失败";
		 
	}
	
}
