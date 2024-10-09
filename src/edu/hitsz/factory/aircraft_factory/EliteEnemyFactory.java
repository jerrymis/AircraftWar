package edu.hitsz.factory.aircraft_factory;

import edu.hitsz.ShootStrategyPackage.DirectShoot;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.ShootStrategyPackage.ShootStrategy;

public class EliteEnemyFactory implements AircraftFactory {
    int speedX = 2;
    int speedY = 5;
    int hp = 60;
    ShootStrategy shootStrategy = new DirectShoot();

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
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp,shootstrategy);
    }

}
