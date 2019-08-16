package com.Keith.hunDouLuo.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Boss {
  public FloatPoint position;
  public int direction;
  public int defaultLife = 60;
  public int life = this.defaultLife;
  public boolean isAlive = true;
  public int state = 0;
  public Weapon[] weapons = new Weapon[6];
  float flyingTemp = 0.0F;
  FloatPoint diePosition;
  boolean playVictorySound = true;
  int shotFPSTemp = 0;

  public Boss(FloatPoint position) {
    this.state = 0;
    this.direction = 6;
    this.position = position;
    this.weapons[0] = new SimpleBossWeapon(0);
    this.weapons[1] = new SimpleBossWeapon(0);
    this.weapons[2] = new StrongBossWeapon(0);
    this.weapons[3] = new StrongBossWeapon(0);
    this.weapons[4] = new SimpleBossWeapon(0);
    this.weapons[5] = new SimpleBossWeapon(0);
  }

  public void drawBoss(Graphics2D g, float mapPosition) {
    if (this.state == 0) {
      flyingAnimation(g, mapPosition);
    }
    else if (this.state == 4) {
      dieingAnimation(g, mapPosition);
    }
    else if (this.state == 5)
      drawDiedBossAnimation(g, mapPosition);
  }

  private void drawDiedBossAnimation(Graphics2D g, float mapPosition) {
    g.drawImage(Imgs.BOSSBOMBBED, (int)(this.position.x - mapPosition) * 3 - 150, (int)this.position.y * 3 - 170, null);
  }

  private void flyingAnimation(Graphics2D g, float mapPosition) {
    this.position.x = ((float)(30.0D * Math.sin(this.flyingTemp)) + 150.0F + mapPosition);
    this.flyingTemp += 0.05F;
    g.drawImage(Imgs.BOSS, (int)(this.position.x - mapPosition) * 3 - 150, (int)this.position.y * 3 - 170, null);
  }

  private void dieingAnimation(Graphics2D g, float mapPosition) {
    g.drawImage(Imgs.BOSS, (int)(this.position.x - mapPosition) * 3 - 150, (int)this.position.y * 3 - 170, null);
    this.state = 5;
    this.isAlive = false;
  }

  public void beShotted(int damage) {
    SoundUtils.playSound("audios/beshotted.wav");
    this.life -= damage;
    if ((this.life <= 0) && (this.playVictorySound)) {
      SoundUtils.playSound("audios/victory.mid");
      this.state = 4;
      this.playVictorySound = false;
      this.diePosition = new FloatPoint(this.position.x, this.position.y);
    }
  }

  public boolean touchHeroBulletCheck(Bullet b) {
    if (this.isAlive) {
      if ((b.position.x > this.position.x - 13.3F - b.size / 3.0F) && (b.position.x < this.position.x + 13.3F + b.size / 3.0F) &&
              (b.position.y > this.position.y - 56.68F - b.size / 3.0F) && (b.position.y < this.position.y + b.size / 3.0F)) {
        return true;
      }
      if ((b.position.x > this.position.x - 25.0F - b.size / 3.0F) && (b.position.x < this.position.x + 25.0F + b.size / 3.0F) &&
              (b.position.y > this.position.y - 39.32F - b.size / 3.0F) && (b.position.y < this.position.y - 27.34F + b.size / 3.0F)) {
        return true;
      }
      if ((b.position.x > this.position.x - 18.34F - b.size / 3.0F) && (b.position.x < this.position.x + 18.34F + b.size / 3.0F) &&
              (b.position.y > this.position.y - 56.68F - b.size / 3.0F) && (b.position.y < this.position.y - 39.32F + b.size / 3.0F)) {
        return true;
      }
      if ((b.position.x > this.position.x - 50.0F - b.size / 3.0F) && (b.position.x < this.position.x + 50.0F + b.size / 3.0F) &&
              (b.position.y > this.position.y - 27.6F - b.size / 3.0F) && (b.position.y < this.position.y - 14.0F + b.size / 3.0F))
        return true;
    }
    return false;
  }

  public List<Bullet> simpleShot(FloatPoint target, int num)
  {
    List list = new ArrayList();
    if (this.life > 0)
      if (this.shotFPSTemp++ % 10 == 0) {
        list.addAll(this.weapons[2].shot(new FloatPoint(this.position.x - 24.0F, this.position.y - 1.0F), new FloatPoint(this.position.x - 24.0F, 206.0F),
                this.direction, num));
        list.addAll(this.weapons[3].shot(new FloatPoint(this.position.x + 24.0F, this.position.y - 1.0F), new FloatPoint(this.position.x + 24.0F, 206.0F),
                this.direction, num));
      }
      else if (this.shotFPSTemp % 100 == 0) {
        list.addAll(this.weapons[0].shot(new FloatPoint(this.position.x - 40.0F, this.position.y - 7.0F), new FloatPoint(target.x, target.y),
                this.direction, num));
        list.addAll(this.weapons[1].shot(new FloatPoint(this.position.x - 34.0F, this.position.y - 5.0F), new FloatPoint(target.x, target.y),
                this.direction, num));
        list.addAll(this.weapons[4].shot(new FloatPoint(this.position.x + 34.0F, this.position.y - 5.0F), new FloatPoint(target.x, target.y),
                this.direction, num));
        list.addAll(this.weapons[5].shot(new FloatPoint(this.position.x + 40.0F, this.position.y - 7.0F), new FloatPoint(target.x, target.y),
                this.direction, num));
      }
    return list;
  }
}

