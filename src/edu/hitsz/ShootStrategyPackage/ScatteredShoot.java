package edu.hitsz.ShootStrategyPackage;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatteredShoot extends ShootStrategy {
    @Override
    public List<AbstractBullet> shoot(AbstractAircraft aircraft) {
        List<AbstractBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction*2;
        int speedX = 4;
        int speedY = aircraft.getSpeedY() + direction*5;
        AbstractBullet abstractBullet;
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (aircraft instanceof HeroAircraft) {
                for(int i=0; i<shootNumHero2; i++) {
//                    aircraft.getShootStrategy().shootNumHero2 = 3;
                aircraft.getShootStrategy().setShootNumHero2(3);
                    double angle = Math.PI/(aircraft.getShootStrategy().shootNumHero2+1);
                    abstractBullet = new HeroBullet(x + (i * 2 - shootNumHero2 + 1) * 10, y, (int)(Math.cos(angle*(i+1)) * 10), (int)(Math.sin(angle*(i+1)) * 10 * direction), 30);
                    res.add(abstractBullet);
//                abstractBullet = new HeroBullet(x + (i * 2 - shootNumHero2 + 1) * 10, y, speedX, speedY, 30);
//                res.add(abstractBullet);
            }
        }

              else if (aircraft instanceof BossEnemy) {
                for(int i=0; i<shootNumEnemy; i++){
                    double angle = Math.PI/(aircraft.getShootStrategy().shootNumEnemy+1);
                    abstractBullet = new EnemyBullet(x + (i * 2 - shootNumEnemy + 1) * 10, y, -(int)(Math.cos(angle*(i+1)) * 10), -(int)(Math.sin(angle*(i+1)) * 10 * direction), 30);
                    res.add(abstractBullet);
//                    abstractBullet = new EnemyBullet(x + (i * 2 - shootNumEnemy + 1) * 10, y, speedX, -speedY, 20);
//                    res.add(abstractBullet);
                }
            }

        return res;
    }
}



