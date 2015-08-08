package com.menkj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static String nowDateTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(calendar.getTime());
		return s;
	}

	public static String nowDateNOYearTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"MM-dd HH:mm");
		String s = simpledateformat.format(calendar.getTime());
		return s;
	}
	
	/**
	 * 时间组合，一般用于命名文件名
	 * 
	 * @return
	 */
	public static String nowCompactDateTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		String s = simpledateformat.format(calendar.getTime());
		return s;
	}

	/**
	 * 获取到天
	 * 
	 * @return
	 */
	public static String nowDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String s = simpledateformat.format(calendar.getTime());
		return s;
	}

	public static String formatTime(String stime) {
		String time1 = "";
		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd.HH.mm.ss.SSS");
		//2012-5-3.11.9. 49. 0
		if (stime != null && !stime.equals("") && stime.lastIndexOf(".") != -1) {
			stime = stime.substring(0, stime.lastIndexOf("."));

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd.HH.mm. ss");
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				java.util.Date date = df.parse(stime);
				time1 = d.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time1;
	}
	
	public static int dayofWeek(String pTime) throws Exception {   
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
		  Calendar c = Calendar.getInstance();   
		  c.setTime(format.parse(pTime));   
		  int dayForWeek = 0;   
		  if(c.get(Calendar.DAY_OF_WEEK) == 1){   
		    dayForWeek = 7;   
		  }else{   
		    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;   
		  }   
		  return dayForWeek;   
	}  
	@SuppressWarnings("deprecation")
	public static String nowSjd(){
		String sjd="";
		 Date time=new Date();
		 int hours = time.getHours();
		 int h=hours;
		 //System.out.println(h);
		if(h>5&&h<9){
			sjd="早上好";
		}else if(h>8&&h<11){
			sjd="上午好";
		}else if(h>10&&h<13){
			sjd="中午好";
		}else if(h>12&&h<18){
			sjd="下午好";
		}else{
			sjd="晚上好";
		}
		return sjd;
		  
	}
}
