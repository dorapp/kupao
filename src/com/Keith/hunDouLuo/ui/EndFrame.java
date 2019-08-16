package com.Keith.hunDouLuo.ui;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndFrame extends JFrame implements MouseListener {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 672;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = this.toolkit.getScreenSize();
    private BackPanel backPanel;
    private JLabel again;
    private JLabel back;
    private ImageIcon againimg;
    private ImageIcon backimg;
    private ImageIcon loseimg;
    private ImageIcon sucessimg;
    private JLabel lose;
    private JLabel sucess;

    private int life;
    AudioStream as ;
    private String musicURL;
    public EndFrame(int life){
        this.life = life;
        loseimg = new ImageIcon("graphics/backgrounds/loser1.png");
        lose = new JLabel(loseimg);
        lose.setBounds(450,80,300,200);
        sucessimg = new ImageIcon("graphics/backgrounds/suc1.png");
        sucess = new JLabel(sucessimg);
        sucess.setBounds(450,80,300,200);
        if(life == 0){
            musicURL = "audios/died.wav";
            try {
                as = Audios.getAudio(musicURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AudioPlayer.player.start(as);
            this.add(lose);
        }
        else {
            musicURL = "audios/victory.mid";
            try {
                as = Audios.getAudio(musicURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AudioPlayer.player.start(as);
            this.add(sucess);
        }
        againimg = new ImageIcon("graphics/backgrounds/again.png");
        again = new JLabel(againimg);
        again.setBounds(500,320,200,80);
        again.setEnabled(true);
        again.addMouseListener(this);
        this.add(again);
        backimg = new ImageIcon("graphics/backgrounds/back.png");
        back = new JLabel(backimg);
        back.setBounds(500,450,200,80);
        back.setEnabled(true);
        back.addMouseListener(this);
        this.add(back);

        backPanel = new BackPanel();
        this.add(backPanel);
        this.setSize(WIDTH , HEIGHT);
        setLocation((this.screenSize.width - getWidth()) / 2, 20);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon("graphics/3.png").getImage());
        this.setUndecorated(true);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(again)) {
            again.setEnabled(false);
            dispose();
            AudioPlayer.player.stop(as);
            new WindowFrame().start();
        }else if(e.getSource().equals(back)) {
            back.setEnabled(false);
            dispose();
            AudioPlayer.player.stop(as);
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
        if(e.getSource().equals(again)) {
            again.setEnabled(false);
        }else if(e.getSource().equals(back)) {
            back.setEnabled(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(again)) {
            again.setEnabled(true);
        } else if (e.getSource().equals(back)) {
            back.setEnabled(true);
        }
    }
}

class BackPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(Imgs.ENDFRAME,0,0,null);
    }
}
