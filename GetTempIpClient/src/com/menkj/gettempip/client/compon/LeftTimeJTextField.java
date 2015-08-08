package com.menkj.gettempip.client.compon;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.menkj.gettempip.constant.Constant;

public class LeftTimeJTextField extends BaseJTextField {
	private static final long serialVersionUID = -6814736224969571407L;

	public LeftTimeJTextField(Rectangle rectangle){
		super();
		this.setBounds(rectangle);
		
		this.setBorder(BorderFactory.createCompoundBorder(baseLineBorder, 
		        BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		this.setFont(new Font("Î¢ÈíÑÅºÚ,Road new man" , Font.PLAIN , 12));
	}
	
	Border baseLineBorder = new Border(){

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			 g.setColor(Constant.Left_JSPLISTPANE_PANE_BORDER_BACKGROUND);
	         g.drawLine(0, height-1, width, height-1);
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(0 , 0, 0, 0);
		}

		@Override
		public boolean isBorderOpaque() {
			return false;
		}
		
	};
}
