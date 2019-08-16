package com.Keith.hunDouLuo.ui;

import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame implements Runnable {
    JLabel background;

    JProgressBar jtp;
    public WindowFrame(){
        background=new JLabel(new ImageIcon("graphics/window.png"));
        this.add(background, BorderLayout.NORTH);

        jtp = new JProgressBar();
        jtp.setStringPainted(true);
        jtp.setBackground(Color.orange);
        this.add(jtp,BorderLayout.SOUTH);

        this.setSize(750,620);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("graphics/3.png").getImage());
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);

        }

        public void start(){
        WindowFrame windowFrame=new WindowFrame();
        Thread t=new Thread(windowFrame);
        t.start();
        dispose();
        }

    public static void main(String[] args) {
        new WindowFrame().start();
    }
    @Override
    public void run() {
        int [] values = {1,2,3,4,10,30,60,80,90,95,99,99,100};
        for (int i=0;i<values.length;i++){
            jtp.setValue(values[i]);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        dispose();
        new Thread(new MainFrame()).start();
    }
}
