package com.menkj.gettempip.client.compon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class LeftTitleJLabel extends BaseJLabel {
	private static final long serialVersionUID = 6967379568522941986L;

	public LeftTitleJLabel(String text, Rectangle rectangle){
		super(text);
		this.setFont(new   java.awt.Font("Î¢ÈíÑÅºÚ,Times New Roman",   Font.PLAIN, 15)); 
		this.setForeground(new Color(63,81,181));
		this.setBounds(rectangle);
	}

	public LeftTitleJLabel(String text, Rectangle rectangle, int fontSeize) {
		super(text);
		this.setFont(new   java.awt.Font("Î¢ÈíÑÅºÚ,Times New Roman",   Font.PLAIN, fontSeize)); 
		this.setForeground(new Color(63,81,181));
		this.setBounds(rectangle);
	}
}
