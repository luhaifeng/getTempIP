package com.menkj.utils;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {
	   private static SchedulerFactory sf = new StdSchedulerFactory();  
	   private static String JOB_GROUP_NAME = "group1";  
	   private static String TRIGGER_GROUP_NAME = "trigger1";  
	   public static void main(String args[]){
//		   String job_name = "getTempIp";
//		   try {
//			QuartzManager.addJob(job_name, new QuartzJob(), "0/1 * * * * ?");
//			
//			Thread.sleep(5000);  
//		      System.out.println("【修改时间】开始(每2秒输出一次)...");  
//		      QuartzManager.modifyJobTime(job_name, "*/2 * * * * ?");  
//		      Thread.sleep(60000);  
//		      System.out.println("【移除定时】开始...");  
//		      QuartzManager.removeJob(job_name);  
//		      System.out.println("【移除定时】成功");  
//		      
//		      System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");  
//		      QuartzManager.addJob(job_name, new QuartzJob(), "*/10 * * * * ?");  
//		      Thread.sleep(60000);  
//		      System.out.println("【移除定时】开始...");  
//		      QuartzManager.removeJob(job_name);  
//		      System.out.println("【移除定时】成功");
//		      
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
	   }
	     
	   /** *//** 
	    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
	    * @param jobName 任务名 
	    * @param job     任务 
	    * @param time    时间设置，参考quartz说明文档 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void addJob(String jobName,Job job,String time) throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();  
	       JobDetail jobDetail = newJob(job.getClass()).withIdentity(jobName, JOB_GROUP_NAME).build();
	       CronTrigger trigger = newTrigger().withIdentity(jobName,TRIGGER_GROUP_NAME).withSchedule(cronSchedule(time)).build();//指定每个？小时执行一次
	       sched.scheduleJob(jobDetail,trigger);  
	       //启动  
	       if(!sched.isShutdown())  
	          sched.start();  
	   }
	     
	   /** *//** 
	    * 添加一个定时任务 
	    * @param jobName 任务名 
	    * @param jobGroupName 任务组名 
	    * @param triggerName  触发器名 
	    * @param triggerGroupName 触发器组名 
	    * @param job     任务 
	    * @param time    时间设置，参考quartz说明文档 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void addJob(String jobName,String jobGroupName, String triggerName,String triggerGroupName, Job job,String time)   
	                               throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();  
	       JobDetail jobDetail = newJob(job.getClass()).withIdentity(jobName, jobGroupName).build();
	       CronTrigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronSchedule(time)).build();//指定每个？小时执行一次
		   sched.scheduleJob(jobDetail, trigger); 
	       if(!sched.isShutdown())  
	          sched.start();  
	   }  
	     
	   /** *//** 
	    * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
	    * @param jobName 
	    * @param time 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void modifyJobTime(String jobName,String time) throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();  
	       TriggerKey triggerKey = new TriggerKey(jobName,TRIGGER_GROUP_NAME);
	       Trigger trigger =  sched.getTrigger(triggerKey);//.getTrigger(jobName,TRIGGER_GROUP_NAME);  
	       if(trigger != null){  
	           CronTrigger  ct = (CronTrigger)trigger;
	           ct.getTriggerBuilder().withSchedule(cronSchedule(time));
	           sched.resumeTrigger(triggerKey);  
	       }  
	   }  
	     
	   /** *//** 
	    * 修改一个任务的触发时间 
	    * @param triggerName 
	    * @param triggerGroupName 
	    * @param time 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void modifyJobTime(String triggerName,String triggerGroupName, String time) throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();
	       TriggerKey triggerKey = new TriggerKey(triggerName,triggerGroupName);
	       Trigger trigger =  sched.getTrigger(triggerKey);  
	       if(trigger != null){  
	           CronTrigger  ct = (CronTrigger)trigger;  
	           //修改时间  
	           //ct.setCronExpression(time);  
	           ct.getTriggerBuilder().withSchedule(cronSchedule(time));
	           //重启触发器  
	           sched.resumeTrigger(triggerKey);  
	       }  
	   }  
	     
	   /** *//** 
	    * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
	    * @param jobName 
	    * @throws SchedulerException 
	    */  
	   public static void removeJob(String jobName) throws SchedulerException{  
	       Scheduler sched = sf.getScheduler();  
	       TriggerKey tk = new TriggerKey(jobName,TRIGGER_GROUP_NAME);
	       sched.pauseTrigger(tk);//停止触发器  
	       sched.unscheduleJob(tk);//移除触发器  
	       JobKey jk = new JobKey(jobName,JOB_GROUP_NAME);
	       sched.deleteJob(jk);//删除任务  
	   }  
	     
	   /** *//** 
	    * 移除一个任务 
	    * @param jobName 
	    * @param jobGroupName 
	    * @param triggerName 
	    * @param triggerGroupName 
	    * @throws SchedulerException 
	    */  
	   public static void removeJob(String jobName,String jobGroupName, String triggerName,String triggerGroupName)   
	                               throws SchedulerException{  
	       Scheduler sched = sf.getScheduler(); 
	       TriggerKey tk = new TriggerKey(triggerName,triggerGroupName);
	       sched.pauseTrigger(tk);//停止触发器  
	       sched.unscheduleJob(tk);//移除触发器  
	       JobKey jk = new JobKey(jobName,jobGroupName);
	       sched.deleteJob(jk);//删除任务  
	   } 
	   
	   public static void shutdownJobs() {
		    try {
		      Scheduler sched = sf.getScheduler();
		      if (!sched.isShutdown()) {
		        sched.shutdown();
		      }
		    } catch (Exception e) {
		      throw new RuntimeException(e);
		    }
		  }
}
