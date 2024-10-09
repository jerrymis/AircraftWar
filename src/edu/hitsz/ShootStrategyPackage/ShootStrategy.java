package edu.hitsz.ShootStrategyPackage;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;
import java.util.List;

public abstract class ShootStrategy {
    public int shootNumHero1 = 1;
    public int shootNumHero2 = 1;
    public int shootNumEnemy = 2;
    public int direction = -1;

    public void setShootNumHero2(int shootNumHero2) {
        this.shootNumHero2 = shootNumHero2;
    }

    public void setShootNumEnemy(int shootNumEnemy) {
        this.shootNumEnemy = shootNumEnemy;
    }

    public abstract List<AbstractBullet> shoot(AbstractAircraft aircraft);
}
