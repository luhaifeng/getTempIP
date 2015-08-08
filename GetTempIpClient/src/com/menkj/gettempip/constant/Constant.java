package com.menkj.gettempip.constant;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.menkj.gettempip.client.compon.RenderContentInterface;
import com.menkj.utils.ImageUtil;

public class Constant {
	
	//===================配置参数
	public static String clientID = "clientid";
	public static String clientPass = "clientpass";
	public static String weekStart ="1" , weekEnd = "5" , 
			hourStart="8" , hourEnd="19" , minuteInterval ="3" , 
			serverURL = "http://ip:port/getIPServer/s/";
	//--------------------配置参数end
	public static boolean isHidden = false;
	public static String jobName = "getTempJob";
	public static RenderContentInterface renderContent;
	public static final Color JSPLITPANE_BACKGROUND = new Color(240 , 243 , 245);
	public static final Color Left_JSPLISTPANE_PANE_BACKGROUND = new Color(255,255,255);
	public static final Color Left_JSPLISTPANE_PANE_BORDER_BACKGROUND = new Color(220 , 223 , 231);
	public static final Color RIGHT_JSPLITPANE_BACKGROUND = new Color(255,255,255);
	private static final String IMAGE_URL_PREFIX = "/menkj/images/";
	private static String windowIcon = "icon.png";
	// 用指定的上、下、左、右四个空白宽度，创建且初始化一个新的 Insets 对象。
	public static final Insets NONE_INSETS = new Insets(0, 0, 0, 0);
	public static final Color LEFT_PANEL_JTEXTFIELD_COLOR = new Color(83,109,254);

	public static Image getWindowIcon() {
		return getImage(windowIcon);
	}

	public static Image getImage(String url) {
		ImageIcon imageIcon = getImageIcon(url);
		if (imageIcon != null)
			return imageIcon.getImage();
		return null;
	}

	public static ImageIcon getImageIcon(String imageName) {
		return ImageUtil.getImageIcon(getImageURL(imageName));// TWaverUtil.getImageIcon(getImageURL(imageName));
	}

	public static String getImageURL(String imageName) {
		return IMAGE_URL_PREFIX + imageName;
	}

	/**
	 * 设置窗体居中
	 * 
	 * @param component
	 */
	public static final void centerWindow(Component component) {
		if (component == null)	return;
		Dimension dimension1 = component.getToolkit().getScreenSize();
		Dimension dimension2 = component.getSize();
		GraphicsConfiguration graphicsConfiguration = component.getGraphicsConfiguration();
		Insets insets;
		if (graphicsConfiguration == null)
			insets = component.getToolkit().getScreenInsets(graphicsConfiguration);
		else
			insets = NONE_INSETS;
		int i = dimension1.height - insets.top - insets.bottom;
		int j = dimension1.width - insets.left - insets.right;
		int k = (i - dimension2.height) / 2 + insets.top;
		int m = (j - dimension2.width) / 2 + insets.left;
		component.setLocation(m, k);
	}
	
	/**
	 * 设置按钮的背景
	 * @param icon
	 * @param roverIcon
	 * @param pressedIcon
	 * @return
	 */
    public static JButton createTransparentButton(ImageIcon icon, ImageIcon roverIcon, ImageIcon pressedIcon) {
        JButton button = new JButton();
        button.setBorder(null);
        button.setMargin(null);
        button.setOpaque(false);
        button.setIcon(icon);
        button.setDisabledIcon(roverIcon);      
        
        
        button.setRolloverEnabled(true);
        
        button.setRolloverIcon(roverIcon);
        button.setPressedIcon(pressedIcon);
        
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setRequestFocusEnabled(false);

        return button;
    }
}
