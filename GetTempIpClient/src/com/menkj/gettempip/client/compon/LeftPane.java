package com.menkj.gettempip.client.compon;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menkj.gettempip.client.QuartzJob;
import com.menkj.gettempip.client.init.InitParameter;
import com.menkj.gettempip.constant.Constant;
import com.menkj.utils.PropertiesUtil;
import com.menkj.utils.QuartzManager;

public class LeftPane extends JPanel {
	private static final Logger log = LoggerFactory.getLogger(LeftPane.class);
	private static final long serialVersionUID = -1556339765918030953L;
	private LeftJTextField clientID , serverURL ;
	private LeftTimeJTextField weekStart , weekEnd , hourStart , hourEnd , minuteInterval; 
	private JPasswordField clientPass;
	private JButton saveBtn , startBtn ,endBtn;
	private boolean isStart = false;
	private RenderContentInterface renderContent ; 
	
	public LeftPane(RightPane rightPane){
		super();
		this.renderContent = rightPane;
		Constant.renderContent = rightPane;
		this.setLayout(null);
		this.setBackground(Constant.Left_JSPLISTPANE_PANE_BACKGROUND);
		this.setBorder(new RoundBorder());//new LineBorder(Constant.Left_JSPLISTPANE_PANE_BORDER_BACKGROUND, 5));
		initContainer();
		initValue();
		initListener();
		startJob();
		
	}
	
