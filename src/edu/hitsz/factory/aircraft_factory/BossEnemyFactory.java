package edu.hitsz.factory.aircraft_factory;

import edu.hitsz.ShootStrategyPackage.DirectShoot;
import edu.hitsz.ShootStrategyPackage.ScatteredShoot;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.ShootStrategyPackage.ShootStrategy;

public class BossEnemyFactory implements  AircraftFactory {
    int speedX = 2;
    int speedY = 0;
    int hp = 180;
    ShootStrategy shootStrategy = new ScatteredShoot();
    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getHp() {
        return hp;
    }

    public ShootStrategy getShootStrategy() {
        return shootStrategy;
    }

    @Override
    public AbstractAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootstrategy){
        return new BossEnemy(locationX, locationY, speedX, speedY, hp,shootstrategy);
    }

}
