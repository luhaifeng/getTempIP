package com.menkj.gettempip.client.compon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.menkj.utils.TimeUtil;

public class RightPane extends JPanel implements RenderContentInterface {
	
	private static final long serialVersionUID = 7091297162286315241L;
	private JTextArea textArea ;
	public RightPane(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(0, 5, 0, 5),
					BorderFactory.createTitledBorder("消息接收窗口")  
					));
		//this.setBackground(Color.white);
		initContainer();
	}
	private void initContainer() {
		textArea = new JTextArea(10, 10);
		textArea.setTabSize(4);  
		textArea.setFont(new Font("微软雅黑", Font.PLAIN, 13));  
		textArea.setEditable(false);
		textArea.setLineWrap(true);// 激活自动换行功能  
		textArea.setWrapStyleWord(true);// 激活断行不断字功能  
		textArea.setBackground(Color.white); 
		textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (textArea.getLineCount() >= 30) {
                            int end = 0;
                            try {
                                end = textArea.getLineEndOffset(25);
                            } catch (Exception e) {
                            }
                            textArea.replaceRange("", 0, end);
                        }
                    }
                });
            }
            public void removeUpdate(DocumentEvent evt) {
            }
            public void changedUpdate(DocumentEvent evt) {
            }
        });
		
		JScrollPane jscrollPane = new JScrollPane(textArea); 
		jscrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5, 5, 5, 5),
				BorderFactory.createEmptyBorder()));
		this.add(jscrollPane, BorderLayout.CENTER);
		//this.add(jscrollPane);
	}
	
	@Override
	/**
	 * 填充内容
	 */
	public void render(String content) {
		textArea.append("["+TimeUtil.nowDateTime()+"] "+content+"\n");
	}
}
