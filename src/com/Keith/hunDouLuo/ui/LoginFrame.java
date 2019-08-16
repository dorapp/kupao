package com.Keith.hunDouLuo.ui;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class LoginFrame extends JFrame {
    JLabel usernameLabel;
    JTextField userText;
    JLabel passwordLabel;
    JTextField passwordText;
    String username;
    String password;
    JButton loginButton;
    JButton cancelButton;

    public LoginFrame(){
        usernameLabel = new JLabel("用户名");
        usernameLabel.setBounds(440,160,60,35);
        usernameLabel.setOpaque(false);
        usernameLabel.setFont(new Font("宋体",Font.BOLD,18));
        this.add(usernameLabel);

        userText = new JTextField();
        userText.setBounds(510,160,120,30);
        userText.setOpaque(false);
        this.add(userText);

        passwordLabel = new JLabel("密  码");
        passwordLabel.setBounds(440,220,60,35);
        passwordLabel.setFont(new Font("宋体",Font.BOLD,18));
        this.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(510,220,120,30);
        passwordText.setOpaque(false);
        this.add(passwordText);

        loginButton = new JButton("登录");
        loginButton.setBounds(460,280,60,30);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username=userText.getText();
                password=passwordText.getText();
                if(username.equals("张三") && password.equals("123456")){
                    JOptionPane.showMessageDialog(null,"登录");
                    dispose();
                    new MenuFrame();
                }else if(username.length()==0 || password.length()==0){
                    JOptionPane.showMessageDialog(null,"用户名和密码不能为空");
                }else if(username.length()==0 || password.equals("123456")){
                    JOptionPane.showMessageDialog(null,"用户名不能为空");
                }else if(username.equals("张三")|| password.length()==0){
                    JOptionPane.showMessageDialog(null,"密码不能为空");
                }else if(!(username.equals("张三")) ||!(password.equals("123456"))){
                    JOptionPane.showMessageDialog(null,"用户名和密码错误");
                }
            }
        });
        this.add(loginButton);

        cancelButton = new JButton("退出");
        cancelButton.setBounds(550,280,60,30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
        LoginBack back = new LoginBack();
        this.add(back);
        this.setSize(1003,615);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setIconImage(new ImageIcon("graphics/3.png").getImage());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}

class LoginBack extends JPanel{
    Image backgroud;
    public LoginBack(){
        try {
            backgroud = ImageIO.read(new File("graphics/1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(backgroud,0,0,null);
    }
}


