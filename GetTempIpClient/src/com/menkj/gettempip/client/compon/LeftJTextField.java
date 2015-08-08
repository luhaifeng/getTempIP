package com.menkj.gettempip.client.compon;

import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;

import com.menkj.gettempip.constant.Constant;

public class LeftJTextField extends BaseJTextField {
	private static final long serialVersionUID = -6814736224969571407L;

	public LeftJTextField(Rectangle rectangle){
		super();
		this.setBounds(rectangle);
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Constant.LEFT_PANEL_JTEXTFIELD_COLOR, 1), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.setFont(new Font("Î¢ÈíÑÅºÚ,Road new man" , Font.PLAIN , 14));
	}
}
