package edu.hitsz.observerPattern;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.EnemyBullet;

import java.util.List;

public class EnemyAircraft implements Item{
    @Override
    public void update(List<?> list) {
        for (Object o : list) {
            if (o instanceof BossEnemy) {
            }
            else{
                ((AbstractAircraft) o).vanish();
            }
        }
    }
}
