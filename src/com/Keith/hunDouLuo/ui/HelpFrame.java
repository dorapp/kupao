package com.Keith.hunDouLuo.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class HelpFrame extends JFrame implements MouseListener {
    JButton cancel;
    public HelpFrame(){
        cancel = new JButton("返回");
        cancel.setBounds(800,520,60,40);
        cancel.addMouseListener(this);
        this.add(cancel);

        HelpBack back = new HelpBack();
        this.add(back);

        this.setSize(960,640);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(new ImageIcon("graphics/3.png").getImage());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new HelpFrame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(cancel)){
            dispose();
            new MenuFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(cancel)){
            cancel.setEnabled(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource().equals(cancel)){
            cancel.setEnabled(false);
        }
    }
}

class HelpBack extends JPanel{
    Image background;
    public HelpBack(){
        try {
            background = ImageIO.read(new File("graphics/backgrounds/helpframe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(background,0,0,960,640,null);
    }

}
