package edu.hitsz.aircraft;

import edu.hitsz.ShootStrategyPackage.ShootStrategy;
import edu.hitsz.application.Main;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootstrategy) {
        super(locationX, locationY, speedX, speedY, hp,shootstrategy);
    }


    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
            setFlag(false);
        }
    }

}
