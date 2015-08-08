package com.menkj.gettempip.client.compon;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

public class MyBorder extends AbstractBorder {

    private static final long serialVersionUID = 1L;

    private int xOff;
    private int yOff;
    private Insets insets;

    public MyBorder(int x, int y) {
        this.xOff = x;
        this.yOff = y;
        this.insets = new Insets(0, 0, this.xOff, this.yOff);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return this.insets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
            int height) {
        g.translate(x, y);
        BufferedImage rightImage = createBufferedImage(this.xOff, height
                - this.yOff, Color.red, 0.5f);
        BufferedImage bottomImage = createBufferedImage(width - 2 * this.xOff,
                height, Color.green, 0.5f);
        g.drawImage(rightImage, width - this.xOff, this.yOff, null);
        g.drawImage(bottomImage, this.xOff, height - this.yOff, null);
        g.translate(-x, -y);

    }

    private BufferedImage createBufferedImage(int width, int height,
            Color color, float alpha) {
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = bufferedImage.createGraphics();
        bufferedImage = g2d.getDeviceConfiguration().createCompatibleImage(
                width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = bufferedImage.createGraphics();
        g2d.setColor(color);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                alpha));
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        return bufferedImage;

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("border");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField text = new JTextField("helloKitty");
        text.setPreferredSize(new Dimension(100, 30));
        JButton button = new JButton("donald duck");
        // text.setBorder(new MyBorder(10, 10));
        // text.setForeground(Color.yellow);
        frame.getContentPane().setLayout(new BorderLayout());
        button.setBorder(new MyBorder(5, 5));
        frame.getContentPane().add(text, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.setSize(300, 300);
        frame.setVisible(true);

    }
}