	private void initListener() {
		
		//保存配置参数
		saveBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				int n=JOptionPane.showConfirmDialog(null, "确定要保存参数吗？","询问",JOptionPane.YES_NO_OPTION);
				if(n == 1) return;
				Map<String,String> map = new HashMap<String,String>();
				map.put("clientPass",clientPass.getText());
				map.put("clientID", clientID.getText());
				map.put("weekStart", weekStart.getText());
				map.put("weekEnd",weekEnd.getText());
				map.put("hourStart",hourStart.getText());
				map.put("hourEnd",hourEnd.getText());
				map.put("minuteInterval",minuteInterval.getText());
				map.put("serverURL",serverURL.getText());
				
				Constant.clientPass = clientPass.getText();
				Constant.clientID=  clientID.getText();
				Constant.weekStart =  weekStart.getText();
				Constant.weekEnd = weekEnd.getText();
				Constant.hourStart = hourStart.getText();
				Constant.hourEnd = hourEnd.getText();
				Constant.minuteInterval = minuteInterval.getText();
				Constant.serverURL = serverURL.getText();
				
				PropertiesUtil.createProperties(InitParameter.filename, map);
				if(isStart)
					startJob();
			}
		});
		
		/**
		 * 开启任务
		 */
		startBtn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					int n=JOptionPane.showConfirmDialog(null, "确定要开启任务吗？","询问",JOptionPane.YES_NO_OPTION);
					if(n == 1) return ;
					startJob();					
				}
		});
		
		endBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int n=JOptionPane.showConfirmDialog(null, "确定要关闭任务吗？","询问",JOptionPane.YES_NO_OPTION);
				if(n == 1) return ;
				log.info("关闭定时器任务。");
				QuartzManager.shutdownJobs();
				startBtn.setEnabled(true);
				endBtn.setEnabled(false);
				isStart = false;
			}
		});
	}
	private void startJob() {
		int n = 0;
		StringBuffer cronStr = new StringBuffer("0 */");
		if(Constant.minuteInterval!=null && StringUtils.isNumeric(Constant.minuteInterval)){
			cronStr.append(Integer.parseInt(Constant.minuteInterval));
		}else{
			n = JOptionPane.showConfirmDialog(null, "分钟的格式有误，请修改后再重新启动任务！","错误",JOptionPane.YES_OPTION);
			return ;
		}
		
		if(Constant.hourStart !=null && StringUtils.isNumeric( Constant.hourStart )){
			cronStr.append(" "+Integer.parseInt(Constant.hourStart));
		}else{
			n = JOptionPane.showConfirmDialog(null, "小时的格式有误，请修改后再重新启动任务！","错误",JOptionPane.YES_OPTION);
			return ;
		}
		
		if(Constant.hourEnd != null && StringUtils.isNumeric(Constant.hourEnd)){
			cronStr.append("-").append(Integer.parseInt(Constant.hourEnd));
		}else{
			n = JOptionPane.showConfirmDialog(null, "小时的格式有误，请修改后再重新启动任务！","错误",JOptionPane.YES_OPTION);
			return ;
		}
		cronStr.append(" ? *");
		if(Constant.weekStart != null && StringUtils.isNumeric(Constant.weekStart)){
			cronStr.append(" ").append(Integer.parseInt(Constant.weekStart));
		}else{
			n = JOptionPane.showConfirmDialog(null, "星期格式有误，请修改后再重新启动任务！","错误",JOptionPane.YES_OPTION);
			return ;
		}
		if(Constant.weekEnd != null && StringUtils.isNumeric(Constant.weekEnd)){
			cronStr.append("-").append(Integer.parseInt(Constant.weekEnd));
		}else{
			n = JOptionPane.showConfirmDialog(null, "星期格式有误，请修改后再重新启动任务！","错误",JOptionPane.YES_OPTION);
			return ;
		}
		
		log.info("定时器将按照如下规则运行："+cronStr.toString());
		renderContent.render("定时器将按照如下规则运行："+cronStr.toString());
		
		try {
			QuartzManager.removeJob(Constant.jobName);
			QuartzJob qj = new QuartzJob();
			QuartzManager.addJob(Constant.jobName, qj , cronStr.toString());
			startBtn.setEnabled(false);
			endBtn.setEnabled(true);
			isStart = true;
		} catch (SchedulerException e1) {
			e1.printStackTrace();
			isStart = false;
		} catch (ParseException e1) {
			isStart = false;
			e1.printStackTrace();
		}
	}
	/**
	 * 初始化配置参数的值
	 */
	private void initValue() {
		clientID.setText(Constant.clientID); 
		serverURL.setText(Constant.serverURL) ;
		weekStart.setText(Constant.weekStart);
		weekEnd.setText(Constant.weekEnd); 
		hourStart.setText(Constant.hourStart); 
		hourEnd.setText(Constant.hourEnd);
		minuteInterval.setText(Constant.minuteInterval); 
		clientPass.setText(Constant.clientPass);
	}

	/**
	 * 
	 */
	private void initContainer() {
		this.add( new LeftTitleJLabel("客户端ID：" , new Rectangle(10, 20, 180, 30)));	
		clientID   = new LeftJTextField(new Rectangle(15,50 , 180,30));   this.add(clientID);
		
		this.add( new LeftTitleJLabel("访问密码：" , new Rectangle(10, 90, 180, 30)));		
		clientPass = new JPasswordField(); 
		clientPass.setBounds(15, 120, 180, 30); 
		clientPass.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Constant.LEFT_PANEL_JTEXTFIELD_COLOR, 1), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.add(clientPass);
		
		this.add(new LeftTitleJLabel("访问地址：",new Rectangle(10,158 , 180,30)));
		serverURL = new LeftJTextField(new Rectangle(15,188,180,30)); this.add(serverURL);
		
		initTimePanel();
		
	}

	private void initTimePanel() {
		JPanel timePanel = new JPanel(null);
		timePanel.setBorder(BorderFactory.createTitledBorder("执行时间"));
		timePanel.setBounds(10, 242, 200, 130);
		timePanel.setBackground(Color.white);
		this.add(timePanel);
		
		timePanel.add(new LeftTitleJLabel("每周" , new Rectangle(26, 30, 30, 25),12));
		timePanel.add(new LeftTitleJLabel("到" , new Rectangle(75, 30, 30, 25) , 12));
		weekStart = new LeftTimeJTextField(new Rectangle(52,30,20,20));	timePanel.add(weekStart);
		weekEnd = new LeftTimeJTextField(new Rectangle(90,30,20,20));	timePanel.add(weekEnd);
		
		timePanel.add(new LeftTitleJLabel("每天" , new Rectangle(26, 58, 30, 25) , 12));
		timePanel.add(new LeftTitleJLabel("点到" , new Rectangle(85, 58, 50, 25) , 12));
		hourStart = new LeftTimeJTextField(new Rectangle(52,58,30,20));	timePanel.add(hourStart);
		hourEnd = new LeftTimeJTextField(new Rectangle(110,58,30,20));	timePanel.add(hourEnd);
		timePanel.add(new LeftTitleJLabel("点" , new Rectangle(140, 58, 50, 25) , 12));
		
		timePanel.add(new LeftTitleJLabel("每隔" , new Rectangle(26, 86, 30, 25) , 12));
		minuteInterval = new LeftTimeJTextField(new Rectangle(52,86,30,20));	timePanel.add(minuteInterval);
		timePanel.add(new LeftTitleJLabel("分钟执行一次" , new Rectangle(85, 86, 80, 25) , 12));
		
		saveBtn = Constant.createTransparentButton(
				 Constant.getImageIcon("men_btn_save_default.png")
				,Constant.getImageIcon("men_btn_save_hover.png")
				,Constant.getImageIcon("men_btn_save_pre.png"));
		saveBtn.setToolTipText("保存设置");
		saveBtn.setBounds(new Rectangle(50, 380, 100, 30) );
		this.add(saveBtn);
		
		startBtn = Constant.createTransparentButton(
				 Constant.getImageIcon("men_btn_start_default.png")
				,Constant.getImageIcon("men_btn_start_hover.png")
				,Constant.getImageIcon("men_btn_start_pre.png"));
		startBtn.setToolTipText("开启任务");
		startBtn.setBounds(new Rectangle(10, 440, 100, 100) );
		this.add(startBtn);
		
		endBtn = Constant.createTransparentButton(
				 Constant.getImageIcon("men_btn_stop_default.png")
				,Constant.getImageIcon("men_btn_stop_hover.png")
				,Constant.getImageIcon("men_btn_stop_pre.png"));
		endBtn.setToolTipText("结束任务");
		endBtn.setEnabled(false);
		endBtn.setBounds(new Rectangle(120, 440, 100, 100) );
		this.add(endBtn);
	}
	

}
