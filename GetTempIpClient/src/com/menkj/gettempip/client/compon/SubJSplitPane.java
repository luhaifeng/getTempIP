package com.menkj.gettempip.client.compon;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

import com.menkj.gettempip.constant.Constant;

public class SubJSplitPane extends JSplitPane {
	private static final long serialVersionUID = -6299366160943591064L;
	
	private RightPane rightPane = new RightPane();
	private LeftPane leftPane = new LeftPane(rightPane);
	public SubJSplitPane(){
		super();
		this.setDividerSize(0);
		this.setBackground(Constant.JSPLITPANE_BACKGROUND);
		this.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		this.setLeftComponent(leftPane);
		this.setRightComponent(rightPane);
	}
}
