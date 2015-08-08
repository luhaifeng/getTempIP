package com.menkj.gettempip.client.compon;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

import com.menkj.gettempip.constant.Constant;

public class RoundBorder implements Border {
	
	 public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
         g.setColor(Constant.Left_JSPLISTPANE_PANE_BORDER_BACKGROUND);
         g.drawLine(0, 0, width, 0);
         g.drawLine(0, 0, 0, height);
         g.drawLine(width-1, y, width-1, height);
         g.drawLine(0, height-1, width-1, height-1);
     }
     //
     public Insets getBorderInsets(Component c) {
         return new Insets(5, 5, 5, 5);
     }

     public boolean isBorderOpaque() {
         return false;
     }

}
