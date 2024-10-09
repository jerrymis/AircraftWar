package edu.hitsz.ShootStrategyPackage;

import edu.hitsz.ShootStrategyPackage.ShootStrategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;


import java.util.LinkedList;
import java.util.List;

public class DirectShoot extends ShootStrategy {


    @Override
    public List<AbstractBullet> shoot(AbstractAircraft aircraft) {
            List<AbstractBullet> res = new LinkedList<>();
            int x = aircraft.getLocationX();
            int y = aircraft.getLocationY() + direction*2;
            int speedX = 0;
            int speedY = -aircraft.getSpeedY()+direction*5;
            AbstractBullet abstractBullet;
            for(int i=0; i<1; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                if (aircraft instanceof HeroAircraft) {
                    abstractBullet = new HeroBullet(x + (i * 2 - shootNumHero1 + 1) * 10, y, speedX, speedY, 30);
                    res.add(abstractBullet);
                }else if(aircraft instanceof EliteEnemy) {
                    abstractBullet = new EnemyBullet(x + (i * 2 - shootNumEnemy+ 1) * 10, y, speedX, -speedY, 10);
                    res.add(abstractBullet);
                } else{
                    return new LinkedList<>();
                }
                }
            return res;
        }
    }

