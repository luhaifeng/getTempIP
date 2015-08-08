package com.menkj.gettempip.server.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.var.Model;

import org.springframework.beans.factory.annotation.Autowired;

import com.menkj.cache.CacheManager;
import com.menkj.gettempip.contant.Constant;
import com.menkj.gettempip.server.init.InitParam;
import com.menkj.gettempip.server.model.DeviceModel;
import com.menkj.gettempip.server.model.UpdateIPModel;
import com.menkj.gettempip.server.service.IPService;

@Path("s")
public class IPController {
	@Autowired
	private IPService ipService;
	
	@Post
	public String updateIP( Invocation inv , UpdateIPModel uipModel ){
		
		HttpServletRequest request = inv.getRequest();
		String remoteIP = request.getRemoteAddr();
		uipModel.setPublicIP(remoteIP);
		return "@"+ipService.updateIP(uipModel);
	}
	
	@Get
	public String GetList(Model model){
		
		List<DeviceModel> deviceList = new ArrayList<DeviceModel>();
		if(InitParam.deviceMap.size()>0){
		   Iterator it = InitParam.deviceMap.keySet().iterator();  
	       while (it.hasNext()) {  
	           String key = it.next().toString();  
	           DeviceModel dm = (DeviceModel) CacheManager.get(Constant.IPPOOL, key );
	           if(dm!=null) {deviceList.add(dm); }
	       }  
		}
		model.add("list", deviceList);
		System.out.println(deviceList.toString());
		return "deviceList";
		
	}
}
