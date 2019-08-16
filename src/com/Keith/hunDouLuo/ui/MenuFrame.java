package com.Keith.hunDouLuo.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class MenuFrame extends JFrame implements MouseListener {
    JLabel start;
    JLabel help;
    JLabel cancel;

    public MenuFrame(){

        start = new JLabel(new ImageIcon("graphics/start.png"));
        start.setBounds(250,300,150,80);
        start.setEnabled(true);
        start.addMouseListener(this);
        this.add(start);

        help = new JLabel(new ImageIcon("graphics/help.png"));
        help.setBounds(250,380,150,80);
        help.setEnabled(true);
        help.addMouseListener(this);
        this.add(help);

        cancel = new JLabel(new ImageIcon("graphics/exit.png"));
        cancel.setBounds(250,460,150,80);
        cancel.setEnabled(true);
        cancel.addMouseListener(this);
        this.add(cancel);

        MenuBack back = new MenuBack();
        this.add(back);

        this.setSize(1003,615);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setIconImage(new ImageIcon("graphics/3.png").getImage());
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new MenuFrame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(start)){
            dispose();
            new WindowFrame().start();
        }
        if(e.getSource().equals(help)){
            dispose();
            new HelpFrame();
        }
        if(e.getSource().equals(cancel)){
            System.exit(0);
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
        if(e.getSource().equals(start)){
            start.setEnabled(false);
        }
        if(e.getSource().equals(help)){
            help.setEnabled(false);
        }
        if(e.getSource().equals(cancel)){
            cancel.setEnabled(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource().equals(start)){
            start.setEnabled(true);
        }
        if(e.getSource().equals(help)){
            help.setEnabled(true);
        }
        if(e.getSource().equals(cancel)){
            cancel.setEnabled(true);
        }
    }
}

class MenuBack extends JPanel{
    Image background;
    public MenuBack(){
        try {
            background = ImageIO.read(new File("graphics/2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(background,0,0,null);
    }
}
