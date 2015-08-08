package com.menkj.gettempip.client;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menkj.gettempip.client.compon.SubJSplitPane;
import com.menkj.gettempip.client.init.InitParameter;
import com.menkj.gettempip.constant.Constant;

/**
 * 客户端入口
 * 
 * @author zen64
 * 
 */
public class MainWindow extends JFrame {
	private static final Logger log = LoggerFactory.getLogger(MainWindow.class);
	private static final long serialVersionUID = -5824223763256719037L;
	private static String productName = "临时公网IP获取客户端";
	private static String version = "0.2";
	private static JFrame _frame;
	private static TrayIcon trayIcon = null;
	static SystemTray tray = SystemTray.getSystemTray();//系统托盘
	private static SubJSplitPane subJSplitPane = null;
	public MainWindow() {
		_frame = this;
		initParam();
		initWindowAttibues();
		initSystemTray();
		initWindowListener();
		initJSplitPane();
	}
	
	/**
	 * 初始化配置参数
	 */
	private void initParam() {
		InitParameter.init();
	}

	/**
	 * 初始化splitPane
	 */
	private void initJSplitPane() {
		subJSplitPane = new SubJSplitPane();
		
		subJSplitPane.setDividerLocation(237); 
		_frame.add(subJSplitPane);
	}

	/**
	 * 初始化窗体事件
	 */
	private void initWindowListener() {
		this.addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) { // 窗口最小化事件
				_frame.setVisible(false);
			}
		});
	}

	/**
	 * 初始化系统托盘
	 */
	private void initSystemTray() {

		if (SystemTray.isSupported()) // 判断系统是否支持系统托盘
		{
			Image trayImg = Constant.getWindowIcon();// 托盘图标
			PopupMenu pop = new PopupMenu(); // 增加托盘右击菜单
			MenuItem showMenuItem = new MenuItem("  还 原  ");
			MenuItem exitMenuItem = new MenuItem("  退 出  ");
			
			showMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { //按下还原键
					//tray.remove(trayIcon);
					displayWin();
				}
			});
			
			exitMenuItem.addActionListener(new ActionListener() { // 按下退出键
				public void actionPerformed(ActionEvent e) {
					tray.remove(trayIcon);
					System.exit(0);
				}
			});

			pop.add(showMenuItem);
			pop.add(exitMenuItem);

			trayIcon = new TrayIcon(trayImg, productName + " v"+version+"", pop);
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) { // 鼠标器双击事件
					if (e.getClickCount() == 2) {
						displayWin();
					}
				}
			});

			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 显示主窗口
	 */
	void displayWin(){
		if(_frame.isVisible()) return;
		_frame.setVisible(true);
		_frame.setExtendedState(JFrame.NORMAL); // 还原窗口
		_frame.toFront();
	}
	
	/**
	 * 初始化窗体基本信息
	 */
	private void initWindowAttibues() {

		// set customized window border 窗口边框透明
		// this.setUndecorated(true);
		// this.setLocationRelativeTo(null);//窗口居中不起作用？？

		// 设置系统主题
		setLookAndFeel();

		this.setTitle(productName + " v"+version+"");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(924, 602);
		this.setIconImage(Constant.getWindowIcon());
		this.setLocationRelativeTo(null);// 要放到setSize下面，不然居中位置不对

	}

	private void setLookAndFeel() {
		try {
			String lookAndFeel = "default";// Constant.getLookAndFeel();
			if ("default".compareTo(lookAndFeel) == 0)
				lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (UnsupportedLookAndFeelException ex) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		if(args!=null){
			for (int i=0;i<args.length;i++){
				String startStr = args[i];
				if(startStr!=null && startStr.compareTo("hidden") == 0){
					//自动隐藏
					Constant.isHidden = true;
				}
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final MainWindow mWin = new MainWindow();
				mWin.setVisible(true);
				if(Constant.isHidden){
					_frame.setVisible(false);
				}
				log.debug("获取临时公网 IP客户端启动成功！");
				
			}
		});
	}

}
