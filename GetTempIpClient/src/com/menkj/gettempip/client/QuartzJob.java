package com.menkj.gettempip.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menkj.gettempip.constant.Constant;
import com.menkj.utils.TimeUtil;
import com.menkj.utils.UtilHttpClient;
import com.menkj.utils.UtilMD5;

public class QuartzJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(QuartzJob.class);

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String timestamp = TimeUtil.nowDateTime();
		Map<String,String> param=new HashMap<String,String>();
		param.put("UserId", Constant.clientID);
		param.put("UserPass", UtilMD5.md5_32(Constant.clientID+Constant.clientPass+timestamp));//密码
		param.put("timestamp", timestamp);//时间戳
		rendererContent(Constant.clientID+" 访问 "+Constant.serverURL);
		
		try {
			String result[] = UtilHttpClient.POSTWithURL(Constant.serverURL , false , "utf-8",  param );
			if(result[0].compareTo("200")==0){
				rendererContent("访问返回结果："+result[2]);
			}else{
				rendererContent("访问远程地址失败，状态码为："+result[0]);
			}
		} catch (IOException e) {
			rendererContent("访问远程地址失败，"+e.getMessage());
		}
	}
	
	void rendererContent(String content){
		if(Constant.renderContent!=null){
			Constant.renderContent.render(content);
			log.info(content);
		}
	}
}
