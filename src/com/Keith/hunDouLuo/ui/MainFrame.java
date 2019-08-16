package com.Keith.hunDouLuo.ui;

import sun.audio.AudioPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends Frame implements Runnable{
    private boolean firstTime = true;
    private BufferedImage bi;
    private Graphics2D big;
    public float mapPosition = 0.0F;
    private FloatPoint bgPosition = new FloatPoint(20.0F, 90.0F);
    private FloatPoint scenePosition = new FloatPoint(0.0F, 0.0F);
    private int sceneWidth = 3328;
    private int backgroundSizeOfWidth = 900;
    private int backgroundSizeOfHeight = 672;
    private Hero hero;
    private static int FPS = 60;
    private int walkSpeed = 1;
    private GrassLand[] grassLands = new GrassLand[41];
    List<Bullet> heroBullets;
    List<Bullet> enemyBullets;
    private boolean leftDown = false;
    private boolean rightDown = false;
    private boolean upDown = false;
    private boolean downDown = false;
    private boolean jumpDown = false;
    private int heroNum = 100;//英雄生命
    private List<SimpleEnemy> enemys = new ArrayList();
    private boolean showBossScene = false;
    private Boss boss;

    GameStartBackgroundSoundThread gameStartBgSoundThread;
    PreBossBackgroundSoundThread preBossBgSoundThread;
    BossBackgroundSoundThread bossBgSound;

    boolean bossBattle = false;

    boolean playPreBossStoneSound = true;
    boolean preBossBattle = false;

    private boolean passedFirstMovingGrassLand = false;
    private boolean passedSecondMovingGrassLand = false;

    boolean stopBossBgSound = true;

    public MainFrame() {
        this.gameStartBgSoundThread = new GameStartBackgroundSoundThread();
        new Thread(this.gameStartBgSoundThread).start();
        this.hero = new Hero(new FloatPoint(50.0F, 30.0F), 2);//人物初始坐标和方向
        this.heroBullets = new ArrayList();
        this.enemyBullets = new ArrayList();
        initGrassLands();
        initEnemys();
        this.setSize(this.backgroundSizeOfWidth, this.backgroundSizeOfHeight);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.GRAY);
        this.setIconImage(new ImageIcon("graphics/3.png").getImage());
        this.setVisible(false);

        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e)
            {
                if (KeyEvent.getKeyText(e.getKeyChar()).equals("Esc"))
                    System.exit(0);
            }

            public void keyReleased(KeyEvent e)
            {
                MainFrame.this.setKeyStateWhenKeyReleased(e);
                if ((MainFrame.this.hero.state != 4) && (MainFrame.this.hero.state != 5)) {//非死亡状态下跳跃和射击
                    if (KeyEvent.getKeyText(e.getKeyCode()).equals("J")) {
                        MainFrame.this.hero.canShot = true;//射击状态设为true
                    }
                    if (KeyEvent.getKeyText(e.getKeyCode()).equals("K"))
                        MainFrame.this.hero.canDrop = true;//跳跃状态下下落状态置为true
                }
            }

            public void keyPressed(KeyEvent e) {
                MainFrame.this.setKeyStateWhenKeyPressed(e);
                if ((MainFrame.this.hero.state != 4) && (MainFrame.this.hero.state != 5)) {
                    if (KeyEvent.getKeyText(e.getKeyCode()).equals("J")) {
                        if (MainFrame.this.hero.canShot) {
                            if (((MainFrame.this.hero.weapon instanceof SimpleWeapon)) && (MainFrame.this.heroBullets.size() < 6)) {//instanceof用来判断武器种类
                                MainFrame.this.heroBullets.addAll(MainFrame.this.hero.shot(1));
                            }
                        }
                        MainFrame.this.hero.canShot = false;
                    }
                    if ((KeyEvent.getKeyText(e.getKeyCode()).equals("K")) &&
                            (MainFrame.this.hero.canDrop) && (MainFrame.this.hero.state == 3) && (MainFrame.this.hero.position.y < MainFrame.this.backgroundSizeOfHeight - 60)) {
                        if (MainFrame.this.hero.position.y < 206.0F)
                            MainFrame.this.hero.position.y += 6.0F;
                        MainFrame.this.hero.canDrop = false;
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("宋体",1,30));
        g.drawString("魂 斗 罗",735,180);
        g.drawString("创 造 1024",720,280);
        g.drawImage(Imgs.amuse,730,320,150,200,null);
    }

    private void initEnemys() {
        SimpleEnemy[] enemy0 = { new SimpleEnemy(new FloatPoint(300.0F, 100.0F), 6, 0),
                new SimpleEnemy(new FloatPoint(332.0F, 100.0F), 6, 0),
                new SimpleEnemy(new FloatPoint(390.0F, 100.0F), 6, 0),
                new SimpleEnemy(new FloatPoint(450.0F, 100.0F), 6, 0),
                new SimpleEnemy(new FloatPoint(550.0F, 100.0F), 6, 0) };
        SimpleEnemy[] enemy1 = { new SimpleEnemy(new FloatPoint(600.0F, 100.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(600.0F, 100.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(620.0F, 100.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(1320.0F, 100.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(1380.0F, 100.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(1650.0F, 130.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(1750.0F, 130.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(3070.0F, 190.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(3140.0F, 190.0F), 6, 1),
                new SimpleEnemy(new FloatPoint(3200.0F, 190.0F), 6, 1) };
        SimpleEnemy[] enemy2 = { new SimpleEnemy(new FloatPoint(1400.0F, 70.0F), 6, 2),
                new SimpleEnemy(new FloatPoint(1430.0F, 70.0F), 6, 2),
                new SimpleEnemy(new FloatPoint(2240.0F, 100.0F), 6, 2),
                new SimpleEnemy(new FloatPoint(2460.0F, 100.0F), 6, 2),
                new SimpleEnemy(new FloatPoint(2600.0F, 100.0F), 6, 2),
                new SimpleEnemy(new FloatPoint(2860.0F, 165.0F), 6, 2),
                new SimpleEnemy(new FloatPoint(2960.0F, 130.0F), 6, 2) };
        SimpleEnemy[] enemy3 = { new SimpleEnemy(new FloatPoint(465.0F, 138.0F), 6, 3),
                new SimpleEnemy(new FloatPoint(662.0F, 148.0F), 6, 3),
                new SimpleEnemy(new FloatPoint(900.0F, 100.0F), 6, 3),
                new SimpleEnemy(new FloatPoint(1000.0F, 100.0F), 6, 3),
                new SimpleEnemy(new FloatPoint(1650.0F, 70.0F), 6, 3),
                new SimpleEnemy(new FloatPoint(2220.0F, 130.0F), 6, 3),
                new SimpleEnemy(new FloatPoint(2490.0F, 70.0F), 6, 3),
                new SimpleEnemy(new FloatPoint(2990.0F, 100.0F), 6, 3) };
        SimpleEnemy[] enemy4 = { new SimpleEnemy(new FloatPoint(306.0F, 200.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(440.0F, 100.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(600.0F, 195.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(950.0F, 100.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(1250.0F, 100.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(1550.0F, 70.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(1500.0F, 140.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(1750.0F, 70.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(1850.0F, 200.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(2090.0F, 70.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(2320.0F, 200.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(2470.0F, 200.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(3050.0F, 100.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(3050.0F, 150.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(3050.0F, 190.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(3110.0F, 100.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(3140.0F, 130.0F), 6, 4),
                new SimpleEnemy(new FloatPoint(3180.0F, 160.0F), 6, 4) };
        for (int i = 0; i < enemy0.length; i++) {
            enemy0[i].state = 0;
            this.enemys.add(enemy0[i]);
        }
        for (int i = 0; i < enemy1.length; i++) {
            enemy1[i].state = 0;
            this.enemys.add(enemy1[i]);
        }
        for (int i = 0; i < enemy2.length; i++) {
            enemy2[i].state = 0;
            this.enemys.add(enemy2[i]);
        }
        for (int i = 0; i < enemy3.length; i++) {
            enemy3[i].state = 0;
            this.enemys.add(enemy3[i]);
        }
        for (int i = 0; i < enemy4.length; i++) {
            enemy4[i].state = 0;
            this.enemys.add(enemy4[i]);
        }
    }

    public void update(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        if (this.firstTime) {
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            new Rectangle(dim);
            this.bi = ((BufferedImage)createImage(w, h));
            this.big = this.bi.createGraphics();
            this.firstTime = false;
            this.big.setStroke(new BasicStroke(1.0F));
        }

        this.big.drawImage(Imgs.BACKGROUND01, (int)this.scenePosition.x, (int)this.scenePosition.y, this.backgroundSizeOfWidth, (int)this.scenePosition.y + 672, (int)this.mapPosition, 0, (int)this.mapPosition + this.backgroundSizeOfWidth/3, 224, null);
        drawNPC(this.big);
        if (this.showBossScene) {
            playPreBossBattleAnimation(this.big);
        }
        if (this.bossBattle) {
            playBossBattleAnimation(this.big);
        }
        for (int i = 0; i < this.enemys.size(); i++) {
            (this.enemys.get(i)).drawPlayer(this.big, this.mapPosition);
        }
        this.hero.drawPlayer(this.big, this.mapPosition);//人物更新
        drawHeroBullets(this.big);
        drawEnemyBullets(this.big);
        g2.drawImage(this.bi, (int)this.bgPosition.x, (int)this.bgPosition.y, (int)(this.backgroundSizeOfWidth / 1.3D), (int)(this.backgroundSizeOfHeight / 1.3D), this);
    }

    private void playBossBattleAnimation(Graphics2D g) {
        this.boss.drawBoss(g, this.mapPosition);
        if ((this.boss != null) && (this.boss.isAlive)) {
            this.enemyBullets.addAll(this.boss.simpleShot(new FloatPoint(this.hero.position.x + 0.001F, this.hero.position.y + 0.001F), 1));
        }  else {

            AudioPlayer.player.stop(this.gameStartBgSoundThread.as);
            this.gameStartBgSoundThread.playSound = false;
            dispose();
            new EndFrame(this.heroNum);
        }
    }
    private void playPreBossBattle(Graphics2D big2)
    {

        this.preBossBattle = false;
        this.bossBattle = true;
        this.boss = new Boss(new FloatPoint(this.backgroundSizeOfWidth / 6 + this.mapPosition, 70.0F));
    }

    public void run()
    {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(true);
        while (true) {
            repaint(0, 0, 720, this.backgroundSizeOfHeight);
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heroMove();
            checkHeroIsDropping();
            heroDeathCheck();
            setEnemyVisibility();
            enemysMove();
            enemysDeathCheck();
            enemyShotControl();
            if ((this.hero.position.x >= 880.0F) && (this.hero.position.x <= 950.0F) && (this.hero.position.y >= 20.0F) && (this.hero.position.y <= 111.0F))
                this.passedFirstMovingGrassLand = true;//人物通过移动草坪的判断
            if ((this.hero.position.x >= 1170.0F) && (this.hero.position.y >= 20.0F) && (this.hero.position.y <= 111.0F)) {
                this.passedSecondMovingGrassLand = true;
            }
            if ((this.hero.state == 5) && (this.heroNum > 0)) {
                heroBirth();//人物重生
            }

            if ((this.hero.position.x > 3260.0F) && (this.mapPosition < 3328.0F) && (this.playPreBossStoneSound)) {
                this.showBossScene = true;
                this.playPreBossStoneSound = false;
                this.preBossBgSoundThread = new PreBossBackgroundSoundThread();
                new Thread(this.preBossBgSoundThread).start();
                AudioPlayer.player.stop(this.gameStartBgSoundThread.as);
                this.gameStartBgSoundThread.playSound = false;
            }

            if (this.preBossBattle)
                playPreBossBattle(this.big);
        }
    }

    private void enemyShotControl()
    {
        for (int i = 0; i < this.enemys.size(); i++)
            if ((this.hero.position.x > (this.enemys.get(i)).position.x - this.backgroundSizeOfWidth / 3) &&
                    (this.hero.position.x < (this.enemys.get(i)).position.x + this.backgroundSizeOfWidth / 3) &&
                    ((this.enemys.get(i)).type == 4)) {
                this.enemyBullets.addAll((this.enemys.get(i)).shot(new FloatPoint(this.hero.position.x + 0.001F, this.hero.position.y + 0.001F), 1));
                (this.enemys.get(i)).canShot = true;
            }
            else {
                (this.enemys.get(i)).canShot = false;
            }
    }

    private void playPreBossBattleAnimation(Graphics2D g) {
        this.sceneWidth = 3628;
        if (this.mapPosition < 3328.0F) {
            this.mapPosition += 0.5F;
            this.hero.position.x += 0.5F;
            if (this.hero.position.x > this.mapPosition + this.backgroundSizeOfWidth / 6) {
                this.hero.direction = 6;
            } else
                this.hero.direction = 2;
        } else {
            this.mapPosition = 3328.0F;
            this.hero.towardsLeft = true;
            this.hero.towardsRight = false;
            this.scenePosition.y = 0.0F;
            this.showBossScene = false;
            this.preBossBattle = true;
            AudioPlayer.player.stop(this.preBossBgSoundThread.as);
            this.preBossBgSoundThread.playSound = false;
            this.bossBgSound = new BossBackgroundSoundThread();
            new Thread(this.bossBgSound).start();
        }
    }
    private void setEnemyVisibility()
    {
        for (int i = 0; i < this.enemys.size(); i++) {
            SimpleEnemy e = this.enemys.get(i);
            if ((e.position.x > this.mapPosition) && (e.position.x < this.mapPosition + this.backgroundSizeOfWidth / 3) &&
                    (!e.visible)) {
                e.visible = true;
                if (e.type != 4) {
                    e.state = 1;
                    e.towardsLeft = true;
                    e.towardsRight = false;
                    e.direction = 6;
                }
            }
        }
    }
//人物死亡判断
    private void heroDeathCheck()
    {
        if ((this.hero.state != 4) && (this.hero.state != 5)) {
            for (int i = 0; i < this.enemyBullets.size(); i++) {
                Bullet b = this.enemyBullets.get(i);
                if ((b.position.x > this.hero.position.x - this.hero.width / 2 / 3) && (b.position.x < this.hero.position.x + this.hero.width / 2 / 3) &&
                        (b.position.y > this.hero.position.y - this.hero.height / 3) && (b.position.y < this.hero.position.y) &&
                        (this.hero.visible)) {
                    this.hero.state = 4;
                    this.hero.deathEventType = 0;
                    SoundUtils.playSound("audios/death.wav");
                    this.enemyBullets.remove(i);//子弹删除处理
                }
            }

            for (int i = 0; i < this.enemys.size(); i++) {//与敌人的碰撞处理
                SimpleEnemy e = this.enemys.get(i);
                if ((e.state != 4) && (e.state != 5) &&
                        (this.hero.position.x > e.position.x - this.hero.width / 2 / 3 - 11.0F) && (this.hero.position.x < e.position.x + 11.0F + this.hero.width / 2 / 3) &&
                        (this.hero.position.y > e.position.y - 26.0F) && (this.hero.position.y < e.position.y + this.hero.height / 3)) {
                    if (this.hero.visible) {
                        this.hero.state = 4;
                        this.hero.deathEventType = 2;
                        SoundUtils.playSound("audios/death.wav");
                    }
                    else {
                        e.state = 4;
                    }
                }
            }
        }
    }

    private void heroBirth() {//草坪上人物重生位置和其他位置重生处理
        if ((this.mapPosition > 680.0F) && (!this.passedFirstMovingGrassLand)) {
            GrassLand movingGrassLand = new GrassLand(new FloatPoint(24.0F, 110.0F), 1);
            movingGrassLand.movingDirection = 3;
            this.grassLands[(this.grassLands.length - 2)] = movingGrassLand;
            this.mapPosition = 680.0F;
            this.hero = new Hero(new FloatPoint(700.0F, 0.0F), 2);
        } else if ((this.hero.position.x > 740.0F) && (!this.passedFirstMovingGrassLand)) {
            GrassLand movingGrassLand = new GrassLand(new FloatPoint(24.0F, 110.0F), 1);
            movingGrassLand.movingDirection = 3;
            this.grassLands[(this.grassLands.length - 2)] = movingGrassLand;
            this.hero = new Hero(new FloatPoint(this.mapPosition + 20.0F, 0.0F), 2);
        } else if ((this.mapPosition > 970.0F) && (!this.passedSecondMovingGrassLand)) {
            GrassLand movingGrassLand = new GrassLand(new FloatPoint(33.0F, 110.0F), 1);
            movingGrassLand.movingDirection = 3;
            this.grassLands[(this.grassLands.length - 1)] = movingGrassLand;
            this.mapPosition = 970.0F;
            this.hero = new Hero(new FloatPoint(990.0F, 0.0F), 2);
        } else if ((this.hero.position.x > 1030.0F) && (!this.passedSecondMovingGrassLand)) {
            GrassLand movingGrassLand = new GrassLand(new FloatPoint(33.0F, 110.0F), 1);
            movingGrassLand.movingDirection = 3;
            this.grassLands[(this.grassLands.length - 1)] = movingGrassLand;
            this.hero = new Hero(new FloatPoint(this.mapPosition + 20.0F, 0.0F), 2);
        }
        else {
            this.hero = new Hero(findBirthPlace(), finBirthDirection());
        }
        this.hero.state = 0;
        this.heroNum -= 1;
        if(this.heroNum <=0) {
            AudioPlayer.player.stop(this.gameStartBgSoundThread.as);
            this.gameStartBgSoundThread.playSound = false;
            dispose();
            new EndFrame(this.heroNum);
        }
        this.leftDown = false;
        this.rightDown = false;
        this.upDown = false;
        this.downDown = false;
        this.jumpDown = false;
        if (this.boss != null)
            this.hero.weapon = new SimpleWeapon(1);
    }

    private int finBirthDirection()
    {
        return 2;
    }//人物重生方向的设定

    private FloatPoint findBirthPlace()//人物重生地点的定位
    {
        if ((this.passedFirstMovingGrassLand) && (this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 32.0F > this.mapPosition)) {
            for (int j = (int)(this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 50.0F); j < this.backgroundSizeOfWidth / 3 + this.mapPosition; j += 32) {
                for (int i = 0; i < this.backgroundSizeOfHeight / 3; i += 3) {
                    if ((isGrassLand(j, i)) && (isGrassLand(j + 10, i))) {
                        if (j % 32 < 16) {
                            j += 10;
                        }
                        return new FloatPoint(j, 0.0F);
                    }
                }
            }
        }
        else if ((this.passedSecondMovingGrassLand) && (this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F + 32.0F > this.mapPosition)) {
            for (int j = (int)(this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F + 50.0F); j < this.backgroundSizeOfWidth / 3 + this.mapPosition; j += 32) {
                for (int i = 0; i < this.backgroundSizeOfHeight / 3; i += 3) {
                    if ((isGrassLand(j, i)) && (isGrassLand(j + 10, i))) {
                        if (j % 32 < 16) {
                            j += 10;
                        }
                        return new FloatPoint(j, 0.0F);
                    }
                }
            }
        }
        else {
            for (int j = (int)this.mapPosition + 20; j < this.backgroundSizeOfWidth / 3 + this.mapPosition; j += 32) {
                for (int i = 0; i < this.backgroundSizeOfHeight / 3; i += 3) {
                    if ((isGrassLand(j, i)) && (isGrassLand(j + 10, i)))
                        return new FloatPoint(j + 10, 0.0F);
                }
            }
        }
        return null;
    }

    private void checkHeroIsDropping() {
        if ((this.hero.state != 4) && (this.hero.state != 5))
            if (!isGrassLand(this.hero.position.x, this.hero.position.y + 1.0F)) {
                if ((this.hero.position.y < 206.0F) && (!this.hero.jumping)) {
                    this.hero.state = 6;
                    this.hero.position.y += 2.0F;
                }
                else if ((this.hero.position.y > 206.0F) && (this.hero.jumping)) {
                    this.hero.position.y = 206.0F;
                    this.hero.state = 0;
                }

                if ((this.hero.position.y >= 206.0F) && (!isGrassLand(this.hero.position.x, this.hero.position.y + 1.0F)))
                {
                    if (this.hero.visible) {
                        this.hero.state = 4;
                        this.hero.deathEventType = 1;
                        SoundUtils.playSound("audios/death.wav");
                    }
                }
            }
            else if ((this.hero.jumping) && (this.hero.jumpFinished)) {
                this.hero.jumping = false;
                if ((this.hero.direction == 0) || (this.hero.direction == 1) || (this.hero.direction == 2) || (this.hero.direction == 3)) {
                    this.hero.direction = 2;
                }
                else if ((this.hero.direction == 4) || (this.hero.direction == 5) || (this.hero.direction == 6) || (this.hero.direction == 7)) {
                    this.hero.direction = 6;
                }
                this.hero.state = 0;
                SoundUtils.playSound("audios/jumpping.wav");
            }
    }

    private void enemysMove()
    {
        for (int i = 0; i < this.enemys.size(); i++) {
            SimpleEnemy enemy = this.enemys.get(i);
            if ((!isGrassLand(enemy.position.x, enemy.position.y + 2.0F)) && (!enemy.jumping)) {
                enemy.position.y += 2.0F;
            }
            if (enemy.position.y <= 206.0F) {
                if (enemy.state == 0) {
                    if (this.hero.position.x > enemy.position.x) {
                        if (this.hero.position.y < enemy.position.y) {
                            if (enemy.position.y - this.hero.position.y > this.hero.position.x - enemy.position.x) {
                                enemy.direction = 1;
                            }
                            else {
                                enemy.direction = 2;
                            }

                        }
                        else if (-enemy.position.y + this.hero.position.y > this.hero.position.x - enemy.position.x) {
                            enemy.direction = 3;
                        }
                        else {
                            enemy.direction = 2;
                        }

                    }
                    else if (this.hero.position.y < enemy.position.y) {
                        if (enemy.position.y - this.hero.position.y > -this.hero.position.x + enemy.position.x) {
                            enemy.direction = 7;
                        }
                        else {
                            enemy.direction = 6;
                        }

                    }
                    else if (-enemy.position.y + this.hero.position.y > -this.hero.position.x + enemy.position.x) {
                        enemy.direction = 5;
                    }
                    else {
                        enemy.direction = 6;
                    }

                }
                else if (enemy.state == 1) {
                    if (enemy.type == 0) {
                        if (enemy.direction == 6) {
                            enemy.position.x -= this.walkSpeed;
                        }
                        else if (enemy.direction == 2) {
                            enemy.position.x += this.walkSpeed;
                        }
                    }
                    else if (enemy.type == 1) {
                        if ((enemy.direction == 6) && (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
                            if ((enemy.position.x > 10.0F + this.mapPosition) && (isGrassLand(enemy.position.x - 10.0F, enemy.position.y + 2.0F))) {
                                enemy.position.x -= this.walkSpeed;
                            }
                            else {
                                enemy.direction = 2;
                                enemy.position.x += this.walkSpeed;
                            }
                        }
                        else if ((enemy.direction == 2) && (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
                            if ((enemy.position.x < this.backgroundSizeOfWidth / 3 + this.mapPosition) && (isGrassLand(enemy.position.x + 10.0F, enemy.position.y + 2.0F))) {
                                enemy.position.x += this.walkSpeed;
                            }
                            else {
                                enemy.direction = 6;
                                enemy.position.x -= this.walkSpeed;
                            }
                        }
                    }
                    else if (enemy.type == 2) {
                        if ((enemy.direction == 6) && (!enemy.jumping) &&
                                (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
                            if (isGrassLand(enemy.position.x - 3.0F, enemy.position.y + 2.0F)) {
                                enemy.position.x -= this.walkSpeed;
                            }
                            else {
                                enemy.state = 2;
                                enemy.jumping = true;
                            }

                        }

                        if ((enemy.direction == 2) && (!enemy.jumping) &&
                                (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
                            if (isGrassLand(enemy.position.x + 3.0F, enemy.position.y + 2.0F)) {
                                enemy.position.x += this.walkSpeed;
                            }
                            else {
                                enemy.state = 2;
                                enemy.jumping = true;
                            }
                        }

                    }
                    else if (enemy.type == 3) {
                        if ((enemy.direction == 6) && (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
                            if ((enemy.position.x > this.mapPosition + 10.0F) && (isGrassLand(enemy.position.x - 3.0F, enemy.position.y + 2.0F))) {
                                enemy.position.x -= this.walkSpeed;
                            }
                            else if (enemy.position.x <= this.mapPosition + 10.0F) {
                                enemy.direction = 2;
                                enemy.position.x += this.walkSpeed;
                            }
                            else if ((isGrassLand(enemy.position.x, enemy.position.y + 2.0F)) && (!isGrassLand(enemy.position.x - 3.0F, enemy.position.y + 2.0F))) {
                                if (isASafetyJump(new FloatPoint(enemy.position.x, enemy.position.y), enemy.direction)) {
                                    enemy.state = 2;
                                    enemy.jumping = true;
                                }
                                else {
                                    enemy.direction = 2;
                                    enemy.position.x += this.walkSpeed;
                                }
                            }
                        }
                        else if ((enemy.direction == 2) && (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
                            if ((enemy.position.x < this.mapPosition + this.backgroundSizeOfWidth / 3 - 10.0F) && (isGrassLand(enemy.position.x + 3.0F, enemy.position.y + 2.0F))) {
                                enemy.position.x += this.walkSpeed;
                            }
                            else if (enemy.position.x >= this.mapPosition + this.backgroundSizeOfWidth / 3 - 10.0F) {
                                enemy.direction = 6;
                                enemy.position.x -= this.walkSpeed;
                            }
                            else if ((isGrassLand(enemy.position.x, enemy.position.y + 2.0F)) && (!isGrassLand(enemy.position.x + 3.0F, enemy.position.y + 2.0F))) {
                                if (isASafetyJump(new FloatPoint(enemy.position.x, enemy.position.y), enemy.direction)) {
                                    enemy.state = 2;
                                    enemy.jumping = true;
                                }
                                else {
                                    enemy.direction = 6;
                                    enemy.position.x -= this.walkSpeed;
                                }
                            }
                        }
                    }
                }
                else if (enemy.state == 2) {
                    if ((enemy.direction == 2) && (enemy.jumping)) {
                        enemy.position.x += this.walkSpeed;
                    }
                    else if ((enemy.direction == 6) && (enemy.jumping)) {
                        enemy.position.x -= this.walkSpeed;
                    }
                    if ((enemy.jumpFinished) &&
                            (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
                        enemy.state = 1;
                        enemy.jumping = false;
                    }
                }

            }
            else if (!isGrassLand(enemy.position.x, enemy.position.y + 2.0F))
                enemy.state = 4;
        }
    }

    private boolean isASafetyJump(FloatPoint position, int direction)
    {
        if (direction == 2) {
            if ((!this.grassLands[(this.grassLands.length - 2)].beTouched) &&
                    (position.x < this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F)) {
                if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 3) {
                    float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
                    if (delta < 23.39D) {
                        delta = 23.4F - delta + 23.4F;
                    }
                    if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
                if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 1) {
                    float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 0.8F;
                    if (delta > 25.79F) {
                        delta = 25.79F - (delta - 25.79F);
                    }
                    if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
            }

            if ((!this.grassLands[(this.grassLands.length - 1)].beTouched) &&
                    (position.x < this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F)) {
                if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 3) {
                    float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
                    if (delta < 32.39D) {
                        delta = 32.402F - delta + 32.402F;
                    }
                    if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
                if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 1) {
                    float delta = this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F + 0.8F;
                    if (delta > 34.79F) {
                        delta = 34.79F - (delta - 34.79F);
                    }
                    if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
            }
            float x = position.x + 20.0F;
            for (float y = position.y - 40.0F;
                 (y < 206.0F) && (x < this.mapPosition + this.backgroundSizeOfWidth / 3 - 14.0F); y += 2.0F) {
                if (isGrassLand(x, y + 2.0F))
                    return true;
                x += 1.0F;
            }

            return false;
        }
        if (direction == 6) {
            if ((!this.grassLands[(this.grassLands.length - 2)].beTouched) &&
                    (position.x > this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 32.0F)) {
                if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 3) {
                    float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
                    if (delta < 23.39D) {
                        delta = 23.4F - delta + 23.4F;
                    }
                    if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
                if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 1) {
                    float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 0.8F;
                    if (delta > 25.79F) {
                        delta = 25.79F - (delta - 25.79F);
                    }
                    if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
            }

            if ((!this.grassLands[(this.grassLands.length - 1)].beTouched) &&
                    (position.x > this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F + 32.0F)) {
                if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 3) {
                    float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
                    if (delta < 32.39D) {
                        delta = 32.402F - delta + 32.402F;
                    }
                    if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
                if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 1) {
                    float delta = this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F + 0.8F;
                    if (delta > 34.79F) {
                        delta = 34.79F - (delta - 34.79F);
                    }
                    if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
                        return true;
                    }
                }
            }

            float x = position.x - 20.0F;
            for (float y = position.y - 40.0F;
                 (y < 206.0F) && (x > 14.0F); y += 2.0F) {
                if (isGrassLand(x, y + 2.0F))
                    return true;
                x -= 1.0F;
            }

            return false;
        }

        return false;
    }

    private void initGrassLands()
    {
        FloatPoint[] points = { new FloatPoint(1.0F, 110.0F), new FloatPoint(5.0F, 142.0F), new FloatPoint(8.0F, 174.0F),
                new FloatPoint(9.0F, 206.0F), new FloatPoint(11.0F, 174.0F), new FloatPoint(13.0F, 142.0F), new FloatPoint(18.0F, 206.0F),
                new FloatPoint(19.0F, 158.0F), new FloatPoint(27.0F, 110.0F), new FloatPoint(36.0F, 110.0F), new FloatPoint(42.0F, 78.0F),
                new FloatPoint(43.0F, 206.0F), new FloatPoint(46.0F, 160.0F), new FloatPoint(49.0F, 142.0F), new FloatPoint(53.0F, 206.0F),
                new FloatPoint(57.0F, 110.0F), new FloatPoint(59.0F, 174.0F), new FloatPoint(62.0F, 174.0F), new FloatPoint(63.0F, 78.0F),
                new FloatPoint(65.0F, 158.0F), new FloatPoint(67.0F, 142.0F), new FloatPoint(69.0F, 110.0F), new FloatPoint(72.0F, 142.0F),
                new FloatPoint(72.0F, 206.0F), new FloatPoint(73.0F, 174.0F), new FloatPoint(76.0F, 110.0F), new FloatPoint(77.0F, 78.0F),
                new FloatPoint(77.0F, 206.0F), new FloatPoint(78.0F, 158.0F), new FloatPoint(80.0F, 110.0F), new FloatPoint(81.0F, 142.0F),
                new FloatPoint(84.0F, 206.0F), new FloatPoint(88.0F, 174.0F), new FloatPoint(91.0F, 142.0F), new FloatPoint(93.0F, 110.0F),
                new FloatPoint(93.0F, 206.0F), new FloatPoint(94.0F, 158.0F), new FloatPoint(98.0F, 142.0F), new FloatPoint(99.0F, 174.0F),
                new FloatPoint(24.0F, 110.0F), new FloatPoint(33.0F, 110.0F) };
        int[] lengths = { 22, 3, 1, 2, 1, 2, 2, 3, 5, 8, 16, 3, 2, 7, 6, 7, 2, 2, 5, 1, 3, 2, 2, 1, 3, 2,
                2, 1, 1, 2, 5, 3, 2, 2, 5, 22, 4, 1, 1, 1, 1 };
        for (int i = 0; i < points.length - 2; i++) {
            GrassLand gl = new GrassLand(points[i], lengths[i]);
            this.grassLands[i] = gl;
        }
        GrassLand movingGrassLand1 = new GrassLand(new FloatPoint(24.0F, 110.0F), 1);
        GrassLand movingGrassLand2 = new GrassLand(new FloatPoint(33.0F, 110.0F), 1);
        movingGrassLand1.movingDirection = 3;
        movingGrassLand2.movingDirection = 3;
        this.grassLands[(this.grassLands.length - 2)] = movingGrassLand1;
        this.grassLands[(this.grassLands.length - 1)] = movingGrassLand2;
    }

    private boolean isGrassLand(float x, float y) {
        if (this.hero.towardsRight) {
            for (int i = 0; i < this.grassLands.length; i++) {
                if ((x > this.grassLands[i].position.x * 32.0F - 7.0F) && (x < (this.grassLands[i].position.x + this.grassLands[i].length) * 32.0F + 3.0F) &&
                        (y > this.grassLands[i].position.y) && (y < this.grassLands[i].position.y + 5.0F))
                    return true;
            }
        }
        else if (this.hero.towardsLeft) {
            for (int i = 0; i < this.grassLands.length; i++) {
                if ((x > this.grassLands[i].position.x * 32.0F - 7.0F) && (x < (this.grassLands[i].position.x + this.grassLands[i].length) * 32.0F + 1.0F) &&
                        (y > this.grassLands[i].position.y) && (y < this.grassLands[i].position.y + 5.0F))
                    return true;
            }
        }
        else if ((!this.hero.towardsLeft) && (!this.hero.towardsRight)) {
            for (int i = 0; i < this.grassLands.length; i++) {
                if ((x > this.grassLands[i].position.x * 32.0F) && (x < (this.grassLands[i].position.x + this.grassLands[i].length) * 32.0F) &&
                        (y > this.grassLands[i].position.y) && (y < this.grassLands[i].position.y + 5.0F))
                    return true;
            }
        }
        return false;
    }

    private void enemysDeathCheck() {
        for (int i = 0; i < this.enemys.size(); i++) {
            if (((this.enemys.get(i)).state == 5) ||
                    ((this.enemys.get(i)).position.x > this.mapPosition + this.backgroundSizeOfWidth / 3)) {
                if ((this.enemys.get(i)).visible)
                    this.enemys.remove(i);
            }
            else if ((this.enemys.get(i)).position.x < this.mapPosition) {
                this.enemys.remove(i);
            }
        }
        for (int i = 0; i < this.enemys.size(); i++) {
            SimpleEnemy e = this.enemys.get(i);
            for (int j = 0; j < this.heroBullets.size(); j++) {
                Bullet b = this.heroBullets.get(j);
                if (e.touchHeroBulletCheck(b)) {
                    if ((e.state == 4) || (e.state == 5)) break;
                    e.beShotted(b.energy);
                    this.heroBullets.remove(j);

                    break;
                }
            }
        }
        if ((this.boss != null) && (this.boss.state != 4) && (this.boss.state != 5)) {
            for (int i = 0; i < this.heroBullets.size(); i++) {
                Bullet b = this.heroBullets.get(i);
                if (this.boss.touchHeroBulletCheck(b)) {
                    this.boss.beShotted(b.energy);
                    this.heroBullets.remove(i);
                }
                if ((this.boss.state == 4) && (this.stopBossBgSound)) {
                    this.enemyBullets.removeAll(this.enemyBullets);
                    this.enemys.removeAll(this.enemys);

                    AudioPlayer.player.stop(this.bossBgSound.as);
                    this.bossBgSound.playSound = false;
                    this.stopBossBgSound = false;
                }
            }
        }


    }

    private void drawHeroBullets(Graphics2D g)
    {
        for (int i = 0; i < this.heroBullets.size(); i++)
        {
            int x = (int)(this.heroBullets.get(i)).position.x;
            int y = (int)(this.heroBullets.get(i)).position.y;
            boolean isAlive = (this.heroBullets.get(i)).isAlive;
            if ((x < this.mapPosition) || (x > this.backgroundSizeOfWidth / 3.0F + this.mapPosition) || (y < 0) || (y > this.backgroundSizeOfHeight / 3.0F) || (!isAlive)) {
                this.heroBullets.remove(i);//人物子弹越界处理
            }
            else
                (this.heroBullets.get(i)).drawBubblet(g, this.mapPosition);
        }
    }

    private void drawEnemyBullets(Graphics2D g)
    {
        for (int i = 0; i < this.enemyBullets.size(); i++) {
            int x = (int)(this.enemyBullets.get(i)).position.x;
            int y = (int)(this.enemyBullets.get(i)).position.y;
            boolean isAlive = (this.enemyBullets.get(i)).isAlive;
            if ((x < this.mapPosition) || (x > this.backgroundSizeOfWidth / 3.0F + this.mapPosition) || (y < 0) || (y > this.backgroundSizeOfHeight / 3.0F) || (!isAlive)) {
                this.enemyBullets.remove(i);
            }
            else
                (this.enemyBullets.get(i)).drawBubblet(g, this.mapPosition);
        }
    }

    private void drawNPC(Graphics2D g)
    {
        this.grassLands[(this.grassLands.length - 2)].drawMovingGrassLand(this.hero, 23.4F, 25.79F, (int)this.mapPosition, this.backgroundSizeOfWidth, this, g);
        this.grassLands[(this.grassLands.length - 1)].drawMovingGrassLand(this.hero, 32.4F, 34.79F, (int)this.mapPosition, this.backgroundSizeOfWidth, this, g);
    }

    private void setKeyStateWhenKeyPressed(KeyEvent e) {
        if ((this.hero.state != 4) && (this.hero.state != 5)) {
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
                this.upDown = true;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
                this.downDown = true;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
                this.leftDown = true;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
                this.rightDown = true;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("K"))
                this.jumpDown = true;
        }
    }

    private void setKeyStateWhenKeyReleased(KeyEvent e)
    {
        if ((this.hero.state != 4) && (this.hero.state != 5)) {
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
                this.upDown = false;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
                this.downDown = false;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
                this.leftDown = false;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
                this.rightDown = false;
            }
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("K"))
                this.jumpDown = false;
        }
    }

    private void heroMove()
    {
        if ((this.hero.state != 4) && (this.hero.state != 5))
            if ((!this.hero.jumping) && (!this.jumpDown) && (!this.leftDown) && (!this.rightDown) && (!this.upDown) && (!this.downDown)) {
                this.hero.state = 0;
                if (this.hero.towardsLeft) {
                    this.hero.direction = 6;
                }
                else if (this.hero.towardsRight) {
                    this.hero.direction = 2;
                }

            }
            else if (this.hero.jumping) {
                if (this.upDown) {
                    this.hero.direction = 0;
                    this.hero.state = 2;
                    if ((this.leftDown) && (!this.showBossScene)) {
                        this.hero.direction = 7;
                        this.hero.towardsLeft = true;
                        this.hero.towardsRight = false;
                        if (this.hero.position.x > 8.0F + this.mapPosition)
                            this.hero.position.x -= this.walkSpeed;
                    }
                    else if ((this.rightDown) && (!this.showBossScene)) {
                        this.hero.direction = 1;
                        this.hero.towardsLeft = false;
                        this.hero.towardsRight = true;
                        moveForwardToRight();
                    }
                }
                else if (this.downDown) {
                    this.hero.direction = 4;
                    this.hero.state = 2;
                    if (this.leftDown) {
                        this.hero.direction = 5;
                        this.hero.towardsLeft = true;
                        this.hero.towardsRight = false;
                        if (this.hero.position.x > 8.0F + this.mapPosition)
                            this.hero.position.x -= this.walkSpeed;
                    }
                    else if ((this.rightDown) && (!this.showBossScene)) {
                        this.hero.direction = 3;
                        this.hero.towardsLeft = false;
                        this.hero.towardsRight = true;
                        moveForwardToRight();
                    }
                }
                else if ((this.leftDown) && (!this.showBossScene)) {
                    this.hero.direction = 6;
                    this.hero.state = 2;
                    this.hero.towardsLeft = true;
                    this.hero.towardsRight = false;
                    if (this.hero.position.x > 8.0F + this.mapPosition)
                        this.hero.position.x -= this.walkSpeed;
                }
                else if ((this.rightDown) && (!this.showBossScene)) {
                    this.hero.direction = 2;
                    this.hero.state = 2;
                    this.hero.towardsLeft = false;
                    this.hero.towardsRight = true;
                    moveForwardToRight();
                }

            }
            else if ((this.leftDown) && (!this.showBossScene)) {
                if ((this.jumpDown) && (isGrassLand(this.hero.position.x, this.hero.position.y + 1.0F)))//跳跃键盘
                {
                    this.hero.direction = 6;
                    this.hero.state = 2;
                    this.hero.towardsLeft = true;
                    this.hero.towardsRight = false;
                    this.hero.jumping = true;
                    this.hero.jumpFinished = false;
                }
                else if (this.upDown) {//左上键盘
                    this.hero.state = 1;
                    this.hero.direction = 7;
                    this.hero.towardsLeft = true;
                    this.hero.towardsRight = false;
                    if (this.hero.position.x > 8.0F + this.mapPosition)
                        this.hero.position.x -= this.walkSpeed;
                }
                else if (this.downDown) {//左下键盘
                    this.hero.direction = 5;
                    this.hero.state = 1;
                    this.hero.towardsLeft = true;
                    this.hero.towardsRight = false;
                    if (this.hero.position.x > 8.0F + this.mapPosition)
                        this.hero.position.x -= this.walkSpeed;
                }
                else//左键
                {
                    this.hero.state = 1;
                    this.hero.direction = 6;
                    this.hero.towardsLeft = true;
                    this.hero.towardsRight = false;
                    if (this.hero.position.x > 8.0F + this.mapPosition)
                        this.hero.position.x -= this.walkSpeed;
                }
            }
            else if ((this.rightDown) && (!this.showBossScene)) {
                if ((this.jumpDown) && (isGrassLand(this.hero.position.x, this.hero.position.y + 1.0F))) {//右跳跃
                    this.hero.direction = 2;
                    this.hero.state = 2;
                    this.hero.towardsLeft = false;
                    this.hero.towardsRight = true;
                    this.hero.jumping = true;
                    this.hero.jumpFinished = false;
                }
                else if (this.upDown) {//右上
                    this.hero.direction = 1;
                    this.hero.state = 1;
                    this.hero.towardsLeft = false;
                    this.hero.towardsRight = true;
                    moveForwardToRight();
                }
                else if (this.downDown) {//右下
                    this.hero.direction = 3;
                    this.hero.state = 1;
                    this.hero.towardsLeft = false;
                    this.hero.towardsRight = true;
                    moveForwardToRight();
                }
                else//右
                {
                    this.hero.direction = 2;
                    this.hero.state = 1;
                    this.hero.towardsLeft = false;
                    this.hero.towardsRight = true;
                    moveForwardToRight();
                }
            }
            else if (this.upDown) {
                if ((this.jumpDown) && (isGrassLand(this.hero.position.x, this.hero.position.y + 1.0F))) {//上跳跃
                    this.hero.direction = 0;
                    this.hero.state = 2;
                    this.hero.jumpFinished = false;
                    this.hero.jumping = true;
                }
                else//上
                {
                    this.hero.state = 0;
                    this.hero.direction = 0;
                }
            }
            else if (this.downDown) {
                if (this.jumpDown) {//下跳
                    this.hero.state = 1;
                }
                else//下
                {
                    this.hero.state = 3;
                    if (this.hero.direction == 3) {
                        this.hero.direction = 2;
                    }
                    else if (this.hero.direction == 5) {
                        this.hero.direction = 6;
                    }
                }
            }
            else if ((this.jumpDown) && (isGrassLand(this.hero.position.x, this.hero.position.y + 1.0F)))//跳跃
            {
                this.hero.state = 2;
                this.hero.jumping = true;
                this.hero.jumpFinished = false;
            }
    }

    private void moveForwardToRight()
    {
        if ((this.hero.state != 4) && (this.hero.state != 5))
            if ((this.hero.position.x - this.mapPosition < this.backgroundSizeOfWidth / 6.0F) || (this.mapPosition >= this.sceneWidth - this.backgroundSizeOfWidth / 3)) {
                if (this.hero.position.x - this.mapPosition < (this.backgroundSizeOfWidth - 20.0F) / 3.0F)
                    this.hero.position.x += this.walkSpeed;
            }
            else {
                if (this.mapPosition < this.sceneWidth - this.backgroundSizeOfWidth / 3) {
                    this.mapPosition += this.walkSpeed;
                }
                else {
                    this.mapPosition = (this.sceneWidth - this.backgroundSizeOfWidth / 3.0F);
                }
                this.hero.position.x = (this.backgroundSizeOfWidth / 6.0F + this.mapPosition);
            }
    }

    public static void main(String[] args)
    {
        new Thread(new MainFrame()).start();
    }
}

