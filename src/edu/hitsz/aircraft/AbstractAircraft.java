package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategyPackage.ShootStrategy;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    private boolean flag = true;
    protected int maxHp;
    protected int hp;
    private ShootStrategy shootStrategy;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setShootStrategy(ShootStrategy shootstrategy) {
        this.shootStrategy = shootstrategy;
    }

    public ShootStrategy getShootStrategy() {
        return shootStrategy;
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootstrategy) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootStrategy = shootstrategy;
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        } else if (hp >= 100) {
            hp = 100;
        }
    }

    public int getHp() {
        return hp;
    }

    public List<AbstractBullet> shoot(AbstractAircraft aircraft) {
        return this.shootStrategy.shoot(aircraft);
    }
}


