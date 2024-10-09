package edu.hitsz.observerPattern;

import edu.hitsz.bullet.EnemyBullet;

import java.util.List;

public class EnemyBulletBomb implements Item{

    @Override
    public void update(List<?>  list) {
        for (Object o : list) {
            if (o instanceof EnemyBullet) {
                ((EnemyBullet) o).vanish();
            }
        }
    }
}